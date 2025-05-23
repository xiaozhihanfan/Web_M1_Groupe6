package miage.groupe6.reseausocial.controller;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.BDDMockito.given;
// import static org.mockito.Mockito.verify;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.mock.web.MockHttpSession;
// import org.springframework.test.web.servlet.MockMvc;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import miage.groupe6.reseausocial.model.entity.ActionEvenement;
// import miage.groupe6.reseausocial.model.entity.Evenement;
// import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
// import miage.groupe6.reseausocial.model.entity.Utilisateur;
// import miage.groupe6.reseausocial.model.jpa.service.ActionEvenementService;
// import miage.groupe6.reseausocial.model.jpa.service.EvenementsService;

// @WebMvcTest(EvenementRestController.class)
class EvenementRestControllerTest {

    // @Autowired
    // private MockMvc mockMvc;

    // @Autowired
    // private ObjectMapper objectMapper;

    // @MockBean
    // private EvenementsService es;

    // @MockBean
    // private ActionEvenementService aes;

    // private MockHttpSession sessionWithUser(long userId) {
    //     MockHttpSession session = new MockHttpSession();
    //     Utilisateur u = new Utilisateur();
    //     u.setIdU(userId);
    //     session.setAttribute("utilisateur", u);
    //     return session;
    // }

    // @Test
    // @DisplayName("POST /evenements/Creer → crée un événement et retourne HTTP 200 + JSON")
    // void createEvenementReturnsSaved() throws Exception {
    //     // Préparer l'objet envoyé
    //     Evenement incoming = new Evenement();
    //     incoming.setTitre("TestEvent");

    //     // Préparer la session utilisateur
    //     long userId = 11L;
    //     Utilisateur user = new Utilisateur();
    //     user.setIdU(userId);

    //     // Stub du service de sauvegarde
    //     Evenement saved = new Evenement();
    //     saved.setIdE(123L);
    //     saved.setTitre("TestEvent");
    //     given(es.save(any(Evenement.class))).willReturn(saved);

    //     mockMvc.perform(post("/evenements/Creer")
    //             .session(sessionWithUser(userId))
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(incoming)))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //         .andExpect(jsonPath("$.idE").value(123))
    //         .andExpect(jsonPath("$.titre").value("TestEvent"));

    //     // Vérifier que l'entité envoyée au service a bien reçu l'utilisateur
    //     verify(es).save(eq(new Evenement() {{
    //         setTitre("TestEvent");
    //         setUtilisateur(user);
    //     }}));
    // }

    // @Test
    // @DisplayName("POST /evenements/{id}/action/{statut} → retourne 401 si non connecté")
    // void actOnEventUnauthorized() throws Exception {
    //     mockMvc.perform(post("/evenements/50/action/INSCRIRE"))
    //         .andExpect(status().isUnauthorized());
    // }

    // @Test
    // @DisplayName("POST /evenements/{id}/action/{statut} → enregistre action et retourne 200 + JSON")
    // void actOnEventSuccess() throws Exception {
    //     long eventId = 77L;
    //     long userId  = 33L;
    //     Utilisateur user = new Utilisateur();
    //     user.setIdU(userId);

    //     // Stub : l'action renvoyée
    //     ActionEvenement ae = new ActionEvenement();
    //     ae.setStatut(StatutActionEvenement.INSCRIRE);
    //     given(aes.actOnEvent(userId, eventId, StatutActionEvenement.INSCRIRE)).willReturn(ae);

    //     mockMvc.perform(post("/evenements/{id}/action/{statut}", eventId, "INSCRIT")
    //             .session(sessionWithUser(userId)))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //         .andExpect(jsonPath("$.statut").value("INSCRIT"));

    //     verify(aes).actOnEvent(userId, eventId, StatutActionEvenement.INSCRIRE);
    // }
}
