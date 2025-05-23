package miage.groupe6.reseausocial.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.RelationAmis;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.ActionEvenementService;
import miage.groupe6.reseausocial.model.jpa.service.ActionPostService;
import miage.groupe6.reseausocial.model.jpa.service.EvenementsService;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;

@WebMvcTest(EvenementController.class)
class EvenementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean private PostService ps;
    @MockBean private EvenementsService es;
    @MockBean private ActionPostService aps;
    @MockBean private RelationAmisService relationAmisService;
    @MockBean private ActionEvenementService aes;

    private MockHttpSession sessionWithUser(Utilisateur user) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("utilisateur", user);
        return session;
    }

    @Test
    @DisplayName("GET /evenements/index → redirige vers signin si non authentifié")
    void indexRedirectsWhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/evenements/index"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/utilisateurs/signin"));
    }


    @Test
    @DisplayName("POST /evenements → crée un événement et retourne JSON")
    void createEvenementReturnsSavedEntity() throws Exception {
        Utilisateur user = new Utilisateur();
        user.setIdU(30L);

        Evenement incoming = new Evenement();
        incoming.setTitre("MonEvent");

        Evenement saved = new Evenement();
        saved.setIdE(40L);
        saved.setTitre("MonEvent");

        given(es.save(any(Evenement.class))).willReturn(saved);

        mockMvc.perform(post("/evenements")
                .session(sessionWithUser(user))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(incoming)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idE").value(40))
            .andExpect(jsonPath("$.titre").value("MonEvent"));

        // Vérifier que l'utilisateur a été lié au nouvel événement
        verify(es).save(argThat(evt ->
            evt.getUtilisateur() != null
            && evt.getUtilisateur().getIdU().equals(30L)
        ));
    }

    @Test
    @DisplayName("POST /evenements/evenements/{id}/action → redirect login si non authentifié")
    void actionEventRedirectsToLoginWhenNotAuthenticated() throws Exception {
        mockMvc.perform(post("/evenements/evenements/55/action")
                .param("statut", StatutActionEvenement.INTERESSER.name()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/login"));
    }

    @Test
    @DisplayName("POST /evenements/evenements/{id}/action → enregistre action et redirige")
    void actionEventRecordsAndRedirectsWhenAuthenticated() throws Exception {
        Utilisateur user = new Utilisateur(); user.setIdU(70L);
        long eventId = 66L;

        mockMvc.perform(post("/evenements/evenements/{id}/action", eventId)
                .param("statut", StatutActionEvenement.INSCRIRE.name())
                .session(sessionWithUser(user)))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/evenements/" + eventId));

        verify(aes).actOnEvent(70L, eventId, StatutActionEvenement.INSCRIRE);
    }
}
