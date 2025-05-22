package miage.groupe6.reseausocial.entity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.GroupeMembre;
import miage.groupe6.reseausocial.model.entity.GroupeMembreId;
import miage.groupe6.reseausocial.model.entity.MembreRole;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Classe de test unitaire pour l'entité {@link GroupeMembre}.
 * <p>
 * Vérifie les accesseurs, les constructeurs et les associations entre utilisateur et groupe.
 */
public class GroupeMembreTest {
    private Groupe groupe;
    private Utilisateur utilisateur;
    private GroupeMembre membre;

    /**
     * Prépare les objets nécessaires avant chaque test.
     */
    @BeforeEach
    void setUp() {
        groupe = new Groupe();
        groupe.setIdGroupe(1L);
        groupe.setNomGroupe("Test Groupe");

        utilisateur = new Utilisateur();
        utilisateur.setIdU(100L);
        utilisateur.setNomU("Durand");
        utilisateur.setPrenomU("Alice");

        membre = new GroupeMembre(
                MembreRole.MEMBRE,
                LocalDateTime.of(2024, 5, 1, 12, 0),
                groupe,
                utilisateur
        );
    }

    /**
     * Vérifie que le constructeur initialise correctement tous les champs.
     */
    @Test
    void testConstructeurComplet() {
        assertEquals(MembreRole.MEMBRE, membre.getRole());
        assertEquals(utilisateur, membre.getUtilisateur());
        assertEquals(groupe, membre.getGroupe());
        assertEquals(LocalDateTime.of(2024, 5, 1, 12, 0), membre.getDateAdhesion());
        assertNotNull(membre.getId());
        assertEquals(1L, membre.getId().getIdGroupe());
        assertEquals(100L, membre.getId().getIdUtilisateur());
    }

    /**
     * Vérifie les accesseurs (getters et setters) de l'entité.
     */
    @Test
    void testGettersEtSetters() {
        membre.setRole(MembreRole.ADMIN);
        assertEquals(MembreRole.ADMIN, membre.getRole());

        LocalDateTime nouvelleDate = LocalDateTime.of(2023, 1, 1, 10, 0);
        membre.setDateAdhesion(nouvelleDate);
        assertEquals(nouvelleDate, membre.getDateAdhesion());
    }

    /**
     * Vérifie l’assignation manuelle de l’identifiant composite.
     */
    @Test
    void testSetIdManuellement() {
        GroupeMembreId id = new GroupeMembreId(100L, 1L);
        membre.setId(id);
        assertEquals(100L, membre.getId().getIdGroupe());
        assertEquals(1L, membre.getId().getIdUtilisateur());
        
    }

        /**
     * Vérifie que l’identifiant du groupe est bien modifié
     * par l’appel de {@code setIdGroupe()}.
     */
    @Test
    void testSetIdGroupe() {
        Groupe groupe = new Groupe();
        groupe.setIdGroupe(123L);

        assertEquals(123L, groupe.getIdGroupe(),
                "L'identifiant du groupe doit être égal à 123");
    }

    /**
     * Vérifie que l’identifiant de l’utilisateur est correctement défini
     * via la méthode {@code setIdUtilisateur()}.
     */
    @Test
    void testSetIdUtilisateur() {
        GroupeMembreId id = new GroupeMembreId();
        id.setIdUtilisateur(123L);

        assertEquals(123L, id.getIdUtilisateur(),
                "L'identifiant de l'utilisateur devrait être égal à 123");
    }

    /**
     * Vérifie que deux instances identiques sont égales par référence.
     */
    @Test
    void testEquals_memeInstance() {
        GroupeMembreId id = new GroupeMembreId(1L, 100L);
        assertEquals(id, id);
    }

    /**
     * Vérifie que deux objets différents mais avec mêmes valeurs sont égaux.
     */
    @Test
    void testEquals_valeursIdentiques() {
        GroupeMembreId id1 = new GroupeMembreId(1L, 100L);
        GroupeMembreId id2 = new GroupeMembreId(1L, 100L);

        assertEquals(id1, id2, "Deux identifiants avec mêmes valeurs doivent être égaux");
    }

    /**
     * Vérifie que deux objets avec des valeurs différentes ne sont pas égaux.
     */
    @Test
    void testEquals_valeursDifferentes() {
        GroupeMembreId id1 = new GroupeMembreId(1L, 100L);
        GroupeMembreId id2 = new GroupeMembreId(2L, 200L);

        assertNotEquals(id1, id2, "Deux identifiants avec des valeurs différentes ne doivent pas être égaux");
    }

    /**
     * Vérifie que l’égalité avec {@code null} renvoie {@code false}.
     */
    @Test
    void testEquals_null() {
        GroupeMembreId id = new GroupeMembreId(1L, 100L);
        assertNotEquals(null, id);
    }

    /**
     * Vérifie que l’égalité avec un objet d’un autre type renvoie {@code false}.
     */
    @Test
    void testEquals_autreType() {
        GroupeMembreId id = new GroupeMembreId(1L, 100L);
        String autreObjet = "pas un identifiant";

        assertNotEquals(id, autreObjet);
    }

    /**
     * Vérifie que deux objets avec les mêmes valeurs ont le même code de hachage.
     */
    @Test
    void testHashCode_valeursIdentiques() {
        GroupeMembreId id1 = new GroupeMembreId(1L, 100L);
        GroupeMembreId id2 = new GroupeMembreId(1L, 100L);

        assertEquals(id1.hashCode(), id2.hashCode(),
                "Deux objets égaux doivent avoir le même code de hachage");
    }

    /**
     * Vérifie que deux objets différents peuvent avoir des codes de hachage différents.
     */
    @Test
    void testHashCode_valeursDifferentes() {
        GroupeMembreId id1 = new GroupeMembreId(1L, 100L);
        GroupeMembreId id2 = new GroupeMembreId(2L, 200L);

        assertNotEquals(id1.hashCode(), id2.hashCode(),
                "Deux objets différents peuvent avoir des codes de hachage différents");
    }

    /**
     * Vérifie que l'égalité logique entraîne l'égalité des hashCode.
     */
    @Test
    void testHashCode_consistenceAvecEquals() {
        GroupeMembreId id1 = new GroupeMembreId(3L, 300L);
        GroupeMembreId id2 = new GroupeMembreId(3L, 300L);

        assertTrue(id1.equals(id2), "Les objets doivent être égaux");
        assertEquals(id1.hashCode(), id2.hashCode(), "Le hashCode doit être identique pour des objets égaux");
    }

    /**
     * Vérifie que le groupe est correctement assigné via {@code setGroupe()}.
     */
    @Test
    void testSetGroupe() {
        GroupeMembre membre = new GroupeMembre();
        Groupe groupe = new Groupe();
        groupe.setIdGroupe(1L);
        groupe.setNomGroupe("Groupe Test");

        membre.setGroupe(groupe);

        assertEquals(groupe, membre.getGroupe(),
                "Le groupe du membre devrait correspondre au groupe assigné");
        assertEquals("Groupe Test", membre.getGroupe().getNomGroupe(),
                "Le nom du groupe assigné doit être correctement conservé");
    }
}
