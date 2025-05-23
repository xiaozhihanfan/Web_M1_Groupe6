package miage.groupe6.reseausocial.controller;

import miage.groupe6.reseausocial.model.dto.PasswordChangeForm;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.SettingsService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class SettingsControllerTest {

    @Mock
    private SettingsService settingsService;

    @Mock
    private UtilisateurService us;

    @InjectMocks
    private SettingsController controller;

    @BeforeEach
    void init() {
        // inject dummy avatarsDir (not used in logic under test)
        ReflectionTestUtils.setField(controller, "avatarsDir", "/tmp/avatars");
    }

    @Test
    void afficherFormulaireModification_SetsPlaceholderWhenNoAvatar() {
        Utilisateur user = new Utilisateur();
        user.setAvatarU(null);
        when(settingsService.getUtilisateurById(1L)).thenReturn(Optional.of(user));

        Model model = new ExtendedModelMap();
        String view = controller.afficherFormulaireModification(1L, model);

        assertEquals("settingsInfo", view);
        Utilisateur mUser = (Utilisateur) model.getAttribute("utilisateur");
        assertEquals("/assets/images/avatar/placeholder.jpg", mUser.getAvatarU());
        assertTrue(model.getAttribute("passwordForm") instanceof PasswordChangeForm);
    }

    @Test
    void afficherFormulaireModification_ThrowsWhenNotFound() {
        when(settingsService.getUtilisateurById(2L)).thenReturn(Optional.empty());

        Model model = new ExtendedModelMap();
        RuntimeException ex = assertThrows(
            RuntimeException.class,
            () -> controller.afficherFormulaireModification(2L, model)
        );
        assertTrue(ex.getMessage().contains("Utilisateur introuvable avec l'id : 2"));
    }

    @Test
    void afficherPageModificationMotDePasse_Success() {
        Utilisateur user = new Utilisateur();
        user.setAvatarU("someUrl");
        when(settingsService.getUtilisateurById(3L)).thenReturn(Optional.of(user));

        Model model = new ExtendedModelMap();
        String view = controller.afficherPageModificationMotDePasse(3L, model);

        assertEquals("settingsMdp", view);
        assertSame(user, model.getAttribute("utilisateur"));
        assertTrue(model.getAttribute("passwordForm") instanceof PasswordChangeForm);
    }

    @Test
    void afficherPageModificationMotDePasse_ThrowsWhenNotFound() {
        when(settingsService.getUtilisateurById(4L)).thenReturn(Optional.empty());

        Model model = new ExtendedModelMap();
        RuntimeException ex = assertThrows(
            RuntimeException.class,
            () -> controller.afficherPageModificationMotDePasse(4L, model)
        );
        assertTrue(ex.getMessage().contains("Utilisateur introuvable avec l'id : 4"));
    }

    @Test
    void modifierMotDePasse_MismatchRedirectsWithError() {
        PasswordChangeForm form = new PasswordChangeForm();
        form.setNewPassword("abc");
        form.setConfirmPassword("xyz");
        RedirectAttributes attrs = new RedirectAttributesModelMap();

        String view = controller.modifierMotDePasse(5L, form, attrs);

        assertEquals("redirect:/utilisateurs/5/modifier", view);
        assertEquals("Les deux mots de passe ne correspondent pas.", attrs.getFlashAttributes().get("passwordError"));
    }

    @Test
    void modifierMotDePasse_SuccessAddsSuccessFlash() {
        PasswordChangeForm form = new PasswordChangeForm();
        form.setNewPassword("pass");
        form.setConfirmPassword("pass");
        form.setCurrentPassword("old");
        RedirectAttributes attrs = new RedirectAttributesModelMap();

        // do nothing on updatePassword
        String view = controller.modifierMotDePasse(6L, form, attrs);

        verify(settingsService).updatePassword(6L, "old", "pass");
        assertEquals("redirect:/utilisateurs/6/modifier-mdp", view);
        assertEquals("Mot de passe modifié avec succès !", attrs.getFlashAttributes().get("passwordSuccess"));
    }

    @Test
    void modifierMotDePasse_ServiceThrowsSetsErrorFlash() {
        PasswordChangeForm form = new PasswordChangeForm();
        form.setNewPassword("p");
        form.setConfirmPassword("p");
        form.setCurrentPassword("o");
        RedirectAttributes attrs = new RedirectAttributesModelMap();

        doThrow(new RuntimeException("bad")).when(settingsService).updatePassword(7L, "o", "p");

        String view = controller.modifierMotDePasse(7L, form, attrs);

        assertEquals("redirect:/utilisateurs/7/modifier-mdp", view);
        assertEquals("bad", attrs.getFlashAttributes().get("passwordError"));
    }

    @Test
    void modifierUtilisateur_NoAvatar_Success() {
        RedirectAttributes attrs = new RedirectAttributesModelMap();
        HttpSession session = mock(HttpSession.class);
        Utilisateur mod = new Utilisateur();

        // no avatarFile
        String view = controller.modifierUtilisateur(8L, mod, null, session, attrs);

        verify(settingsService).updateUtilisateur(8L, mod);
        assertEquals("redirect:/utilisateurs/8/modifier-info", view);
        assertEquals("Profil mis à jour avec succès !", attrs.getFlashAttributes().get("updateSuccess"));
        verifyNoMoreInteractions(settingsService);
    }

    @Test
    void modifierUtilisateur_WithAvatar_Success() throws IOException {
        RedirectAttributes attrs = new RedirectAttributesModelMap();
        HttpSession session = mock(HttpSession.class);
        MultipartFile file = mock(MultipartFile.class);
        Utilisateur user = new Utilisateur();

        when(file.isEmpty()).thenReturn(false);
        byte[] data = "img".getBytes();
        when(file.getBytes()).thenReturn(data);
        when(file.getContentType()).thenReturn("image/png");
        when(us.getUtilisateurById(9L)).thenReturn(Optional.of(user));

        String view = controller.modifierUtilisateur(9L, new Utilisateur(), file, session, attrs);

        verify(settingsService).updateUtilisateur(eq(9L), any());
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(settingsService).updateAvatarUrl(eq(9L), captor.capture());
        String url = captor.getValue();
        assertTrue(url.startsWith("data:image/png;base64,"));
        assertTrue(url.endsWith(Base64.getEncoder().encodeToString(data)));
        verify(session).setAttribute("utilisateur", user);
        assertEquals("redirect:/utilisateurs/9/modifier-info", view);
        assertEquals("Profil mis à jour avec succès !", attrs.getFlashAttributes().get("updateSuccess"));
    }

    @Test
    void modifierUtilisateur_AvatarIOException_SetsErrorFlash() throws IOException {
        RedirectAttributes attrs = new RedirectAttributesModelMap();
        HttpSession session = mock(HttpSession.class);
        MultipartFile file = mock(MultipartFile.class);
        Utilisateur mod = new Utilisateur();

        when(file.isEmpty()).thenReturn(false);
        when(file.getBytes()).thenThrow(new IOException("ioerr"));

        String view = controller.modifierUtilisateur(10L, mod, file, session, attrs);

        verify(settingsService).updateUtilisateur(10L, mod);
        assertEquals("redirect:/utilisateurs/10/modifier-info", view);
        assertEquals("Échec de l'upload de l'avatar : ioerr", attrs.getFlashAttributes().get("updateError"));
        verify(settingsService, never()).updateAvatarUrl(anyLong(), any());
    }

    @Test
    void modifierUtilisateur_ServiceThrows_SetsErrorFlash() {
        RedirectAttributes attrs = new RedirectAttributesModelMap();
        HttpSession session = mock(HttpSession.class);
        Utilisateur mod = new Utilisateur();

        doThrow(new RuntimeException("fail")).when(settingsService).updateUtilisateur(11L, mod);

        String view = controller.modifierUtilisateur(11L, mod, null, session, attrs);

        assertEquals("redirect:/utilisateurs/11/modifier-info", view);
        assertEquals("fail", attrs.getFlashAttributes().get("updateError"));
    }
}
