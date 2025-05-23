package miage.groupe6.reseausocial.controller;

// import static org.mockito.BDDMockito.given;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import java.util.Optional;

// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.mock.web.MockHttpSession;
// import org.springframework.test.web.servlet.MockMvc;

// import miage.groupe6.reseausocial.model.entity.ActionEvenement;
// import miage.groupe6.reseausocial.model.entity.Evenement;
// import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
// import miage.groupe6.reseausocial.model.entity.Utilisateur;
// import miage.groupe6.reseausocial.model.jpa.service.ActionEvenementService;
// import miage.groupe6.reseausocial.model.jpa.service.EvenementsService;

// @WebMvcTest(EvenementDetailController.class)
class EvenementDetailControllerTest {

    // @Autowired
    // private MockMvc mockMvc;

    // @MockBean
    // private EvenementsService evenementService;

    // @MockBean
    // private ActionEvenementService aes;

    // /** 
    //  * Crée une session HTTP contenant un Utilisateur avec l’ID spécifié. 
    //  */
    // private MockHttpSession sessionWithUser(long userId) {
    //     MockHttpSession session = new MockHttpSession();
    //     Utilisateur u = new Utilisateur();
    //     u.setIdU(userId);
    //     session.setAttribute("utilisateur", u);
    //     return session;
    // }

    // @Test
    // @DisplayName("detail : si c'est le créateur, tout est caché")
    // void detailCreatorHidesAll() throws Exception {
    //     long eventId = 1L;
    //     long creatorId = 42L;
    //     Utilisateur creator = new Utilisateur();
    //     creator.setIdU(creatorId);

    //     Evenement ev = new Evenement();
    //     ev.setIdE(eventId);
    //     ev.setUtilisateur(creator);

    //     // stubbing
    //     given(evenementService.getEvenementAvecDetails(eventId)).willReturn(ev);
    //     given(aes.findByUserAndEvent(creatorId, eventId)).willReturn(Optional.empty());
    //     given(aes.countInscriptions(eventId)).willReturn(5L);
    //     given(aes.countInteresses(eventId)).willReturn(7L);

    //     mockMvc.perform(get("/evenements/{id}", eventId)
    //             .session(sessionWithUser(creatorId)))
    //         .andExpect(status().isOk())
    //         .andExpect(view().name("event-details"))
    //         .andExpect(model().attribute("hideAll", true))
    //         .andExpect(model().attribute("showInscrire", false))
    //         .andExpect(model().attribute("showInteress", false))
    //         .andExpect(model().attribute("evenement", ev))
    //         .andExpect(model().attribute("utilisateur", creator))
    //         .andExpect(model().attribute("nbInscriptions", 5L))
    //         .andExpect(model().attribute("nbInteresses", 7L));
    // }

    // @Test
    // @DisplayName("detail : sans action préalable, affiche les deux boutons")
    // void detailNoActionShowsButtons() throws Exception {
    //     long eventId = 2L;
    //     long userId = 100L;
    //     long creatorId = 200L;

    //     Utilisateur user = new Utilisateur();
    //     user.setIdU(userId);
    //     Utilisateur creator = new Utilisateur();
    //     creator.setIdU(creatorId);

    //     Evenement ev = new Evenement();
    //     ev.setIdE(eventId);
    //     ev.setUtilisateur(creator);

    //     given(evenementService.getEvenementAvecDetails(eventId)).willReturn(ev);
    //     given(aes.findByUserAndEvent(userId, eventId)).willReturn(Optional.empty());
    //     given(aes.countInscriptions(eventId)).willReturn(2L);
    //     given(aes.countInteresses(eventId)).willReturn(4L);

    //     mockMvc.perform(get("/evenements/{id}", eventId)
    //             .session(sessionWithUser(userId)))
    //         .andExpect(status().isOk())
    //         .andExpect(view().name("event-details"))
    //         .andExpect(model().attribute("hideAll", false))
    //         .andExpect(model().attribute("showInscrire", true))
    //         .andExpect(model().attribute("showInteress", true))
    //         .andExpect(model().attribute("nbInscriptions", 2L))
    //         .andExpect(model().attribute("nbInteresses", 4L));
    // }

    // @Test
    // @DisplayName("detail : statut INTERESSER → seul bouton S'inscrire visible")
    // void detailInteresserShowsInscrireOnly() throws Exception {
    //     long eventId = 3L;
    //     long userId = 101L;
    //     long creatorId = 202L;

    //     Utilisateur user = new Utilisateur();
    //     user.setIdU(userId);
    //     Utilisateur creator = new Utilisateur();
    //     creator.setIdU(creatorId);

    //     Evenement ev = new Evenement();
    //     ev.setIdE(eventId);
    //     ev.setUtilisateur(creator);

    //     ActionEvenement ae = new ActionEvenement();
    //     ae.setStatut(StatutActionEvenement.INTERESSER);

    //     given(evenementService.getEvenementAvecDetails(eventId)).willReturn(ev);
    //     given(aes.findByUserAndEvent(userId, eventId)).willReturn(Optional.of(ae));
    //     given(aes.countInscriptions(eventId)).willReturn(1L);
    //     given(aes.countInteresses(eventId)).willReturn(3L);

    //     mockMvc.perform(get("/evenements/{id}", eventId)
    //             .session(sessionWithUser(userId)))
    //         .andExpect(status().isOk())
    //         .andExpect(view().name("event-details"))
    //         .andExpect(model().attribute("hideAll", false))
    //         .andExpect(model().attribute("showInscrire", true))
    //         .andExpect(model().attribute("showInteress", false));
    // }

    // @Test
    // @DisplayName("detail : statut INSCRIRE → tout caché comme créateur")
    // void detailInscrireHidesAll() throws Exception {
    //     long eventId = 4L;
    //     long userId = 303L;
    //     long creatorId = 404L;

    //     Utilisateur user = new Utilisateur();
    //     user.setIdU(userId);
    //     Utilisateur creator = new Utilisateur();
    //     creator.setIdU(creatorId);

    //     Evenement ev = new Evenement();
    //     ev.setIdE(eventId);
    //     ev.setUtilisateur(creator);

    //     ActionEvenement ae = new ActionEvenement();
    //     ae.setStatut(StatutActionEvenement.INSCRIRE);

    //     given(evenementService.getEvenementAvecDetails(eventId)).willReturn(ev);
    //     given(aes.findByUserAndEvent(userId, eventId)).willReturn(Optional.of(ae));
    //     given(aes.countInscriptions(eventId)).willReturn(10L);
    //     given(aes.countInteresses(eventId)).willReturn(20L);

    //     mockMvc.perform(get("/evenements/{id}", eventId)
    //             .session(sessionWithUser(userId)))
    //         .andExpect(status().isOk())
    //         .andExpect(view().name("event-details"))
    //         .andExpect(model().attribute("hideAll", true))
    //         .andExpect(model().attribute("showInscrire", false))
    //         .andExpect(model().attribute("showInteress", false));
    // }
}
