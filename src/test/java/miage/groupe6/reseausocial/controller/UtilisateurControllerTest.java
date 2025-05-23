package miage.groupe6.reseausocial.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.EvenementsService;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

@WebMvcTest(UtilisateurController.class)
class UtilisateurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtilisateurService us;

    @MockBean
    private PostService ps;

    @MockBean
    private RelationAmisService ras;

    @MockBean
    private EvenementsService es;

    @Mock
    private Utilisateur utilisateur;

    @Mock
    private Utilisateur utilisateur2;

    @Mock
    private Evenement evenement;

    private MockHttpSession session;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    
    @BeforeEach
    void setUp() {

        session = new MockHttpSession();
       
        session.setAttribute("utilisateur", utilisateur);
        // Configure view resolver
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
                
    }

    @Test
    void testSignInPage() throws Exception {
        mockMvc.perform(get("/utilisateurs/signin"))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-in"));
    }

    @Test
    void testVerifierSignInSuccess() throws Exception {
        when(us.verifierSignIn(anyString(), anyString())).thenReturn(utilisateur);
        when(ps.countPostByUtilisateur(any())).thenReturn(5);

        mockMvc.perform(post("/utilisateurs/verifierSignIn")
                .param("email", "test@example.com")
                .param("password", "password")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void testVerifierSignInFailure() throws Exception {
        when(us.verifierSignIn(anyString(), anyString())).thenReturn(null);

        mockMvc.perform(post("/utilisateurs/verifierSignIn")
                .param("email", "wrong@example.com")
                .param("password", "wrongpassword")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-in"))
                .andExpect(model().attributeExists("erreur"));
    }

    @Test
    void testSignUpPage() throws Exception {
        mockMvc.perform(get("/utilisateurs/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-up"));
    }

    @Test
    void testVerifierSignUpSuccess() throws Exception {
        when(us.verifierSignUp(anyString(), anyString(), anyString(), anyString(), anyString(), 
                anyString(), anyString(), any(), anyString())).thenReturn("succès");

        mockMvc.perform(post("/utilisateurs/verifierSignUp")
                .param("email", "new@example.com")
                .param("password", "password")
                .param("confirmPassword", "password")
                .param("nomU", "Doe")
                .param("prenomU", "John")
                .param("universite", "University")
                .param("ville", "City")
                .param("birthday", "1990-01-01")
                .param("ine", "123456"))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-in"));
    }

    @Test
    void testVerifierSignUpFailure() throws Exception {
        when(us.verifierSignUp(anyString(), anyString(), anyString(), anyString(), anyString(), 
                anyString(), anyString(), any(), anyString())).thenReturn("Erreur de validation");

        mockMvc.perform(post("/utilisateurs/verifierSignUp")
                .param("email", "invalid@example.com")
                .param("password", "short")
                .param("confirmPassword", "mismatch")
                .param("nomU", "Doe")
                .param("prenomU", "John")
                .param("universite", "University")
                .param("ville", "City")
                .param("birthday", "1990-01-01")
                .param("ine", "123456"))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-up"))
                .andExpect(model().attributeExists("erreur", "email", "nomU", "prenomU", 
                        "universite", "ville", "birthday", "ine"));
    }

    @Test
    void testRechercherUtilisateursWhenNotLoggedIn() throws Exception {
        mockMvc.perform(get("/utilisateurs/resultats")
                .param("query", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/utilisateurs/signin"));
    }

    @Test
    void testRechercherUtilisateursByEmail() throws Exception {
        when(us.rechercherParEmail(anyString())).thenReturn(utilisateur2);
        when(ps.countPostByUtilisateur(any())).thenReturn(3);
        when(es.findAll()).thenReturn(new ArrayList<>());
        when(es.countEvenements(any())).thenReturn(2);
        when(ras.countAmis(any())).thenReturn(5);

        mockMvc.perform(get("/utilisateurs/resultats")
                .param("query", "test@example.com")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("resultatsRechercherUtilisateurs"))
                .andExpect(model().attributeExists("utilisateur", "nbPost", "nbAmis", "nbEvenement", 
                        "query", "utilisateurs", "nbPostsParUtilisateur", "nbEvenementsParUtilisateur", "exploreEvents"));
    }

    @Test
    void testRechercherUtilisateursByNomOuPrenom() throws Exception {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        utilisateurs.add(utilisateur2);
        when(us.rechercherParNomOuPrenom(anyString())).thenReturn(utilisateurs);
        when(ps.countPostByUtilisateur(any())).thenReturn(3);
        when(es.findAll()).thenReturn(new ArrayList<>());
        when(es.countEvenements(any())).thenReturn(2);
        when(ras.countAmis(any())).thenReturn(5);

        mockMvc.perform(get("/utilisateurs/resultats")
                .param("query", "John Doe")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("resultatsRechercherUtilisateurs"))
                .andExpect(model().attributeExists("utilisateur", "nbPost", "nbAmis", "nbEvenement", 
                        "query", "utilisateurs", "nbPostsParUtilisateur", "nbEvenementsParUtilisateur", "exploreEvents"));
    }
}
