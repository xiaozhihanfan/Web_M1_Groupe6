package miage.groupe6.reseausocial.controller;

import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;

import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UtilisateurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private Utilisateur utilisateur;

    private MockHttpSession session;

    @BeforeEach
    void setup() {

        utilisateurRepository.deleteAll();
        
        utilisateur = new Utilisateur();
        utilisateur.setEmailU("test@mail.com");
        utilisateur.setMpU("1234");
        utilisateur.setNomU("TestNom");
        utilisateur.setPrenomU("TestPrenom");

        utilisateur = utilisateurRepository.save(utilisateur); 

        session = new MockHttpSession();
        session.setAttribute("utilisateur", utilisateur); 
    }

    @Test
    void testAfficherFormulaireRechercher() throws Exception {
        mockMvc.perform(get("/utilisateurs/rechercher").session(session))
               .andExpect(status().isOk())
               .andExpect(view().name("rechercherUtilisateurs"));
    }

    @Test
    void testRechercherUtilisateurParEmail() throws Exception {
        mockMvc.perform(get("/utilisateurs/resultats")
                        .param("query", utilisateur.getEmailU())
                        .session(session))
               .andExpect(status().isOk())
               .andExpect(view().name("resultatsRechercherUtilisateurs"))
               .andExpect(content().string(containsString(utilisateur.getEmailU())))
               .andExpect(model().attributeExists("utilisateurs"));
    }

    @Test
    void testRedirectionVersConnexionSiNonConnecté() throws Exception {
        mockMvc.perform(get("/utilisateurs/rechercher")) 
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/utilisateurs/signin"));
    }
}
