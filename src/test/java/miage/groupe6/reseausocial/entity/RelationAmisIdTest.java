package miage.groupe6.reseausocial.entity;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import miage.groupe6.reseausocial.model.entity.RelationAmisId;

/**
 * Tests unitaires pour la classe {@link RelationAmisId}.
 * Vérifie le bon fonctionnement du constructeur, des accesseurs,
 * et des méthodes equals/hashCode.
 */
public class RelationAmisIdTest {

    /**
     * Vérifie que le constructeur avec arguments initialise correctement les champs.
     */
    @Test
    void testConstructeurAvecArguments() {
        RelationAmisId id = new RelationAmisId(1L, 2L);

        assertThat(id.getIdUtilisateurDemande()).isEqualTo(1L);
        assertThat(id.getIdUtilisateurRecu()).isEqualTo(2L);
    }

    /**
     * Vérifie le bon fonctionnement des getters et setters.
     */
    @Test
    void testGettersSetters() {
        RelationAmisId id = new RelationAmisId();
        id.setIdUtilisateurDemande(10L);
        id.setIdUtilisateurRecu(20L);

        assertThat(id.getIdUtilisateurDemande()).isEqualTo(10L);
        assertThat(id.getIdUtilisateurRecu()).isEqualTo(20L);
    }

    /**
     * Vérifie que deux identifiants avec mêmes valeurs sont considérés égaux.
     */
    @Test
    void testEqualsEtHashCode() {
        RelationAmisId id1 = new RelationAmisId(1L, 2L);
        RelationAmisId id2 = new RelationAmisId(1L, 2L);
        RelationAmisId id3 = new RelationAmisId(1L, 3L);

        assertThat(id1).isEqualTo(id2);
        assertThat(id1.hashCode()).isEqualTo(id2.hashCode());

        assertThat(id1).isNotEqualTo(id3);
    }

    /**
     * Vérifie que equals gère correctement les cas null et autres classes.
     */
    @Test
    void testEqualsAvecObjetsIncompatibles() {
        RelationAmisId id = new RelationAmisId(1L, 2L);

        assertThat(id.equals(null)).isFalse();
        assertThat(id.equals("not an id")).isFalse();
        assertThat(id.equals(id)).isTrue(); // Reflexivité
    }

    /**
     * Vérifie que la méthode setIdUtilisateurRecu()
     * attribue correctement l'identifiant du destinataire.
     */
    @Test
    void testSetIdUtilisateurRecu() {
        // GIVEN un objet RelationAmisId sans valeurs
        RelationAmisId id = new RelationAmisId();

        // WHEN on définit un identifiant destinataire
        id.setIdUtilisateurRecu(42L);

        // THEN il doit être correctement stocké
        assertThat(id.getIdUtilisateurRecu()).isEqualTo(42L);
    }
}
