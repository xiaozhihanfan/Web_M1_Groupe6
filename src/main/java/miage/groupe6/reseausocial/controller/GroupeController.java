package miage.groupe6.reseausocial.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.GroupeService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

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
    @GetMapping("/creer")
    public String afficherFormulaireGroupe(Model model, HttpSession session) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur == null) {
            return "redirect:/utilisateurs/signin";
        }

        model.addAttribute("groupe", new Groupe());
        return "creerGroupe";

    }

    @PostMapping("/creer")
    public String soumettreFormulaireGroupe(@ModelAttribute Groupe groupe, HttpSession session) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur == null) {
            return "redirect:/utilisateurs/signin";
        }

        groupeService.creerGroupe(groupe, utilisateur);

        return "index"; //跳转到 /groupes/liste 以后展示列表
    }

    // -------------- us 3.2 inviter mes amis dans une groupe ------------------
    @GetMapping("/{idGroupe}/inviter")
    public String afficherAmisInvitables(@PathVariable Long idGroupe, HttpSession session, Model model) {
        Utilisateur u1 = (Utilisateur) session.getAttribute("utilisateur");

        Groupe groupe = groupeService.getGroupeById(idGroupe).orElse(null);
        if(groupe == null || u1 == null) {
            return "redirect:/";
        }

        List<Utilisateur> mesAmis = relationAmisService.listerAmis(u1);
        List<Utilisateur> membresAmis = groupeService.listerMembres(groupe);

        mesAmis.removeAll(membresAmis);

        model.addAttribute("groupe", groupe);
        model.addAttribute("mesAmis", mesAmis);

        return "inviterGroupe";
    }

    @PostMapping("/{idGroupe}/inviter/{idUtilisateur}")
    public String inviterAmi(@PathVariable Long idGroupe,
                            @PathVariable Long idUtilisateur,
                            RedirectAttributes redirectAttributes) {
        Optional<Groupe> groupeOpt = groupeService.getGroupeById(idGroupe);
        Optional<Utilisateur> utilisateurOpt = utilisateurService.getUtilisateurById(idUtilisateur);

        if (groupeOpt.isPresent() && utilisateurOpt.isPresent()) {
            groupeService.ajouterMembreAuGroupe(utilisateurOpt.get(), groupeOpt.get());
            redirectAttributes.addFlashAttribute("message", "Des amis ont rejoint le groupe ！");
        }

        return "redirect:/groupes/" + idGroupe + "/inviter";
    }
}
