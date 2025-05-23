package miage.groupe6.reseausocial.controller;


// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// import java.util.Base64;
// import java.util.Optional;

// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.mockito.ArgumentCaptor;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.mock.web.MockHttpSession;
// import org.springframework.mock.web.MockMultipartFile;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MvcResult;
// import org.springframework.web.servlet.ModelAndView;

// import miage.groupe6.reseausocial.model.dto.PasswordChangeForm;
// import miage.groupe6.reseausocial.model.entity.Utilisateur;
// import miage.groupe6.reseausocial.model.jpa.service.SettingsService;
// import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

// @WebMvcTest(SettingsController.class)
class SettingsControllerTest {

    // @Autowired
    // private MockMvc mockMvc;

    // @MockBean
    // private SettingsService settingsService;

    // @MockBean
    // private UtilisateurService us;

    // private MockHttpSession sessionWithUser(long id) {
    //     Utilisateur u = new Utilisateur();
    //     u.setIdU(id);
    //     MockHttpSession session = new MockHttpSession();
    //     session.setAttribute("utilisateur", u);
    //     return session;
    // }

    // @Test
    // @DisplayName("GET /modifier-info : placeholder avatar si vide")
    // void getModifierInfoPlaceholder() throws Exception {
    //     Utilisateur u = new Utilisateur();
    //     u.setIdU(1L);
    //     u.setAvatarU("");
    //     when(settingsService.getUtilisateurById(1L)).thenReturn(Optional.of(u));

    //     MvcResult res = mockMvc.perform(
    //             org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/utilisateurs/1/modifier-info")
    //         ).andReturn();

    //     assertEquals(200, res.getResponse().getStatus());
    //     ModelAndView mav = res.getModelAndView();
    //     assertEquals("settingsInfo", mav.getViewName());

    //     Utilisateur modelUser = (Utilisateur) mav.getModel().get("utilisateur");
    //     assertEquals("/assets/images/avatar/placeholder.jpg", modelUser.getAvatarU());

    //     assertTrue(mav.getModel().get("passwordForm") instanceof PasswordChangeForm);
    //     verify(settingsService).getUtilisateurById(1L);
    // }

    // @Test
    // @DisplayName("GET /modifier-info : avatar conservé s'il existe")
    // void getModifierInfoWithAvatar() throws Exception {
    //     Utilisateur u = new Utilisateur();
    //     u.setIdU(2L);
    //     u.setAvatarU("/img/avatar.png");
    //     when(settingsService.getUtilisateurById(2L)).thenReturn(Optional.of(u));

    //     MvcResult res = mockMvc.perform(
    //             org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/utilisateurs/2/modifier-info")
    //         ).andReturn();

    //     assertEquals(200, res.getResponse().getStatus());
    //     ModelAndView mav = res.getModelAndView();
    //     assertEquals("settingsInfo", mav.getViewName());

    //     Utilisateur modelUser = (Utilisateur) mav.getModel().get("utilisateur");
    //     assertEquals("/img/avatar.png", modelUser.getAvatarU());
    //     verify(settingsService).getUtilisateurById(2L);
    // }

    // @Test
    // @DisplayName("GET /modifier-info : 500 si utilisateur introuvable")
    // void getModifierInfoNotFound() throws Exception {
    //     when(settingsService.getUtilisateurById(3L)).thenReturn(Optional.empty());

    //     MvcResult res = mockMvc.perform(
    //             org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/utilisateurs/3/modifier-info")
    //         ).andReturn();

    //     assertEquals(500, res.getResponse().getStatus());
    // }

    // @Test
    // @DisplayName("GET /modifier-mdp : affiche le formulaire")
    // void getModifierMdp() throws Exception {
    //     Utilisateur u = new Utilisateur();
    //     u.setIdU(4L);
    //     when(settingsService.getUtilisateurById(4L)).thenReturn(Optional.of(u));

    //     MvcResult res = mockMvc.perform(
    //             org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/utilisateurs/4/modifier-mdp")
    //         ).andReturn();

    //     assertEquals(200, res.getResponse().getStatus());
    //     ModelAndView mav = res.getModelAndView();
    //     assertEquals("settingsMdp", mav.getViewName());
    //     assertSame(u, mav.getModel().get("utilisateur"));
    //     assertTrue(mav.getModel().get("passwordForm") instanceof PasswordChangeForm);
    // }

    // @Test
    // @DisplayName("GET /modifier-mdp : 500 si utilisateur introuvable")
    // void getModifierMdpNotFound() throws Exception {
    //     when(settingsService.getUtilisateurById(5L)).thenReturn(Optional.empty());

    //     MvcResult res = mockMvc.perform(
    //             org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/utilisateurs/5/modifier-mdp")
    //         ).andReturn();

    //     assertEquals(500, res.getResponse().getStatus());
    // }

    // @Test
    // @DisplayName("POST /modifier-mdp : mismatch redirige avec erreur")
    // void postModifierMdpMismatch() throws Exception {
    //     MvcResult res = mockMvc.perform(
    //             org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/utilisateurs/6/modifier-mdp")
    //                 .param("currentPassword", "a")
    //                 .param("newPassword", "b")
    //                 .param("confirmPassword", "c")
    //         ).andReturn();

