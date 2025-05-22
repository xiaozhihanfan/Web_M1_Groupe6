package miage.groupe6.reseausocial.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import miage.groupe6.reseausocial.model.entity.GroupeMembreId;


/**
 * Test unitaire de la méthode {@code setIdGroupe()} de la classe {@link GroupeMembreId}.
 */
public class GroupeMembreIdTest {
    /**
     * Vérifie que l’identifiant du groupe est correctement défini
     * via la méthode {@code setIdGroupe()}.
     */
    @Test
    void testSetIdGroupe() {
        GroupeMembreId id = new GroupeMembreId();
        id.setIdGroupe(42L);

        assertEquals(42L, id.getIdGroupe(),
                "L'identifiant du groupe devrait être égal à 42");
    }
}
