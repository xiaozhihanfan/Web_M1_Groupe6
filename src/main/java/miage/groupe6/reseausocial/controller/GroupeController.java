package miage.groupe6.reseausocial.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.GroupeService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

/**
 * Contrôleur Spring MVC pour la gestion des groupes dans le réseau social.
 * <p>
 * Gère la création de groupes, l'affichage des amis invitables à un groupe,
 * et l'invitation d'utilisateurs à rejoindre un groupe.
 */
@Controller
@RequestMapping("/groupes")
public class GroupeController {

    @Autowired
    private GroupeService groupeService;

    @Autowired
    private RelationAmisService relationAmisService;

    @Autowired
    private UtilisateurService utilisateurService;

    // -------------- us 3.1 créer une groupe ------------------
    /**
     * Affiche le formulaire de création d'un groupe.
     *
     * @param model   modèle Spring pour passer des attributs à la vue
     * @param session session HTTP contenant l'utilisateur connecté
     * @return nom de la vue à afficher ou redirection vers la page de connexion
     */
    @GetMapping("/creer")
    public String afficherFormulaireGroupe(Model model, HttpSession session) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur == null) {
            return "redirect:/utilisateurs/signin";
        }

        model.addAttribute("groupe", new Groupe());
        return "creerGroupe";

    }

    /**
     * Soumet le formulaire de création d'un groupe.
     *
     * @param groupe  instance de {@link Groupe} remplie depuis le formulaire
     * @param session session HTTP contenant l'utilisateur connecté
     * @return redirection vers la page de groupes de l'utilisateur
     */
    @PostMapping("/creer")
    public String soumettreFormulaireGroupe(@ModelAttribute Groupe groupe, HttpSession session) {
        

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur == null) {
            return "redirect:/utilisateurs/signin";
        }

        groupeService.creerGroupe(groupe, utilisateur);

        return "redirect:/utilisateurs/" + utilisateur.getIdU() + "/profile-groups"; 
    }

    // -------------- us 3.2 inviter mes amis dans une groupe ------------------
    // @GetMapping("/{idGroupe}/inviter")
    // public String afficherAmisInvitables(@PathVariable Long idGroupe, HttpSession session, Model model) {
    //     Utilisateur u1 = (Utilisateur) session.getAttribute("utilisateur");

    //     Groupe groupe = groupeService.getGroupeById(idGroupe).orElse(null);
    //     if(groupe == null || u1 == null) {
    //         return "redirect:/";
    //     }

    //     List<Utilisateur> mesAmis = relationAmisService.listerAmis(u1);
    //     List<Utilisateur> membresAmis = groupeService.listerMembres(groupe);

    //     mesAmis.removeAll(membresAmis);

    //     model.addAttribute("groupe", groupe);
    //     model.addAttribute("mesAmis", mesAmis);

    //     return "inviterGroupe";
    // }

    /**
     * Renvoie une liste JSON des amis pouvant être invités dans un groupe donné.
     *
     * @param idGroupe identifiant du groupe
     * @param session  session HTTP contenant l'utilisateur connecté
     * @return map JSON contenant le statut, les infos du groupe et les amis à inviter
     */
    @GetMapping("/{idGroupe}/amis-invitables")
    @ResponseBody
    public Map<String, Object> getInvitablesJson(@PathVariable Long idGroupe, HttpSession session) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        Optional<Groupe> optGroupe = groupeService.getGroupeById(idGroupe);

        
        if (utilisateur == null || optGroupe.isEmpty()) {
            return Map.of("status", "error");
        }

        Groupe groupe = optGroupe.get();
        List<Utilisateur> mesAmis = relationAmisService.listerAmis(utilisateur);
        List<Utilisateur> membres = groupeService.listerMembres(groupe);

        mesAmis.removeAll(membres);

        List<Map<String, Object>> amis = mesAmis.stream()
            .map(ami -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", ami.getIdU());
                map.put("nom", ami.getPrenomU() + " " + ami.getNomU());
                return map;
            })
            .collect(Collectors.toList());

        return Map.of(
            "status", "ok",
            "nom", groupe.getNomGroupe(),
            "description", groupe.getDescription(),
            "amis", amis
        );
    }

    /**
     * Invite un utilisateur à rejoindre un groupe.
     *
     * @param idGroupe      identifiant du groupe
     * @param idUtilisateur identifiant de l'utilisateur à inviter
     * @param session       session HTTP
     * @return réponse HTTP indiquant le succès ou l'échec
     */
    @PostMapping("/{idGroupe}/inviter/{idUtilisateur}")
    @ResponseBody
    public ResponseEntity<String> inviterAmis(@PathVariable Long idGroupe,
                                                @PathVariable Long idUtilisateur,
                                                HttpSession session) {
        Optional<Groupe> groupeOpt = groupeService.getGroupeById(idGroupe);
        Optional<Utilisateur> utilisateurOpt = utilisateurService.getUtilisateurById(idUtilisateur);

        if (groupeOpt.isPresent() && utilisateurOpt.isPresent()) {
            groupeService.ajouterMembreAuGroupe(utilisateurOpt.get(), groupeOpt.get());
            return ResponseEntity.ok("success");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
    }
}
