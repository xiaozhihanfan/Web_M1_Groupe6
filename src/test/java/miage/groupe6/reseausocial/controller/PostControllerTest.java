package miage.groupe6.reseausocial.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.ActionPost;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.StatutActionPost;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.ActionPostService;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

class PostControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PostService ps;

    @Mock
    private ActionPostService aps;

    @Mock
    private UtilisateurService utilisateurService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    void testCreerPost() throws Exception {
        String contenuP = "Test post content";
        Utilisateur poster = new Utilisateur();
        poster.setIdU(1L);
        poster.setNomU("Test User");
        
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("utilisateur", poster);

        when(ps.save(any(Post.class))).thenAnswer(invocation -> {
            Post post = invocation.getArgument(0);
            post.setIdP(1L);
            return post;
        });

        mockMvc.perform(post("/posts/creerPost")
                .param("contenuP", contenuP)
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void testCreerPostImage() throws Exception {
        Post newPost = new Post();
        newPost.setContenuP("Test post with image");
        newPost.setImageP("asd");
        
        Utilisateur poster = new Utilisateur();
        poster.setIdU(1L);
        poster.setNomU("Test User");
        
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("utilisateur", poster);

        when(ps.save(any(Post.class))).thenAnswer(invocation -> {
            Post post = invocation.getArgument(0);
            post.setIdP(1L);
            return post;
        });

        mockMvc.perform(post("/posts/creerPostImage")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"contenuP\":\"Test post with image\",\"imageP\":\"base64_image_data\"}")
                .session(session))
                .andExpect(status().isOk());
    }

    @Test
    void testLikePostWhenUserNotLoggedIn() throws Exception {
        mockMvc.perform(get("/posts/1/like"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/utilisateurs/signin"));
    }

    @Test
    void testLikePostWhenPostNotFound() throws Exception {
        Utilisateur liker = new Utilisateur();
        liker.setIdU(1L);
        liker.setNomU("Test User");
        
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("utilisateur", liker);

        when(ps.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/posts/1/like")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void testLikePostSuccessfully() throws Exception {
        Utilisateur liker = new Utilisateur();
        liker.setIdU(1L);
        liker.setNomU("Test User");
        
        Post post = new Post();
        post.setIdP(1L);
        post.setContenuP("Test post");
        
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("utilisateur", liker);

        when(ps.findById(anyLong())).thenReturn(Optional.of(post));
        when(aps.findByUtilisateurAndPostAndStatut(any(), any(), any()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/posts/1/like")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void testCreerPostPageProfile() throws Exception {
        Utilisateur poster = new Utilisateur();
        poster.setIdU(1L);
        poster.setNomU("Test User");
        
        Post newPost = new Post();
        newPost.setContenuP("Test post for profile");
        
        when(utilisateurService.getUtilisateurById(anyLong()))
                .thenReturn(Optional.of(poster));
        when(ps.save(any(Post.class))).thenAnswer(invocation -> {
            Post post = invocation.getArgument(0);
            post.setIdP(1L);
            return post;
        });

        mockMvc.perform(post("/posts/creerPostPageProfile")
                .param("idU", "1")
                .param("contenuP", "Test post for profile")
                .sessionAttr("utilisateur", poster))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/utilisateurs/1/profile-post"));
    }

    @Test
    void testRepostWhenUserNotLoggedIn() throws Exception {
        mockMvc.perform(post("/posts/repost")
                .param("idP", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/utilisateurs/signin"));
    }

    @Test
    void testRepostSuccessfully() throws Exception {
        Utilisateur reposter = new Utilisateur();
        reposter.setIdU(1L);
        reposter.setNomU("Test User");
        
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("utilisateur", reposter);

        mockMvc.perform(post("/posts/repost")
                .param("idP", "1")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
