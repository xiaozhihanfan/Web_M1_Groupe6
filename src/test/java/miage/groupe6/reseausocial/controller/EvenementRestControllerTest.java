package miage.groupe6.reseausocial.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.entity.ActionEvenement;
import miage.groupe6.reseausocial.model.entity.ActionEvenementId;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.jpa.service.ActionEvenementService;
import miage.groupe6.reseausocial.model.jpa.service.EvenementsService;

@SpringBootTest
@AutoConfigureMockMvc
class EvenementRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvenementsService evenementsService;

    @MockBean
    private ActionEvenementService actionEvenementService;

    private Utilisateur utilisateur;
    private MockHttpSession session;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        utilisateur = new Utilisateur();
        utilisateur.setIdU(1L);
        utilisateur.setEmailU("test@reseau.com");
        utilisateur.setNomU("Test");

        session = new MockHttpSession();
        session.setAttribute("utilisateur", utilisateur);
    }

    /**
     * Vérifie que la création d'un événement renvoie bien un objet Evenement JSON
     * contenant les champs attendus (titre, description, etc.).
     *
     * @throws Exception en cas d'erreur de la requête HTTP
     */
    @Test
    void testCreateEvenement_success() throws Exception {
        Evenement evenement = new Evenement();
        evenement.setTitre("Conférence");
        evenement.setDescriptionE("Test conférence");
        evenement.setDateDebut(new Date());
        evenement.setUtilisateur(utilisateur); // utilisateur mocké en @BeforeEach

        when(evenementsService.save(any(Evenement.class))).thenReturn(evenement);

        mockMvc.perform(post("/evenements/Creer")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(evenement)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titre").value("Conférence"))
                .andExpect(jsonPath("$.descriptionE").value("Test conférence"));
    }

    /**
     * Vérifie que l'action sur un événement (inscription) retourne correctement une réponse HTTP 200.
     */
    @Test
    void testActOnEvent_success() throws Exception {
        ActionEvenement ae = new ActionEvenement();
        ae.setId(new ActionEvenementId(1L, 100L)); 
        ae.setStatut(StatutActionEvenement.INSCRIRE);

        when(actionEvenementService.actOnEvent(1L, 100L, StatutActionEvenement.INSCRIRE)).thenReturn(ae);

        mockMvc.perform(post("/evenements/100/action/INSCRIRE")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statut").value("INSCRIRE"));
    }

    /**
     * Vérifie que l'appel sans session utilisateur renvoie HTTP 401 (non autorisé).
     */
    @Test
    void testActOnEvent_unauthorized() throws Exception {
        mockMvc.perform(post("/evenements/100/action/INTERESSER"))
                .andExpect(status().isUnauthorized());
    }
}