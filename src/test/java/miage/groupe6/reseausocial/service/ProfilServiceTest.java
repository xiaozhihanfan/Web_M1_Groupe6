package miage.groupe6.reseausocial.service;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.GroupeMembre;
import miage.groupe6.reseausocial.model.entity.MembreRole;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.EvenementRepository;
import miage.groupe6.reseausocial.model.jpa.repository.GroupeMembreRepository;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;
import miage.groupe6.reseausocial.model.jpa.service.ProfilService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ProfilServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;
    @Mock
    private EvenementRepository evenementRepository;
    @Mock
    private GroupeMembreRepository groupeMembreRepository;

    @InjectMocks
    private ProfilService service;

    private Utilisateur user;
    private Evenement evt1, evt2;
    private Groupe grp1, grp2;

    @BeforeEach
    void setUp() {
        user = new Utilisateur();
        user.setIdU(1L);

        evt1 = new Evenement();
        evt2 = new Evenement();

        grp1 = new Groupe();
        grp1.setIdGroupe(10L);
        grp2 = new Groupe();
        grp2.setIdGroupe(20L);
    }

    @Test
    void getProfileById_Success() {
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(user));

        Utilisateur result = service.getProfileById(1L);

        assertSame(user, result);
    }

    @Test
    void getProfileById_NotFound() {
        when(utilisateurRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> service.getProfileById(99L)
        );
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Utilisateur non trouvé : 99", ex.getReason());
    }

    @Test
    void getEvenementsCrees_Success() {
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(user));
        when(evenementRepository.findByUtilisateur(user)).thenReturn(List.of(evt1, evt2));

        List<Evenement> out = service.getEvenementsCrees(1L);

        assertEquals(2, out.size());
        assertTrue(out.containsAll(List.of(evt1, evt2)));
    }

    @Test
    void getEvenementsCrees_UserNotFound() {
        when(utilisateurRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> service.getEvenementsCrees(2L)
        );
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Utilisateur non trouvé : 2", ex.getReason());
    }

    @Test
    void getEvenementsInscrits_Success() {
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(user));
        when(evenementRepository.findByParticipantAndStatut(user, StatutActionEvenement.INSCRIRE))
            .thenReturn(List.of(evt1));

        List<Evenement> out = service.getEvenementsInscrits(1L);

        assertEquals(1, out.size());
        assertEquals(evt1, out.get(0));
    }

    @Test
    void getEvenementsInscrits_UserNotFound() {
        when(utilisateurRepository.findById(3L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> service.getEvenementsInscrits(3L)
        );
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Utilisateur non trouvé : 3", ex.getReason());
    }

    @Test
    void getEvenementsInteresses_Success() {
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(user));
        when(evenementRepository.findByParticipantAndStatut(user, StatutActionEvenement.INTERESSER))
            .thenReturn(List.of(evt2));

        List<Evenement> out = service.getEvenementsInteresses(1L);

        assertEquals(1, out.size());
        assertEquals(evt2, out.get(0));
    }

    @Test
    void getEvenementsInteresses_UserNotFound() {
        when(utilisateurRepository.findById(4L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> service.getEvenementsInteresses(4L)
        );
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Utilisateur non trouvé : 4", ex.getReason());
    }

    @Test
    void getGroupesAdmin_Success() {
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(user));
        GroupeMembre gm1 = mock(GroupeMembre.class);
        when(gm1.getGroupe()).thenReturn(grp1);
        when(groupeMembreRepository.findByUtilisateurAndRole(user, MembreRole.ADMIN))
            .thenReturn(List.of(gm1));

        List<Groupe> out = service.getGroupesAdmin(1L);

        assertEquals(1, out.size());
        assertEquals(grp1, out.get(0));
    }

    @Test
    void getGroupesAdmin_UserNotFound() {
        when(utilisateurRepository.findById(5L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> service.getGroupesAdmin(5L)
        );
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Utilisateur non trouvé : 5", ex.getReason());
    }

    @Test
    void getGroupesMembre_Success() {
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(user));
        GroupeMembre gm2 = mock(GroupeMembre.class);
        when(gm2.getGroupe()).thenReturn(grp2);
        when(groupeMembreRepository.findByUtilisateurAndRole(user, MembreRole.MEMBRE))
            .thenReturn(List.of(gm2));

        List<Groupe> out = service.getGroupesMembre(1L);

        assertEquals(1, out.size());
        assertEquals(grp2, out.get(0));
    }

    @Test
    void getGroupesMembre_UserNotFound() {
        when(utilisateurRepository.findById(6L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> service.getGroupesMembre(6L)
        );
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Utilisateur non trouvé : 6", ex.getReason());
    }
}
