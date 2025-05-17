package miage.groupe6.reseausocial.controller;

import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.ProfilService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
class ProfilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfilService profilService;

    @MockBean
    private PostService postService;

    @Test
    void afficherProfil_existingUser_rendersProfilePage() throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdU(7L);
        utilisateur.setNomU("Alice");
        utilisateur.setPrenomU("Martin");
        utilisateur.setEmailU("alice.martin@example.com");
        utilisateur.setBirthday(LocalDate.of(1992, 3, 1));
        utilisateur.setTelephone("0712345678");

        when(profilService.getProfileById(7L))
            .thenReturn(utilisateur);

        mockMvc.perform(get("/utilisateurs/7/profile-about"))
               .andExpect(status().isOk())
               .andExpect(view().name("my-profile-about"))
               .andExpect(model().attribute("utilisateur", utilisateur));

        verify(profilService).getProfileById(7L);
    }

    @Test
    void afficherProfil_userNotFound_returnsServerError() throws Exception {
        when(profilService.getProfileById(99L))
            .thenThrow(new RuntimeException("Utilisateur non trouvé : 99"));

        mockMvc.perform(get("/utilisateurs/99/profile-about"))
               .andExpect(status().is5xxServerError());

        verify(profilService).getProfileById(99L);
    }

        @Test
    void afficherProfilePost_existingUser_rendersProfilePostPageWithPosts() throws Exception {
        // Préparation
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdU(1L);
        // … complétez le profil …
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date1 = sdf.parse("2025-05-01T09:00:00");
        Post p1 = new Post();
        p1.setContenuP("Premier post");
        p1.setDateP(date1);

        Date date2 = sdf.parse("2025-05-02T15:30:00");
        Post p2 = new Post();
        p2.setContenuP("Deuxième post");
        p2.setDateP(date2);

        when(profilService.getProfileById(1L)).thenReturn(utilisateur);
        when(postService.findByAuteurOrderByDateDesc(utilisateur)).thenReturn(List.of(p2, p1));

        // Exécution & Vérification
        mockMvc.perform(get("/utilisateurs/1/profile-post"))
               .andExpect(status().isOk())
               .andExpect(view().name("my-profile-post"))
               .andExpect(model().attribute("utilisateur", utilisateur))
               .andExpect(model().attribute("posts", List.of(p2, p1)));

        verify(profilService).getProfileById(1L);
        verify(postService).findByAuteurOrderByDateDesc(utilisateur);
    }

    @Test
    void afficherProfilePost_userNotFound_returnsServerError() throws Exception {
        when(profilService.getProfileById(99L))
            .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé : 99"));

        mockMvc.perform(get("/utilisateurs/99/profile-post"))
               .andExpect(status().is5xxServerError());

        verify(profilService).getProfileById(99L);
        verifyNoInteractions(postService);
    }

    
}

