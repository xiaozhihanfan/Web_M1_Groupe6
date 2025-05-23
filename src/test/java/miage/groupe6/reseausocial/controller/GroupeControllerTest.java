package miage.groupe6.reseausocial.controller;

import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.GroupeService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests d'intégration pour {@link GroupeController} utilisant Spring Boot.
 * <p>
 * Ces tests chargent tout le contexte Spring, y compris Thymeleaf et MockMvc.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class GroupeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupeService groupeService;

    @MockBean
    private RelationAmisService relationAmisService;

    @MockBean
    private UtilisateurService utilisateurService;

    private Utilisateur utilisateur;
    private MockHttpSession session;

    /**
     * Initialise un utilisateur simulé en session avant chaque test.
     */
    @BeforeEach
    void setup() {
        utilisateur = new Utilisateur();
        utilisateur.setIdU(1L);
        utilisateur.setNomU("Nom");
        utilisateur.setPrenomU("Prenom");

        session = new MockHttpSession();
        session.setAttribute("utilisateur", utilisateur);
    }


    /**
     * Teste la redirection vers la page de connexion si aucun utilisateur n'est connecté.
     *
     * @throws Exception si la requête échoue
     */
    @Test
    void testAfficherFormulaireGroupeNonConnecte() throws Exception {
        mockMvc.perform(get("/groupes/creer"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/utilisateurs/signin"));
    }

    /**
     * Teste la soumission d’un formulaire de groupe valide.
     *
     * @throws Exception si la requête échoue
     */
    @Test
    void testSoumettreFormulaireGroupe() throws Exception {
        mockMvc.perform(post("/groupes/creer")
                        .session(session)
                        .param("nomGroupe", "Test Groupe")
                        .param("description", "Description du test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/utilisateurs/1/profile-groups"));

        verify(groupeService, times(1)).creerGroupe(any(Groupe.class), eq(utilisateur));
    }

    

    /**
     * Teste l’invitation d’un utilisateur valide dans un groupe.
     *
     * @throws Exception si la requête échoue
     */
    @Test
    void testInviterUtilisateur() throws Exception {
        Groupe groupe = new Groupe();
        Utilisateur cible = new Utilisateur();
        cible.setIdU(2L);

        when(groupeService.getGroupeById(10L)).thenReturn(Optional.of(groupe));
        when(utilisateurService.getUtilisateurById(2L)).thenReturn(Optional.of(cible));

        mockMvc.perform(post("/groupes/10/inviter/2").session(session))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));

        verify(groupeService).ajouterMembreAuGroupe(cible, groupe);
    }

    /**
     * Teste l’échec d’invitation si le groupe n’existe pas.
     *
     * @throws Exception si la requête échoue
     */
    @Test
    void testInviterUtilisateurInvalide() throws Exception {
        when(groupeService.getGroupeById(10L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/groupes/10/inviter/2").session(session))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("error"));
    }

}
