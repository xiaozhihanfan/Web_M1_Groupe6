package miage.groupe6.reseausocial.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import miage.groupe6.reseausocial.model.entity.ActionEvenement;
import miage.groupe6.reseausocial.model.entity.ActionEvenementId;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.ActionEvenementRepository;
import miage.groupe6.reseausocial.model.jpa.repository.EvenementRepository;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;
import miage.groupe6.reseausocial.model.jpa.service.ActionEvenementService;

@ExtendWith(MockitoExtension.class)
class ActionEvenementServiceTest {

    @Mock
    private ActionEvenementRepository aer;
    @Mock
    private UtilisateurRepository    ur;
    @Mock
    private EvenementRepository      er;

    @InjectMocks
    private ActionEvenementService   service;

    @Test
    @DisplayName("actOnEvent : exception si utilisateur introuvable")
    void actOnEvent_userNotFound() {
        given(ur.findById(1L)).willReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
            service.actOnEvent(1L, 2L, StatutActionEvenement.INSCRIRE)
        );

        assertTrue(ex.getMessage().contains("Utilisateur non trouvé : 1"));
    }

    @Test
    @DisplayName("actOnEvent : exception si événement introuvable")
    void actOnEvent_eventNotFound() {
        Utilisateur u = new Utilisateur(); u.setIdU(1L);
        given(ur.findById(1L)).willReturn(Optional.of(u));
        given(er.findById(2L)).willReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
            service.actOnEvent(1L, 2L, StatutActionEvenement.INTERESSER)
        );

        assertTrue(ex.getMessage().contains("Événement non trouvé : 2"));
    }

    @Test
    @DisplayName("actOnEvent : met à jour action existante")
    void actOnEvent_updatesExisting() {
        long uId = 10L, eId = 20L;
        Utilisateur u = new Utilisateur(); u.setIdU(uId);
        Evenement e = new Evenement();    e.setIdE(eId);
        ActionEvenement existing = new ActionEvenement();
        existing.setUtilisateur(u);
        existing.setEvenement(e);
        existing.setStatut(StatutActionEvenement.INSCRIRE);

        given(ur.findById(uId)).willReturn(Optional.of(u));
        given(er.findById(eId)).willReturn(Optional.of(e));
        given(aer.findByUtilisateurAndEvenement(u, e))
            .willReturn(Optional.of(existing));
        given(aer.save(existing)).willReturn(existing);

        ActionEvenement result = service.actOnEvent(uId, eId, StatutActionEvenement.INTERESSER);

        assertSame(existing, result);
        assertEquals(StatutActionEvenement.INTERESSER, result.getStatut());
        assertNotNull(result.getDateActionEvenemnt());
        then(aer).should().save(existing);
    }

    @Test
    @DisplayName("actOnEvent : crée nouvelle action si absente")
    void actOnEvent_createsNew() {
        long uId = 11L, eId = 22L;
        Utilisateur u = new Utilisateur(); u.setIdU(uId);
        Evenement e = new Evenement();    e.setIdE(eId);

        given(ur.findById(uId)).willReturn(Optional.of(u));
        given(er.findById(eId)).willReturn(Optional.of(e));
        given(aer.findByUtilisateurAndEvenement(u, e))
            .willReturn(Optional.empty());
        given(aer.save(any())).willAnswer(inv -> inv.getArgument(0));

        ActionEvenement result = service.actOnEvent(uId, eId, StatutActionEvenement.INSCRIRE);

        assertEquals(u, result.getUtilisateur());
        assertEquals(e, result.getEvenement());
        assertEquals(StatutActionEvenement.INSCRIRE, result.getStatut());
        assertNotNull(result.getDateActionEvenemnt());
        then(aer).should().save(result);
    }

    @Test
    @DisplayName("inscrireEvent et interesserEvent délèguent correctement")
    void delegateMethods() {
        long uId = 5L, eId = 6L;
        given(ur.findById(uId)).willReturn(Optional.of(new Utilisateur() {{ setIdU(uId); }}));
        given(er.findById(eId)).willReturn(Optional.of(new Evenement() {{ setIdE(eId); }}));
        given(aer.findByUtilisateurAndEvenement(any(), any())).willReturn(Optional.empty());
        given(aer.save(any())).willAnswer(inv -> inv.getArgument(0));

        ActionEvenement a1 = service.inscrireEvent(uId, eId);
        assertEquals(StatutActionEvenement.INSCRIRE, a1.getStatut());

        ActionEvenement a2 = service.interesserEvent(uId, eId);
        assertEquals(StatutActionEvenement.INTERESSER, a2.getStatut());
    }

    @Test
    @DisplayName("ajouterAction saute si existsById true")
    void ajouterAction_skipsWhenExists() {
        ActionEvenementId id = new ActionEvenementId(1L,2L);
        given(aer.existsById(id)).willReturn(true);

        service.ajouterAction(1L, 2L, StatutActionEvenement.INSCRIRE);
        then(aer).should(never()).save(any());
    }

    @Test
    @DisplayName("ajouterAction lève exception si événement absent")
    void ajouterAction_eventNotFound() {
        given(aer.existsById(any())).willReturn(false);
        given(er.findById(2L)).willReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            service.ajouterAction(1L, 2L, StatutActionEvenement.INTERESSER)
        );
        assertTrue(ex.getMessage().contains("Evenement introuvable : 2"));
    }

    @Test
    @DisplayName("ajouterAction lève exception si utilisateur absent")
    void ajouterAction_userNotFound() {
        given(aer.existsById(any())).willReturn(false);
        given(er.findById(3L)).willReturn(Optional.of(new Evenement() {{ setIdE(3L); }}));
        given(ur.findById(1L)).willReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            service.ajouterAction(1L, 3L, StatutActionEvenement.INSCRIRE)
        );
        assertTrue(ex.getMessage().contains("Utilisateur introuvable : 1"));
    }

    @Test
    @DisplayName("ajouterAction crée et sauvegarde nouvelle action")
    void ajouterAction_createsAndSaves() {
        long uId = 12L, eId = 24L;
        ActionEvenementId id = new ActionEvenementId(uId, eId);
        given(aer.existsById(id)).willReturn(false);
        Utilisateur u = new Utilisateur(); u.setIdU(uId);
        Evenement e = new Evenement();    e.setIdE(eId);
        given(er.findById(eId)).willReturn(Optional.of(e));
        given(ur.findById(uId)).willReturn(Optional.of(u));
        given(aer.save(any())).willAnswer(inv -> inv.getArgument(0));

        service.ajouterAction(uId, eId, StatutActionEvenement.INTERESSER);

        ArgumentCaptor<ActionEvenement> cap = ArgumentCaptor.forClass(ActionEvenement.class);
        then(aer).should().save(cap.capture());
        ActionEvenement saved = cap.getValue();
        assertEquals(StatutActionEvenement.INTERESSER, saved.getStatut());
        assertEquals(u, saved.getUtilisateur());
        assertEquals(e, saved.getEvenement());
        assertNotNull(saved.getDateActionEvenemnt());
    }

    @Test
    @DisplayName("countInscriptions & countInteresses retournent bien les compteurs")
    void countMethods() {
        given(aer.countByEvenementIdEAndStatut(5L, StatutActionEvenement.INSCRIRE))
            .willReturn(3L);
        given(aer.countByEvenementIdEAndStatut(5L, StatutActionEvenement.INTERESSER))
            .willReturn(4L);

        assertEquals(3L, service.countInscriptions(5L));
        assertEquals(4L, service.countInteresses(5L));
    }

    @Test
    @DisplayName("findByUserAndEvent : exception si utilisateur absent")
    void findByUserAndEvent_userNotFound() {
        given(ur.findById(9L)).willReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
            service.findByUserAndEvent(9L, 10L)
        );
        assertTrue(ex.getMessage().contains("Utilisateur non trouvé : 9"));
    }

    @Test
    @DisplayName("findByUserAndEvent : exception si événement absent")
    void findByUserAndEvent_eventNotFound() {
        Utilisateur u = new Utilisateur(); u.setIdU(9L);
        given(ur.findById(9L)).willReturn(Optional.of(u));
        given(er.findById(10L)).willReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
            service.findByUserAndEvent(9L, 10L)
        );
        assertTrue(ex.getMessage().contains("Événement non trouvé : 10"));
    }

    @Test
    @DisplayName("findByUserAndEvent retourne l’action si existante")
    void findByUserAndEvent_returnsOptional() {
        long uId = 21L, eId = 22L;
        Utilisateur u = new Utilisateur(); u.setIdU(uId);
        Evenement e = new Evenement();    e.setIdE(eId);
        ActionEvenement ae = new ActionEvenement();
        ae.setStatut(StatutActionEvenement.INSCRIRE);

        given(ur.findById(uId)).willReturn(Optional.of(u));
        given(er.findById(eId)).willReturn(Optional.of(e));
        given(aer.findByUtilisateurAndEvenement(u, e))
            .willReturn(Optional.of(ae));

        Optional<ActionEvenement> opt = service.findByUserAndEvent(uId, eId);
        assertTrue(opt.isPresent());
        assertEquals(StatutActionEvenement.INSCRIRE, opt.get().getStatut());
    }
}