    //     assertEquals(302, res.getResponse().getStatus());
    //     assertEquals("redirect:/utilisateurs/6/modifier", res.getModelAndView().getViewName());
    //     assertEquals("Les deux mots de passe ne correspondent pas.",
    //         res.getFlashMap().get("passwordError"));
    // }

    // @Test
    // @DisplayName("POST /modifier-mdp : succès met à jour et redirige")
    // void postModifierMdpSuccess() throws Exception {
    //     MvcResult res = mockMvc.perform(
    //             org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/utilisateurs/7/modifier-mdp")
    //                 .param("currentPassword", "old")
    //                 .param("newPassword", "x")
    //                 .param("confirmPassword", "x")
    //         ).andReturn();

    //     assertEquals(302, res.getResponse().getStatus());
    //     assertEquals("redirect:/utilisateurs/7/modifier-mdp", res.getModelAndView().getViewName());
    //     assertEquals("Mot de passe modifié avec succès !",
    //         res.getFlashMap().get("passwordSuccess"));

    //     verify(settingsService).updatePassword(7L, "old", "x");
    // }

    // @Test
    // @DisplayName("POST /modifier-mdp : service exception flash erreur")
    // void postModifierMdpServiceError() throws Exception {
    //     doThrow(new RuntimeException("fail")).when(settingsService)
    //         .updatePassword(8L, "c", "c");

    //     MvcResult res = mockMvc.perform(
    //             org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/utilisateurs/8/modifier-mdp")
    //                 .param("currentPassword", "c")
    //                 .param("newPassword", "c")
    //                 .param("confirmPassword", "c")
    //         ).andReturn();

    //     assertEquals(302, res.getResponse().getStatus());
    //     assertEquals("redirect:/utilisateurs/8/modifier-mdp", res.getModelAndView().getViewName());
    //     assertEquals("fail", res.getFlashMap().get("passwordError"));
    // }

    // @Test
    // @DisplayName("POST /modifier-info : sans avatar met à jour et flash succès")
    // void postModifierInfoNoAvatar() throws Exception {
    //     MvcResult res = mockMvc.perform(
    //             org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/utilisateurs/9/modifier-info")
    //                 .contentType(MediaType.APPLICATION_FORM_URLENCODED)
    //         ).andReturn();

    //     assertEquals(302, res.getResponse().getStatus());
    //     assertEquals("redirect:/utilisateurs/9/modifier-info", res.getModelAndView().getViewName());
    //     assertEquals("Profil mis à jour avec succès !",
    //         res.getFlashMap().get("updateSuccess"));

    //     ArgumentCaptor<Utilisateur> cap = ArgumentCaptor.forClass(Utilisateur.class);
    //     verify(settingsService).updateUtilisateur(eq(9L), cap.capture());
    //     assertNotNull(cap.getValue());
    // }

    // @Test
    // @DisplayName("POST /modifier-info : avec avatar met à jour avatar et session")
    // void postModifierInfoWithAvatar() throws Exception {
    //     byte[] data = "hello".getBytes();
    //     MockMultipartFile file = new MockMultipartFile(
    //         "avatarFile", "avatar.png", "image/png", data
    //     );
    //     Utilisateur updated = new Utilisateur();
    //     updated.setIdU(10L);
    //     when(us.getUtilisateurById(10L)).thenReturn(Optional.of(updated));

    //     MvcResult res = mockMvc.perform(
    //             org.springframework.test.web.servlet.request.MockMvcRequestBuilders
    //                 .multipart("/utilisateurs/10/modifier-info")
    //                 .file(file)
    //                 .session(sessionWithUser(10L))
    //         ).andReturn();

    //     assertEquals(302, res.getResponse().getStatus());
    //     assertEquals("redirect:/utilisateurs/10/modifier-info", res.getModelAndView().getViewName());
    //     assertEquals("Profil mis à jour avec succès !",
    //         res.getFlashMap().get("updateSuccess"));

    //     ArgumentCaptor<String> urlCap = ArgumentCaptor.forClass(String.class);
    //     verify(settingsService).updateAvatarUrl(eq(10L), urlCap.capture());

    //     String encoded = urlCap.getValue();
    //     assertTrue(encoded.startsWith("data:image/png;base64,"));
    //     byte[] decoded = Base64.getDecoder().decode(encoded.split(",",2)[1]);
    //     assertArrayEquals(data, decoded);
    // }

    // @Test
    // @DisplayName("POST /modifier-info : service exception flash erreur")
    // void postModifierInfoServiceError() throws Exception {
    //     doThrow(new RuntimeException("err")).when(settingsService).updateUtilisateur(eq(11L), any());

    //     MvcResult res = mockMvc.perform(
    //             org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/utilisateurs/11/modifier-info")
    //                 .contentType(MediaType.APPLICATION_FORM_URLENCODED)
    //         ).andReturn();

    //     assertEquals(302, res.getResponse().getStatus());
    //     assertEquals("redirect:/utilisateurs/11/modifier-info", res.getModelAndView().getViewName());
    //     assertEquals("err", res.getFlashMap().get("updateError"));
    // }
}
