package miage.groupe6.reseausocial.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Optional;

import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtilisateurService utilisateurService;

    @MockBean
    private PostService postService;

    @Test
    void creerPostPageProfile_withValidUser_redirectsToProfile() throws Exception {
        Utilisateur user = new Utilisateur();
        user.setIdU(1L);
        when(utilisateurService.getUtilisateurById(1L))
            .thenReturn(Optional.of(user));

        mockMvc.perform(post("/posts/creerPostPageProfile")
                .param("idU", "1")
                .param("contenuP", "Bonjour tout le monde"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/utilisateurs/1/profile-post"));

        // on vérifie que le PostService a bien été appelé pour sauvegarder
        verify(postService).save(any(Post.class));
    }

    @Test
    void creerPostPageProfile_missingIdU_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/posts/creerPostPageProfile")
                .param("contenuP", "Je n’ai pas mis d’ID"))
               .andExpect(status().isBadRequest());
    }
}
