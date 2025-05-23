package miage.groupe6.reseausocial.dto;


import org.junit.jupiter.api.Test;

import miage.groupe6.reseausocial.model.dto.PasswordChangeForm;

import static org.junit.jupiter.api.Assertions.*;

class PasswordChangeFormTest {

    @Test
    void initialValuesAreNull() {
        PasswordChangeForm form = new PasswordChangeForm();
        assertNull(form.getCurrentPassword(), "Le mot de passe actuel doit être null par défaut");
        assertNull(form.getNewPassword(), "Le nouveau mot de passe doit être null par défaut");
        assertNull(form.getConfirmPassword(), "La confirmation doit être null par défaut");
    }

    @Test
    void settersAndGettersWork() {
        PasswordChangeForm form = new PasswordChangeForm();

        form.setCurrentPassword("oldPass123");
        assertEquals("oldPass123", form.getCurrentPassword(), "getCurrentPassword doit retourner la valeur définie");

        form.setNewPassword("newPass456");
        assertEquals("newPass456", form.getNewPassword(), "getNewPassword doit retourner la valeur définie");

        form.setConfirmPassword("newPass456");
        assertEquals("newPass456", form.getConfirmPassword(), "getConfirmPassword doit retourner la valeur définie");
    }

    @Test
    void instancesAreIndependent() {
        PasswordChangeForm form1 = new PasswordChangeForm();
        PasswordChangeForm form2 = new PasswordChangeForm();

        form1.setCurrentPassword("a");
        form1.setNewPassword("b");
        form1.setConfirmPassword("c");

        // form2 must still have null fields
        assertNull(form2.getCurrentPassword(), "form2.currentPassword doit rester null");
        assertNull(form2.getNewPassword(), "form2.newPassword doit rester null");
        assertNull(form2.getConfirmPassword(), "form2.confirmPassword doit rester null");

        // And form1 retains its own values
        assertEquals("a", form1.getCurrentPassword());
        assertEquals("b", form1.getNewPassword());
        assertEquals("c", form1.getConfirmPassword());
    }
}

