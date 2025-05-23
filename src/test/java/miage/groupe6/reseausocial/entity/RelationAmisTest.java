package miage.groupe6.reseausocial.entity;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import miage.groupe6.reseausocial.model.entity.RelationAmis;
import miage.groupe6.reseausocial.model.entity.RelationAmisId;
import miage.groupe6.reseausocial.model.entity.StatutRelation;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Tests unitaires pour l’entité {@link RelationAmis}.
 * Vérifie le comportement du constructeur, des accesseurs et de la clé composite.
 */
public class RelationAmisTest {
    /**
     * Vérifie que le constructeur complet initialise correctement tous les champs.
     */
    @Test
    void testConstructeurComplet() {
        Utilisateur u1 = new Utilisateur();
        u1.setIdU(1L);
        Utilisateur u2 = new Utilisateur();
        u2.setIdU(2L);
        Date date = new Date();

        RelationAmis relation = new RelationAmis(u1, u2, date, StatutRelation.ACCEPTEE);

        assertThat(relation.getUtilisateurDemande()).isEqualTo(u1);
        assertThat(relation.getUtilisateurRecu()).isEqualTo(u2);
        assertThat(relation.getDateRelationAmis()).isEqualTo(date);
        assertThat(relation.getStatut()).isEqualTo(StatutRelation.ACCEPTEE);
        assertThat(relation.getId()).isEqualTo(new RelationAmisId(1L, 2L));
    }

    /**
     * Vérifie les accesseurs (getters/setters) de l’entité RelationAmis.
     */
    @Test
    void testGettersEtSetters() {
        RelationAmis relation = new RelationAmis();
        Utilisateur demande = new Utilisateur(); demande.setIdU(10L);
        Utilisateur recu = new Utilisateur(); recu.setIdU(20L);
        Date now = new Date();

        RelationAmisId id = new RelationAmisId(10L, 20L);

        relation.setId(id);
        relation.setUtilisateurDemande(demande);
        relation.setUtilisateurRecu(recu);
        relation.setDateRelationAmis(now);
        relation.setStatut(StatutRelation.TRAITEE);

        assertThat(relation.getId()).isEqualTo(id);
        assertThat(relation.getUtilisateurDemande()).isEqualTo(demande);
        assertThat(relation.getUtilisateurRecu()).isEqualTo(recu);
        assertThat(relation.getDateRelationAmis()).isEqualTo(now);
        assertThat(relation.getStatut()).isEqualTo(StatutRelation.TRAITEE);
    }

    /**
     * Vérifie que deux relations avec la même clé composite sont égales.
     */
    @Test
    void testEqualsRelationAmisId() {
        RelationAmisId id1 = new RelationAmisId(1L, 2L);
        RelationAmisId id2 = new RelationAmisId(1L, 2L);

        assertThat(id1).isEqualTo(id2);
        assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
    }
}
