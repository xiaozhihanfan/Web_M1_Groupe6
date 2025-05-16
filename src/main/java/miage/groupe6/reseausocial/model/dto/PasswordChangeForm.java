package miage.groupe6.reseausocial.model.dto;

/**
 * DTO pour le formulaire de changement de mot de passe.
 * Contient les champs nécessaires pour valider un changement sécurisé :
 * - mot de passe actuel
 * - nouveau mot de passe
 * - confirmation du nouveau mot de passe
 */
public class PasswordChangeForm {

    /** Mot de passe actuel saisi par l'utilisateur */
    private String currentPassword;

    /** Nouveau mot de passe souhaité */
    private String newPassword;

    /** Confirmation du nouveau mot de passe */
    private String confirmPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
