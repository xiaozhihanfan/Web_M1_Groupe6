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

     /**
     * Retourne le mot de passe actuel saisi.
     *
     * @return la valeur du mot de passe actuel
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * Définit le mot de passe actuel saisi.
     *
     * @param currentPassword le mot de passe actuel fourni par l'utilisateur
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    /**
     * Retourne le nouveau mot de passe souhaité.
     *
     * @return la valeur du nouveau mot de passe
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Définit le nouveau mot de passe souhaité.
     *
     * @param newPassword le nouveau mot de passe fourni par l'utilisateur
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Retourne la confirmation du nouveau mot de passe.
     *
     * @return la valeur de confirmation du mot de passe
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Définit la confirmation du nouveau mot de passe.
     *
     * @param confirmPassword la confirmation du nouveau mot de passe fournie par l'utilisateur
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}