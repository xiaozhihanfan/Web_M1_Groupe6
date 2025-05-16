package miage.groupe6.reseausocial;

import miage.groupe6.reseausocial.controller.SettingsController;
import miage.groupe6.reseausocial.controller.UtilisateurController;
import miage.groupe6.reseausocial.model.dto.PasswordChangeForm;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.SettingsService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SettingsController.class)
public class SettingsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SettingsService ss;

    @Test
    void afficherFormulaireModification_shouldRenderSettingsInfo() throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdU(1L);
        utilisateur.setNomU("Jean");

        Mockito.when(ss.getUtilisateurById(1L)).thenReturn(Optional.of(utilisateur));

        mockMvc.perform(get("/utilisateurs/1/modifier-info"))
                .andExpect(status().isOk())
                .andExpect(view().name("settingsInfo"))
                .andExpect(model().attributeExists("utilisateur"))
                .andExpect(model().attributeExists("passwordForm"));
    }

    @Test
    void modifierUtilisateur_shouldRedirectWithSuccess() throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdU(1L);
        utilisateur.setNomU("Jean");

        Mockito.when(ss.updateUtilisateur(anyLong(), any(Utilisateur.class)))
                .thenReturn(utilisateur);

        mockMvc.perform(post("/utilisateurs/1/modifier-info")
                        .param("nomU", "Jean")
                        .param("prenomU", "Dupont")
                        .param("emailU", "jean@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/utilisateurs/1/modifier-info"));
    }

    @Test
    void modifierMotDePasse_shouldSucceed_whenPasswordsMatch() throws Exception {
        Mockito.doNothing().when(ss).updatePassword(anyLong(), anyString(), anyString());

        mockMvc.perform(post("/utilisateurs/1/modifier-mdp")
                        .param("currentPassword", "oldpass")
                        .param("newPassword", "newpass")
                        .param("confirmPassword", "newpass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/utilisateurs/1/modifier-mdp"));
    }

    @Test
    void modifierMotDePasse_shouldFail_whenPasswordsMismatch() throws Exception {
        mockMvc.perform(post("/utilisateurs/1/modifier-mdp")
                        .param("currentPassword", "oldpass")
                        .param("newPassword", "newpass")
                        .param("confirmPassword", "wrongpass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("passwordError", "Les deux mots de passe ne correspondent pas."))
                .andExpect(redirectedUrl("/utilisateurs/1/modifier"));
    }
}