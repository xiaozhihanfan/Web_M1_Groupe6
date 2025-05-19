package miage.groupe6.reseausocial.controller;

import miage.groupe6.reseausocial.model.dto.PasswordChangeForm;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Contrôleur MVC pour afficher et modifier le profil utilisateur via Thymeleaf.
 */
@Controller
@RequestMapping("/utilisateurs")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    /**
     * Affiche la page de modification de profil pour un utilisateur donné.
     *
     * @param id    identifiant de l'utilisateur
     * @param model le modèle pour transmettre les données à la vue
     * @return la page HTML edit-profile
     */
    @GetMapping("/{id}/modifier-info")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        Utilisateur utilisateur = settingsService.getUtilisateurById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'id : " + id));
    
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("passwordForm", new PasswordChangeForm());
    
        return "settingsInfo";
    }
    

    /**
     * Traite le formulaire de modification de profil.
     *
     * @param id             identifiant de l'utilisateur à modifier
     * @param utilisateurMod les nouvelles données du formulaire
     * @return redirection vers la même page (ou une autre page de confirmation)
     */
    @PostMapping("/{id}/modifier-info")
    public String modifierUtilisateur(@PathVariable Long id,
                                    @ModelAttribute("utilisateur") Utilisateur utilisateurMod,
                                    RedirectAttributes redirectAttributes) {
        try {
            settingsService.updateUtilisateur(id, utilisateurMod);
            redirectAttributes.addFlashAttribute("updateSuccess", true);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("updateError", e.getMessage());
        }
        return "redirect:/utilisateurs/" + id + "/modifier-info";
    }



    

    /**
     * Affiche le formulaire de modification du mot de passe
     * pour l’utilisateur spécifié.
     *
     * @param id    l’identifiant de l’utilisateur
     * @param model le modèle Spring MVC pour transmettre les données à la vue
     * @return le nom de la vue Thymeleaf pour le changement de mot de passe (settingsMdp)
     * @throws RuntimeException si l’utilisateur n’est pas trouvé
     */
    @GetMapping("/{id}/modifier-mdp")
    public String afficherPageModificationMotDePasse(@PathVariable Long id, Model model) {
        Utilisateur utilisateur = settingsService.getUtilisateurById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'id : " + id));

        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("passwordForm", new PasswordChangeForm());
        return "settingsMdp";
    }



    
    /**
     * Traite la soumission du formulaire de changement de mot de passe.
     *
     * @param id           l'identifiant de l'utilisateur
     * @param form         les données du formulaire de changement de mot de passe (ancien, nouveau, confirmation)
     * @param model        le modèle utilisé pour transmettre des attributs à la vue
     * @return la vue à afficher (la même page avec succès ou message d'erreur)
     */
    @PostMapping("/{id}/modifier-mdp")
    public String modifierMotDePasse(@PathVariable Long id,
                                    @ModelAttribute("passwordForm") PasswordChangeForm form,
                                    RedirectAttributes redirectAttributes) {
        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("passwordError", "Les deux mots de passe ne correspondent pas.");
            return "redirect:/utilisateurs/" + id + "/modifier";
        }

        try {
            settingsService.updatePassword(id, form.getCurrentPassword(), form.getNewPassword());
            redirectAttributes.addFlashAttribute("passwordSuccess", "Mot de passe modifié avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("passwordError", e.getMessage());
        }

        return "redirect:/utilisateurs/" + id + "/modifier-mdp";
    }

}