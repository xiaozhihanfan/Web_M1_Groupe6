package miage.groupe6.reseausocial.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.RelationAmisRepository;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class RelationAmisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RelationAmisRepository relationAmisRepository;

    private Utilisateur demandeur;
    private Utilisateur cible;

    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        relationAmisRepository.deleteAll();
        utilisateurRepository.deleteAll();

        demandeur = new Utilisateur();
        demandeur.setEmailU("demandeur@mail.com");
        demandeur.setMpU("pwd");
        demandeur.setNomU("Demandeur");
        demandeur.setPrenomU("Test");
        utilisateurRepository.save(demandeur);

        cible = new Utilisateur();
        cible.setEmailU("cible@mail.com");
        cible.setMpU("pwd");
        cible.setNomU("Cible");
        cible.setPrenomU("User");
        utilisateurRepository.save(cible);

        session = new MockHttpSession();
        session.setAttribute("utilisateur", demandeur);
    }

    @Test
    void testEnvoyerDemandeAmiAvecSucces() throws Exception {
        mockMvc.perform(post("/relationamis/envoyer/" + cible.getIdU())
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/utilisateurs/resultats?query=" + cible.getEmailU()))
                .andExpect(flash().attributeExists("message"));
    }

    @Test
    void testEnvoyerDemandeAmiDejaExistante() throws Exception {
        
        mockMvc.perform(post("/relationamis/envoyer/" + cible.getIdU())
                .session(session)).andReturn();

        
        mockMvc.perform(post("/relationamis/envoyer/" + cible.getIdU())
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("info"));
    }

    @Test
    void testEnvoyerDemandeAmiUtilisateurInvalide() throws Exception {
        mockMvc.perform(post("/relationamis/envoyer/999999") 
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/utilisateurs/rechercher"))
                .andExpect(flash().attributeExists("erreur"));
    }

    @Test
    void testEnvoyerDemandeAmiSansConnexion() throws Exception {
        mockMvc.perform(post("/relationamis/envoyer/" + cible.getIdU()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/utilisateurs/signin"));
    }
}

