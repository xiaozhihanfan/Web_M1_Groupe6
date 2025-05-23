package miage.groupe6.reseausocial.service;

import jakarta.persistence.EntityNotFoundException;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.ActionEvenementRepository;
import miage.groupe6.reseausocial.model.jpa.repository.EvenementRepository;
import miage.groupe6.reseausocial.model.jpa.service.EvenementsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class EvenementsServiceTest {

    @Mock
    private EvenementRepository evenementRepository;

    @Mock
    private ActionEvenementRepository actionEvenementRepository;

    @InjectMocks
    private EvenementsService service;

    private Utilisateur user;
    private Evenement evt1, evt2;

    @BeforeEach
    void init() {
        user = new Utilisateur();
        user.setIdU(42L);

        evt1 = new Evenement();
        evt1.setIdE(1L);
        evt2 = new Evenement();
        evt2.setIdE(2L);
    }

    @Test
    void testSaveCallsRepositoryAndReturnsSaved() {
        when(evenementRepository.save(evt1)).thenReturn(evt1);

        Evenement returned = service.save(evt1);

        assertSame(evt1, returned);
        verify(evenementRepository, times(1)).save(evt1);
    }

    @Test
    void testFindExploreEventsDelegatesToRepository() {
        List<Evenement> expected = Arrays.asList(evt1, evt2);
        when(evenementRepository.findExploreEvents(user)).thenReturn(expected);

        List<Evenement> result = service.findExploreEvents(user);

        assertEquals(expected, result);
        verify(evenementRepository).findExploreEvents(user);
    }

    @Test
    void testGetEvenementReturnsWhenFound() {
        when(evenementRepository.findById(1L)).thenReturn(Optional.of(evt1));

        Evenement out = service.getEvenement(1L);

        assertSame(evt1, out);
    }

    @Test
    void testGetEvenementThrowsWhenNotFound() {
        when(evenementRepository.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
            EntityNotFoundException.class,
            () -> service.getEvenement(99L)
        );
        assertTrue(ex.getMessage().contains("Événement introuvable pour id = 99"));
    }

    @Test
    void testGetEvenementAvecDetailsReturnsWhenFound() {
        when(evenementRepository.findByIdWithDetails(2L)).thenReturn(Optional.of(evt2));

        Evenement out = service.getEvenementAvecDetails(2L);

        assertSame(evt2, out);
    }

    @Test
    void testGetEvenementAvecDetailsThrowsWhenNotFound() {
        when(evenementRepository.findByIdWithDetails(100L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
            EntityNotFoundException.class,
            () -> service.getEvenementAvecDetails(100L)
        );
        assertTrue(ex.getMessage().contains("Événement introuvable pour id = 100"));
    }

    @Test
    void testFindAllReturnsList() {
        List<Evenement> list = Collections.singletonList(evt1);
        when(evenementRepository.findAll()).thenReturn(list);

        List<Evenement> out = service.findAll();

        assertEquals(1, out.size());
        assertEquals(evt1, out.get(0));
        verify(evenementRepository).findAll();
    }
}

