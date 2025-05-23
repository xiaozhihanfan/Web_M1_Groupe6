package miage.groupe6.reseausocial.controller;

import miage.groupe6.reseausocial.model.dto.PasswordChangeForm;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.SettingsService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;


/**
 * Contrôleur MVC pour afficher et modifier le profil utilisateur via Thymeleaf.
 */
@Controller
@RequestMapping("/utilisateurs")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private UtilisateurService us;

    /**
     * Répertoire local pour stocker les avatars uploadés.
     * Doit être configuré dans application.properties : app.upload.avatars-dir
     */
    @Value("${app.upload.avatars-dir}")
    private String avatarsDir;

    /**
     * Affiche la page de modification de profil pour un utilisateur donné.
     *
     * @param id    identifiant de l'utilisateur
     * @param model le modèle pour transmettre les données à la vue
     * @return la page HTML edit-profile
     */
    @GetMapping("/{id}/modifier-info")
    public String afficherFormulaireModification(@PathVariable Long id,Model model) {
        Utilisateur utilisateur = settingsService.getUtilisateurById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'id : " + id));
    
        // Si pas d'avatar défini, utiliser le placeholder
        if (utilisateur.getAvatarU() == null || utilisateur.getAvatarU().trim().isEmpty()) {
            utilisateur.setAvatarU("/assets/images/avatar/placeholder.jpg");
        }

        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("passwordForm", new PasswordChangeForm());
        return "settingsInfo";
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

    /**
     * Traite la soumission du formulaire de modification de profil.
     * <p>
     * Met à jour les informations de l’utilisateur, gère l’upload de l’avatar
     * en le convertissant en Base64, et met à jour la session.
     * </p>
     *
     * @param id                 identifiant de l’utilisateur
     * @param utilisateurMod     objet Utilisateur contenant les nouvelles valeurs
     * @param avatarFile         fichier uploadé pour l’avatar (optionnel)
     * @param session            session HTTP pour mettre à jour l’attribut utilisateur
     * @param redirectAttributes pour passer les messages flash (succès ou erreur)
     * @return redirection vers la page de modification des informations
     */
    @PostMapping("/{id}/modifier-info")
    public String modifierUtilisateur(
            @PathVariable Long id,
            @ModelAttribute("utilisateur") Utilisateur utilisateurMod,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            settingsService.updateUtilisateur(id, utilisateurMod);
            System.out.println("avatarFile = " + avatarFile);
            if (avatarFile != null && !avatarFile.isEmpty()) {
                byte[] bytes = avatarFile.getBytes();
            
                String contentType = avatarFile.getContentType();
                String prefix = "data:" + (contentType != null ? contentType : "application/octet-stream") + ";base64,";
                
                String base64Body = Base64.getEncoder().encodeToString(bytes);
                String base64Url = prefix + base64Body;
                settingsService.updateAvatarUrl(id, base64Url);
                Utilisateur utilisateur = us.getUtilisateurById(id).get();
                session.setAttribute("utilisateur", utilisateur);
            }

            redirectAttributes.addFlashAttribute("updateSuccess", "Profil mis à jour avec succès !");
        } catch (IOException ioe) {
            redirectAttributes.addFlashAttribute("updateError", "Échec de l'upload de l'avatar : " + ioe.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("updateError", e.getMessage());
        }
        return "redirect:/utilisateurs/" + id + "/modifier-info";
    }

}