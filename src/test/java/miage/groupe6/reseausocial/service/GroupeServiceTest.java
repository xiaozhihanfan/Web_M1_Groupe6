package miage.groupe6.reseausocial.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.GroupeMembre;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.GroupeMembreRepository;
import miage.groupe6.reseausocial.model.jpa.repository.GroupeRepository;
import miage.groupe6.reseausocial.model.jpa.service.GroupeService;

/**
 * Classe de tests unitaires pour le service {@link GroupeService}.
 * <p>
 * Vérifie la création de groupes, l’ajout de membres, la récupération des membres et des groupes d’un utilisateur.
 */
public class GroupeServiceTest {
    @InjectMocks
    private GroupeService groupeService;

    @Mock
    private GroupeRepository groupeRepository;

    @Mock
    private GroupeMembreRepository groupeMembreRepository;

    private Utilisateur createur;
    private Groupe groupe;

    /**
     * Initialisation des objets communs avant chaque test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        createur = new Utilisateur();
        createur.setIdU(1L);
        createur.setNomU("Test");
        createur.setPrenomU("User");

        groupe = new Groupe();
        groupe.setIdGroupe(10L);
        groupe.setNomGroupe("Test Groupe");
        groupe.setCreateur(createur);
    }
    /**
     * Vérifie que la création d’un groupe enregistre le groupe
     * et ajoute le créateur en tant qu’administrateur.
     */
    @Test
    void testCreerGroupe_shouldCreateAndAssignAdmin() {
        when(groupeRepository.save(any(Groupe.class))).thenReturn(groupe);

        Groupe result = groupeService.creerGroupe(groupe, createur);

        assertNotNull(result);
        assertEquals(createur, result.getCreateur());
        verify(groupeRepository).save(groupe);
        verify(groupeMembreRepository).save(any(GroupeMembre.class));
    }

    /**
     * Vérifie que les membres d’un groupe sont correctement listés.
     */
    @Test
    void testListerMembres_shouldReturnUserList() {
        GroupeMembre membre1 = new GroupeMembre();
        membre1.setUtilisateur(createur);
        groupe.setMembres(Set.of(membre1));

        List<Utilisateur> membres = groupeService.listerMembres(groupe);

        assertEquals(1, membres.size());
        assertEquals(createur, membres.get(0));
    }

    /**
     * Vérifie l’ajout d’un utilisateur à un groupe s’il n’est pas déjà membre.
     */
    @Test
    void testAjouterMembreAuGroupe_shouldAddUser() {
        Utilisateur autre = new Utilisateur();
        autre.setIdU(2L);

        when(groupeMembreRepository.existsById(any())).thenReturn(false);

        groupeService.ajouterMembreAuGroupe(autre, groupe);

        verify(groupeMembreRepository).save(any(GroupeMembre.class));
    }


    /**
     * Vérifie qu’aucune opération n’est effectuée si l’utilisateur est déjà membre.
     */
    @Test
    void testAjouterMembreAuGroupe_shouldNotAddIfAlreadyExists() {
        Utilisateur autre = new Utilisateur();
        autre.setIdU(2L);

        when(groupeMembreRepository.existsById(any())).thenReturn(true);

        groupeService.ajouterMembreAuGroupe(autre, groupe);

        verify(groupeMembreRepository, never()).save(any());
    }

    /**
     * Vérifie la récupération d’un groupe par son identifiant.
     */
    @Test
    void testGetGroupeById_shouldReturnGroupe() {
        when(groupeRepository.findById(10L)).thenReturn(Optional.of(groupe));

        Optional<Groupe> result = groupeService.getGroupeById(10L);

        assertTrue(result.isPresent());
        assertEquals("Test Groupe", result.get().getNomGroupe());
    }

    /**
     * Vérifie que tous les groupes où un utilisateur est membre sont retournés.
     */
    @Test
    void testGetGroupesOuEtreMembre_shouldReturnGroupList() {
        GroupeMembre membre = new GroupeMembre();
        membre.setGroupe(groupe);

        when(groupeMembreRepository.findByUtilisateur(createur)).thenReturn(List.of(membre));

        List<Groupe> result = groupeService.getGroupesOuEtreMembre(createur);

        assertEquals(1, result.size());
        assertEquals(groupe, result.get(0));
    }
}
