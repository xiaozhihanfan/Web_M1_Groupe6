package miage.groupe6.reseausocial.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.ActionEvenement;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.ActionEvenementService;
import miage.groupe6.reseausocial.model.jpa.service.EvenementsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class EvenementDetailControllerTest {

    @Mock
    private EvenementsService evenementService;

    @Mock
    private ActionEvenementService aes;

    @InjectMocks
    private EvenementDetailController controller;

    private HttpSession session;
    private Utilisateur currentUser;
    private Evenement event;

    @BeforeEach
    void setUp() {
        session = mock(HttpSession.class);
        currentUser = new Utilisateur();
        currentUser.setIdU(1L);
        when(session.getAttribute("utilisateur")).thenReturn(currentUser);

        event = new Evenement();
        // set owner to a different user by default
        Utilisateur owner = new Utilisateur();
        owner.setIdU(2L);
        event.setUtilisateur(owner);
    }


    @Test
    void detail_StatusInteress_ShowInscrireOnly() {
        when(evenementService.getEvenementAvecDetails(101L)).thenReturn(event);
        ActionEvenement ae = mock(ActionEvenement.class);
        when(ae.getStatut()).thenReturn(StatutActionEvenement.INTERESSER);
        when(aes.findByUserAndEvent(1L, 101L)).thenReturn(Optional.of(ae));
        when(aes.countInscriptions(101L)).thenReturn(0L);
        when(aes.countInteresses(101L)).thenReturn(1L);

        Model model = new ExtendedModelMap();
        controller.detail(101L, model, session);

        assertFalse((Boolean) model.getAttribute("hideAll"));
        assertTrue((Boolean) model.getAttribute("showInscrire"));
        assertFalse((Boolean) model.getAttribute("showInteress"));
    }

    @Test
    void detail_StatusInscrire_HideAll() {
        when(evenementService.getEvenementAvecDetails(102L)).thenReturn(event);
        ActionEvenement ae = mock(ActionEvenement.class);
        when(ae.getStatut()).thenReturn(StatutActionEvenement.INSCRIRE);
        when(aes.findByUserAndEvent(1L, 102L)).thenReturn(Optional.of(ae));

        Model model = new ExtendedModelMap();
        controller.detail(102L, model, session);

        assertTrue((Boolean) model.getAttribute("hideAll"));
        assertFalse((Boolean) model.getAttribute("showInscrire"));
        assertFalse((Boolean) model.getAttribute("showInteress"));
    }

    @Test
    void detail_CurrentUserIsOwner_HideAllEvenIfNoStatus() {
        // owner id equals currentUser id
        Utilisateur owner = new Utilisateur();
        owner.setIdU(1L);
        event.setUtilisateur(owner);
        when(evenementService.getEvenementAvecDetails(103L)).thenReturn(event);
        when(aes.findByUserAndEvent(1L, 103L)).thenReturn(Optional.empty());

        Model model = new ExtendedModelMap();
        controller.detail(103L, model, session);

        assertTrue((Boolean) model.getAttribute("hideAll"));
        assertFalse((Boolean) model.getAttribute("showInscrire"));
        assertFalse((Boolean) model.getAttribute("showInteress"));
    }

}
