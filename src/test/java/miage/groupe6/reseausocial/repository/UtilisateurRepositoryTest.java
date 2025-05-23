package miage.groupe6.reseausocial.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;

/**
 * Tests unitaires pour le {@link UtilisateurRepository}.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UtilisateurRepositoryTest {

    @Autowired
    private UtilisateurRepository repository;

    private Utilisateur user1;
    private Utilisateur user2;
    private Utilisateur user3;

    @BeforeEach
    void setUp() {
        // Utilisateur 1
        user1 = new Utilisateur();
        user1.setEmailU("user1@example.com");
        user1.setNomU("Dupont");
        user1.setPrenomU("Alice");
        user1 = repository.save(user1);

        // Utilisateur 2 (email en majuscules pour tester ignoreCase)
        user2 = new Utilisateur();
        user2.setEmailU("USER2@EXAMPLE.COM");
        user2.setNomU("Martin");
        user2.setPrenomU("Bob");
        user2 = repository.save(user2);

        // Utilisateur 3 (nom et prénom partagent 'John')
        user3 = new Utilisateur();
        user3.setEmailU("john@example.com");
        user3.setNomU("Johnson");
        user3.setPrenomU("John");
        user3 = repository.save(user3);
    }

    @Test
    @DisplayName("findByEmailU retourne Optional contenant l'utilisateur existant")
    void testFindByEmailUFound() {
        Optional<Utilisateur> found = repository.findByEmailU("user1@example.com");
        assertTrue(found.isPresent(), "L'utilisateur doit être trouvé par son email exact");
        assertEquals(user1.getIdU(), found.get().getIdU());
    }

    @Test
    @DisplayName("findByEmailU retourne Optional vide si pas d'utilisateur")
    void testFindByEmailUNotFound() {
        Optional<Utilisateur> found = repository.findByEmailU("nonexistent@example.com");
        assertFalse(found.isPresent(), "Aucun utilisateur ne doit être trouvé pour un email inconnu");
    }

    @Test
    @DisplayName("findByEmailUIgnoreCase trouve quel qu'en soit la casse")
    void testFindByEmailUIgnoreCase() {
        Utilisateur found = repository.findByEmailUIgnoreCase("user2@example.com");
        assertNotNull(found, "L'utilisateur doit être trouvé sans tenir compte de la casse");
        assertEquals(user2.getIdU(), found.getIdU());
    }

    @Test
    @DisplayName("findByEmailUIgnoreCase retourne null si pas d'utilisateur")
    void testFindByEmailUIgnoreCaseNotFound() {
        Utilisateur found = repository.findByEmailUIgnoreCase("absent@example.com");
        assertNull(found, "Aucun utilisateur ne doit être retourné pour un email inconnu");
    }

}

