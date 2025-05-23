package miage.groupe6.reseausocial.entity;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.GroupeMembre;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Classe de test unitaire pour {@link Groupe}.
 * <p>
 * Teste les méthodes getter, setter, constructeur complet et {@code toString}.
 * Ce test ne contient aucun accès à la base de données.
 */
public class GroupeTest {

    private Groupe groupe;
    private Utilisateur createur;
    private Evenement evenement;
    private Set<GroupeMembre> membres;

    /**
     * Initialise les objets utilisés dans chaque test.
     */
    @BeforeEach
    void setUp() {
        createur = new Utilisateur(); // instance factice
        evenement = new Evenement();  // instance factice
        membres = new HashSet<>();

        groupe = new Groupe();
        groupe.setIdGroupe(1L);
        groupe.setNomGroupe("NomTest");
        groupe.setDescription("DescriptionTest");
        groupe.setAvatarG("avatar.png");
        groupe.setCreateur(createur);
        groupe.setEvenement(evenement);
        groupe.setMembres(membres);
    }

    /**
     * Vérifie que tous les getters renvoient les valeurs correctes
     * après utilisation des setters.
     */
    @Test
    void testGetters() {
        assertEquals(1L, groupe.getIdGroupe(), "L'id du groupe doit être 1");
        assertEquals("NomTest", groupe.getNomGroupe(), "Le nom du groupe est incorrect");
        assertEquals("DescriptionTest", groupe.getDescription(), "La description est incorrecte");
        assertEquals("avatar.png", groupe.getAvatarG(), "L'avatar est incorrect");
        assertEquals(createur, groupe.getCreateur(), "Le créateur est incorrect");
        assertEquals(evenement, groupe.getEvenement(), "L'événement est incorrect");
        assertEquals(membres, groupe.getMembres(), "Les membres ne correspondent pas");
    }

    /**
     * Teste le constructeur avec tous les arguments pour s'assurer
     * que les valeurs sont correctement assignées.
     */
    @Test
    void testAllArgsConstructor() {
        Groupe g = new Groupe(2L, "GroupeX", "Test desc", "img.jpg", createur, evenement, membres);

        assertEquals(2L, g.getIdGroupe());
        assertEquals("GroupeX", g.getNomGroupe());
        assertEquals("Test desc", g.getDescription());
        assertEquals("img.jpg", g.getAvatarG());
        assertEquals(createur, g.getCreateur());
        assertEquals(evenement, g.getEvenement());
        assertEquals(membres, g.getMembres());
    }

    /**
     * Vérifie la méthode {@code toString()} du groupe pour s'assurer
     * qu'elle retourne la chaîne attendue.
     */
    @Test
    void testToString() {
        String expected = "Groupe [idGroupe=1, nomGroupe=NomTest, description=DescriptionTest]";
        assertEquals(expected, groupe.toString(), "Le toString ne correspond pas à la valeur attendue");
    }
}
