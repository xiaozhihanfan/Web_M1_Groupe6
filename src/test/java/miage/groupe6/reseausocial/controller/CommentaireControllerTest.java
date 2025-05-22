package miage.groupe6.reseausocial.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.CommentaireService;
import miage.groupe6.reseausocial.model.jpa.service.PostService;

public class CommentaireControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommentaireService cs;

    @Mock
    private PostService ps;

    @InjectMocks
    private CommentaireController commentaireController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(commentaireController).build();
    }

    @Test
    public void testAjouterCommentaire() throws Exception {
        // Mocking
        Utilisateur utilisateur = new Utilisateur();
        Post post = new Post();
        when(ps.findById(anyLong())).thenReturn(java.util.Optional.of(post));
        doNothing().when(cs).ajouterCommentaire(any(Utilisateur.class), any(Post.class), any(String.class));

        // Test
        mockMvc.perform(post("/commentaires/creer/1")
                .param("contenu", "Test commentaire")
                .sessionAttr("utilisateur", utilisateur))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        // Verification
        verify(ps, times(1)).findById(1L);
        verify(cs, times(1)).ajouterCommentaire(utilisateur, post, "Test commentaire");
    }

    @Test
    public void testAjouterCommentairePostNotFound() throws Exception {
        // Mocking
        when(ps.findById(anyLong())).thenReturn(java.util.Optional.empty());

        // Test
        mockMvc.perform(post("/commentaires/creer/1")
                .param("contenu", "Test commentaire")
                .sessionAttr("utilisateur", new Utilisateur()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        // Verification
        verify(ps, times(1)).findById(1L);
        verify(cs, times(0)).ajouterCommentaire(any(), any(), any());
    }

    @Test
    public void testAfficherAccueil() throws Exception {
        // Mocking
        Post post1 = new Post();
        Post post2 = new Post();
        List<Post> posts = Arrays.asList(post1, post2);
        when(ps.findAllPostsWithCommentaires()).thenReturn(posts);

        // Test
        mockMvc.perform(get("/commentaires/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attribute("posts", posts));

        // Verification
        verify(ps, times(1)).findAllPostsWithCommentaires();
    }
}
