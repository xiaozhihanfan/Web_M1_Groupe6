package miage.groupe6.reseausocial.service;

import miage.groupe6.reseausocial.model.entity.RelationAmis;
import miage.groupe6.reseausocial.model.entity.RelationAmisId;
import miage.groupe6.reseausocial.model.entity.StatutRelation;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.RelationAmisRepository;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class RelationAmisServiceTest {

    @Mock
    private RelationAmisRepository rar;

    @InjectMocks
    private RelationAmisService service;

    private Utilisateur u1, u2, u3;

    @BeforeEach
    void setUp() {
        u1 = new Utilisateur(); u1.setIdU(1L);
        u2 = new Utilisateur(); u2.setIdU(2L);
        u3 = new Utilisateur(); u3.setIdU(3L);
    }

    @Test
    void countAmis_NoRelations_ReturnsZero() {
        when(rar.findByUtilisateurDemandeAndStatut(u1, StatutRelation.ACCEPTEE))
                .thenReturn(Collections.emptyList());
        when(rar.findByUtilisateurRecuAndStatut(u1, StatutRelation.ACCEPTEE))
                .thenReturn(Collections.emptyList());

        int count = service.countAmis(u1);
        assertEquals(0, count);
    }

    @Test
    void countAmis_Typical_ReturnsTwoDistinct() {
        RelationAmis rel1 = new RelationAmis(u1, u2, new Date(), StatutRelation.ACCEPTEE);
        RelationAmis rel2 = new RelationAmis(u3, u1, new Date(), StatutRelation.ACCEPTEE);
        when(rar.findByUtilisateurDemandeAndStatut(u1, StatutRelation.ACCEPTEE))
                .thenReturn(List.of(rel1));
        when(rar.findByUtilisateurRecuAndStatut(u1, StatutRelation.ACCEPTEE))
                .thenReturn(List.of(rel2));

        int count = service.countAmis(u1);
        assertEquals(2, count);
    }

    @Test
    void countAmis_Duplicates_ReturnsOne() {
        // u1→u2 and u2→u1 both accepted → one friend
        RelationAmis sent = new RelationAmis(u1, u2, new Date(), StatutRelation.ACCEPTEE);
        RelationAmis recv = new RelationAmis(u2, u1, new Date(), StatutRelation.ACCEPTEE);
        when(rar.findByUtilisateurDemandeAndStatut(u1, StatutRelation.ACCEPTEE))
                .thenReturn(List.of(sent));
        when(rar.findByUtilisateurRecuAndStatut(u1, StatutRelation.ACCEPTEE))
                .thenReturn(List.of(recv));

        int count = service.countAmis(u1);
        assertEquals(1, count);
    }

    @Test
    void demandeExisteDeja_TrueAndFalse() {
        RelationAmisId id = new RelationAmisId(1L, 2L);
        when(rar.existsById(id)).thenReturn(true);
        assertTrue(service.demandeExisteDeja(1L, 2L));
        when(rar.existsById(id)).thenReturn(false);
        assertFalse(service.demandeExisteDeja(1L, 2L));
    }

    @Test
    void envoyerDemandeAmi_NewRequest_SavesAndReturnsTrue() {
        when(rar.findByUtilisateurDemandeIdUAndUtilisateurRecuIdU(1L, 2L))
                .thenReturn(Optional.empty());
        ArgumentCaptor<RelationAmis> captor = ArgumentCaptor.forClass(RelationAmis.class);

        boolean result = service.envoyerDemandeAmi(u1, u2);
        assertTrue(result);
        verify(rar).save(captor.capture());
        RelationAmis saved = captor.getValue();
        assertEquals(u1, saved.getUtilisateurDemande());
        assertEquals(u2, saved.getUtilisateurRecu());
        assertEquals(StatutRelation.TRAITEE, saved.getStatut());
        assertNotNull(saved.getDateRelationAmis());
    }

    @Test
    void envoyerDemandeAmi_ExistingRequest_ReturnsFalse() {
        RelationAmis existing = new RelationAmis(u1, u2, new Date(), StatutRelation.TRAITEE);
        when(rar.findByUtilisateurDemandeIdUAndUtilisateurRecuIdU(1L, 2L))
                .thenReturn(Optional.of(existing));

        boolean result = service.envoyerDemandeAmi(u1, u2);
        assertFalse(result);
        verify(rar, never()).save(any());
    }

    @Test
    void getDemandesRecues_ReturnsList() {
        RelationAmis r = new RelationAmis(u3, u1, new Date(), StatutRelation.TRAITEE);
        when(rar.findByUtilisateurRecuAndStatut(u1, StatutRelation.TRAITEE))
                .thenReturn(List.of(r));

        List<RelationAmis> out = service.getDemandesRecues(u1);
        assertEquals(1, out.size());
        assertEquals(r, out.get(0));
    }

    @Test
    void accepterDemandeAmis_SuccessAndFailure() {
        RelationAmis rel = new RelationAmis(u1, u2, new Date(), StatutRelation.TRAITEE);
        RelationAmisId id = new RelationAmisId(1L, 2L);

        when(rar.findById(id)).thenReturn(Optional.of(rel));
        boolean ok = service.accepterDemandeAmis(1L, 2L);
        assertTrue(ok);
        assertEquals(StatutRelation.ACCEPTEE, rel.getStatut());
        verify(rar).save(rel);

        when(rar.findById(id)).thenReturn(Optional.empty());
        assertFalse(service.accepterDemandeAmis(1L, 2L));
    }

    @Test
    void refuserDemandeAmis_SuccessAndFailure() {
        RelationAmisId id = new RelationAmisId(1L, 2L);
        when(rar.existsById(id)).thenReturn(true);
        assertTrue(service.refuserDemandeAmis(1L, 2L));
        verify(rar).deleteById(id);

        when(rar.existsById(id)).thenReturn(false);
        assertFalse(service.refuserDemandeAmis(1L, 2L));
    }

    @Test
    void listerAmis_TypicalAndDuplicates() {
        RelationAmis sent = new RelationAmis(u1, u2, new Date(), StatutRelation.ACCEPTEE);
        RelationAmis recv = new RelationAmis(u3, u1, new Date(), StatutRelation.ACCEPTEE);
        RelationAmis dup  = new RelationAmis(u1, u2, new Date(), StatutRelation.ACCEPTEE);

        when(rar.findByUtilisateurDemandeAndStatut(u1, StatutRelation.ACCEPTEE))
                .thenReturn(List.of(sent, dup));
        when(rar.findByUtilisateurRecuAndStatut(u1, StatutRelation.ACCEPTEE))
                .thenReturn(List.of(recv));

        List<Utilisateur> friends = service.listerAmis(u1);
        assertEquals(2, friends.size());
        assertTrue(friends.containsAll(List.of(u2, u3)));
    }

    @Test
    void findRelationByIds_ReturnsList() {
        RelationAmis r = new RelationAmis(u1, u2, new Date(), StatutRelation.TRAITEE);
        when(rar.findRelationByIds(1L, 2L)).thenReturn(List.of(r));

        List<RelationAmis> out = service.findRelationByIds(1L, 2L);
        assertEquals(1, out.size());
        assertEquals(r, out.get(0));
    }

    @Test
    void deleteRelationByIds_CallsRepository() {
        // just verify deletion is invoked
        service.deleteRelationByIds(1L, 2L);
        verify(rar).deleteRelationByIds(1L, 2L);
    }
}
