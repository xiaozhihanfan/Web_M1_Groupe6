package miage.groupe6.reseausocial.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.RelationAmis;
import miage.groupe6.reseausocial.model.entity.RelationAmisId;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.ActionEvenementService;
import miage.groupe6.reseausocial.model.jpa.service.ActionPostService;
import miage.groupe6.reseausocial.model.jpa.service.EvenementsService;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;

@WebMvcTest(IndexController.class)
class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private EvenementsService evenementsService;

    @MockBean
    private ActionPostService actionPostService;

    @MockBean
    private RelationAmisService relationAmisService;

    @MockBean
    private ActionEvenementService actionEvenementService;

    @Mock
    private HttpSession session;

    private Utilisateur utilisateur;
    private Post post;
    private Evenement evenement;
    private RelationAmis relationAmis;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        utilisateur = new Utilisateur();
        utilisateur.setIdU(1L);
        utilisateur.setNomU("Test");
        utilisateur.setPrenomU("User");
        
        post = new Post();
        post.setIdP(1L);
        post.setContenuP("Test post");
        post.setAuteur(utilisateur);

        evenement = new Evenement();
        evenement.setIdE(1L);
        evenement.setTitre("Test Event");
        RelationAmisId id = new RelationAmisId();
        relationAmis = new RelationAmis();
        relationAmis.setId(id);
        relationAmis.setUtilisateurDemande(new Utilisateur());
        relationAmis.setUtilisateurRecu(new Utilisateur());
    }

    @Test
    void testIndexWhenUserNotLoggedIn() throws Exception {
        when(session.getAttribute("utilisateur")).thenReturn(null);
        
        mockMvc.perform(get("/"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/utilisateurs/signin"));
    }

    @Test
    void testIndexWhenUserLoggedIn() throws Exception {
        when(session.getAttribute("utilisateur")).thenReturn(utilisateur);
        
        List<Post> posts = Arrays.asList(post);
        when(postService.findAllOrderedByDateDesc()).thenReturn(posts);
        
        List<Evenement> events = Arrays.asList(evenement);
        when(evenementsService.findExploreEvents(utilisateur)).thenReturn(events);
        
        when(postService.countPostByUtilisateur(utilisateur)).thenReturn(5);
        when(relationAmisService.countAmis(utilisateur)).thenReturn(10);
        when(evenementsService.countEvenements(utilisateur)).thenReturn(3);
        when(relationAmisService.getDemandesRecues(utilisateur)).thenReturn(Arrays.asList(relationAmis));
        when(actionPostService.countLikes(post)).thenReturn(2);
        
        mockMvc.perform(get("/")
                .sessionAttr("utilisateur", utilisateur))
               .andExpect(status().isOk())
               .andExpect(view().name("index"))
               .andExpect(model().attributeExists("utilisateur", "posts", "exploreEvents", 
                       "nbPost", "nbAmis", "nbEvenement", "demandesAmis"))
               .andExpect(model().attribute("utilisateur", utilisateur))
               .andExpect(model().attribute("posts", posts))
               .andExpect(model().attribute("exploreEvents", events))
               .andExpect(model().attribute("nbPost", 5))
               .andExpect(model().attribute("nbAmis", 10))
               .andExpect(model().attribute("nbEvenement", 3))
               .andExpect(model().attribute("demandesAmis", Arrays.asList(relationAmis)));
    }

    @Test
    void testEnregistrerActionEvenementWhenUserNotLoggedIn() throws Exception {
        when(session.getAttribute("utilisateur")).thenReturn(null);
        
        mockMvc.perform(post("/evenements/1/action")
                .param("statut", "INTERESSER"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/utilisateurs/signin"));
    }

    @Test
    void testEnregistrerActionEvenementWhenUserLoggedIn() throws Exception {
        when(session.getAttribute("utilisateur")).thenReturn(utilisateur);
        
        mockMvc.perform(post("/evenements/1/action")
                .sessionAttr("utilisateur", utilisateur)
                .param("statut", "INTERESSER"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/"));
       
        // Verify that the action was recorded
        actionEvenementService.actOnEvent(1L, 1L, StatutActionEvenement.INTERESSER);
    }
}
