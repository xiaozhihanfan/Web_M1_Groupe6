package miage.groupe6.reseausocial.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.PostService;

@WebMvcTest(IndexController.class)
public class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private MockHttpSession session;
    private Utilisateur utilisateur;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    	session = new MockHttpSession();
    }

    @Test
    public void testIndexWhenUserNotLoggedIn() throws Exception {
        mockMvc.perform(get("/"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/utilisateurs/signin"));
    }

    @Test
    public void testIndexWhenUserLoggedIn() throws Exception {
    	Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNomU("Doe");
        utilisateur.setPrenomU("John");
        utilisateur.setEmailU("john.doe@example.com");
        session.setAttribute("utilisateur", utilisateur);
        when(postService.countPostByUtilisateur(any(Utilisateur.class))).thenReturn(5);

        mockMvc.perform(get("/")
                .session(session))
               .andExpect(status().isOk())
               .andExpect(view().name("index"))
               .andExpect(model().attributeExists("utilisateur"))
               .andExpect(model().attributeExists("nbPost"))
               .andExpect(model().attribute("nbPost", 5));
    }
}
