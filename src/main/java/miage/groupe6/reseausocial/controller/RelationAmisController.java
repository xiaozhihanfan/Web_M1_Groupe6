package miage.groupe6.reseausocial.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.EvenementsService;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

/**
 * Contrôleur Spring MVC pour la gestion des relations d'amitié entre utilisateurs.
 * <p>
 * Ce contrôleur permet :
 * <ul>
 *   <li>d'envoyer une demande d'amitié,</li>
 *   <li>d'accepter ou refuser une demande reçue,</li>
 *   <li>de supprimer une relation existante.</li>
 * </ul>
 */
@Controller
@RequestMapping("/relationamis")
public class RelationAmisController {
    
    @Autowired
    private RelationAmisService relationAmisService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private PostService postService;

    @Autowired
    private EvenementsService evenementsService;


    // ----------------------- us 1.4 Envoyer une demande d’ami ---------------------

    /**
     * Envoie une demande d'amitié de l'utilisateur connecté vers un autre utilisateur cible.
     *
     * @param idCible identifiant de l'utilisateur à qui la demande est adressée
     * @param session session HTTP contenant l'utilisateur connecté
     * @param redirectAttributes attributs utilisés pour transmettre des messages flash
     * @return redirection vers la page des résultats ou vers la connexion si non authentifié
     */
    @PostMapping("/envoyer/{idCible}")
    public String envoyerDemandeAmi(@PathVariable Long idCible,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        Utilisateur demandeur = (Utilisateur) session.getAttribute("utilisateur");
        if (demandeur == null) {
            return "redirect:/utilisateurs/signin";
        }

        Optional<Utilisateur> cibleOpt = utilisateurService.getUtilisateurById(idCible);
        if(cibleOpt.isEmpty() || cibleOpt.get().getIdU().equals(demandeur.getIdU())) {
            redirectAttributes.addFlashAttribute("erreur", "Utilisateur cible invalide.");
            return "redirect:/utilisateurs/rechercher";
        }

        if(relationAmisService.demandeExisteDeja(demandeur.getIdU(),idCible)) {
            redirectAttributes.addFlashAttribute("info", "Demande d'amitié déjà envoyée.");
        } else {
            relationAmisService.envoyerDemandeAmi(demandeur, cibleOpt.get());
            redirectAttributes.addFlashAttribute("message", "Demande d'amitié envoyée avec succès.");
        }

        return "redirect:/utilisateurs/resultats?query=" + cibleOpt.get().getEmailU();
    }


    // ----------------------- us 1.5 Accepter ou refuser une demande d’ami ---------------------
    /**
     * Accepte une demande d'amitié reçue d'un autre utilisateur.
     * Met à jour les compteurs de posts, amis et événements pour l'accueil.
     *
     * @param idDemandeur identifiant de l'utilisateur ayant envoyé la demande
     * @param session session HTTP contenant l'utilisateur connecté
     * @param redirectAttributes attributs pour transmettre les messages de confirmation
     * @return redirection vers la page d'accueil
     */
    @PostMapping("/accepter/{idDemandeur}")
    public String accepter(@PathVariable Long idDemandeur, HttpSession session, RedirectAttributes redirectAttributes) {
        Utilisateur receveur = (Utilisateur) session.getAttribute("utilisateur");
        if (relationAmisService.accepterDemandeAmis(idDemandeur, receveur.getIdU())) {
            redirectAttributes.addFlashAttribute("message", "Demande acceptée !");
        }

        int nbPost = postService.countPostByUtilisateur(receveur);

        int nbAmis = relationAmisService.countAmis(receveur);

        int nbEvenement = evenementsService.countEvenements(receveur);

        redirectAttributes.addFlashAttribute("nbAmis", nbAmis);

        redirectAttributes.addFlashAttribute("nbPost", nbPost);

        redirectAttributes.addFlashAttribute("nbEvenement", nbEvenement);
        return "redirect:/";
    }

    /**
     * Refuse une demande d'amitié reçue.
     *
     * @param idDemandeur identifiant de l'utilisateur ayant envoyé la demande
     * @param session session HTTP contenant l'utilisateur connecté
     * @param redirectAttributes attributs pour message flash
     * @return redirection vers la page d'accueil
     */
    @PostMapping("/refuser/{idDemandeur}")
    public String refuser(@PathVariable Long idDemandeur, HttpSession session, RedirectAttributes redirectAttributes) {
        Utilisateur receveur = (Utilisateur) session.getAttribute("utilisateur");
        if (relationAmisService.refuserDemandeAmis(idDemandeur, receveur.getIdU())) {
            redirectAttributes.addFlashAttribute("message", "Demande refusée !");
        }
        return "redirect:/";
    }
    
    /**
     * Supprime une relation d'amitié entre l'utilisateur connecté et un autre utilisateur.
     *
     * @param idUtilisateur identifiant de l'ami à supprimer
     * @param session session contenant l'utilisateur connecté
     * @return redirection vers la page des connexions de l'utilisateur
     */
    @GetMapping("/remove/{idUtilisateur}")
    public String removeAmi(@PathVariable("idUtilisateur") Long idUtilisateur,HttpSession session) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if(relationAmisService.findRelationByIds(idUtilisateur, utilisateur.getIdU())!=null){
        	relationAmisService.deleteRelationByIds(idUtilisateur, utilisateur.getIdU());
        }
    
        return "redirect:/utilisateurs/" + utilisateur.getIdU() + "/profile-connections";
    }
    
    
}