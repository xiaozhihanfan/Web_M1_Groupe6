package miage.groupe6.reseausocial.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import miage.groupe6.reseausocial.model.entity.ActionEvenementId;

/**
 * Tests unitaires pour la classe {@link ActionEvenementId}.
 */
class ActionEvenementIdTest {

    @Test
    @DisplayName("Constructeur par défaut initialise les champs à null")
    void testDefaultConstructor() {
        ActionEvenementId id = new ActionEvenementId();
        assertNull(id.getIdU(), "idU doit être null après le constructeur par défaut");
        assertNull(id.getIdE(), "idE doit être null après le constructeur par défaut");
    }

    @Test
    @DisplayName("Constructeur avec paramètres initialise correctement idE et idU")
    void testAllArgsConstructor() {
        Long expectedIdE = 100L;
        Long expectedIdU = 200L;
        ActionEvenementId id = new ActionEvenementId(expectedIdE, expectedIdU);
        assertEquals(expectedIdE, id.getIdE(), "Le constructeur doit initialiser idE");
        assertEquals(expectedIdU, id.getIdU(), "Le constructeur doit initialiser idU");
    }

    @Test
    @DisplayName("Getters et setters fonctionnent correctement")
    void testSettersAndGetters() {
        ActionEvenementId id = new ActionEvenementId();
        Long idE = 55L;
        Long idU = 77L;
        id.setIdE(idE);
        id.setIdU(idU);
        assertEquals(idE, id.getIdE(), "Le setter/getter de idE doit fonctionner");
        assertEquals(idU, id.getIdU(), "Le setter/getter de idU doit fonctionner");
    }

    @Test
    @DisplayName("equals et hashCode pour deux objets avec mêmes valeurs")
    void testEqualsAndHashCodeSameValues() {
        ActionEvenementId id1 = new ActionEvenementId(1L, 2L);
        ActionEvenementId id2 = new ActionEvenementId(1L, 2L);
        // réflexivité
        assertEquals(id1, id1, "Un objet doit être égal à lui-même");
        // symétrie
        assertEquals(id1, id2, "Deux objets avec mêmes champs doivent être égaux");
        assertEquals(id2, id1, "Symétrie de equals");
        // cohérence hashCode
        assertEquals(id1.hashCode(), id2.hashCode(),
            "Deux objets égaux doivent avoir le même hashCode");
    }

    @Test
    @DisplayName("equals retourne false pour null et objets d'autres classes")
    void testEqualsWithNullAndDifferentClass() {
        ActionEvenementId id = new ActionEvenementId(3L, 4L);
        assertNotEquals(null, id, "equals(null) doit retourner false");
        assertNotEquals("some string", id, "equals doit retourner false pour d'autres classes");
    }

    @Test
    @DisplayName("equals retourne false pour objets avec valeurs différentes")
    void testNotEqualsDifferentValues() {
        ActionEvenementId base = new ActionEvenementId(5L, 6L);
        ActionEvenementId diffE = new ActionEvenementId(7L, 6L);
        ActionEvenementId diffU = new ActionEvenementId(5L, 8L);
        assertNotEquals(base, diffE, "Objets avec idE différent ne doivent pas être égaux");
        assertNotEquals(base, diffU, "Objets avec idU différent ne doivent pas être égaux");
    }
}