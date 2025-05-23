package miage.groupe6.reseausocial.repository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.GroupeMembre;
import miage.groupe6.reseausocial.model.entity.MembreRole;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.GroupeMembreRepository;
import miage.groupe6.reseausocial.model.jpa.repository.GroupeRepository;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;

/**
 * Tests d'intégration pour le repository {@link GroupeMembreRepository}.
 * <p>
 * Utilise une base de données en mémoire (H2) pour tester les requêtes personnalisées.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GroupeMembreRepositoryTest {
    @Autowired
    private GroupeMembreRepository groupeMembreRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private GroupeRepository groupeRepository;

    private Utilisateur utilisateur;
    private Groupe groupe;

    /**
     * Initialise un utilisateur, un groupe et quelques membres de groupe avant chaque test.
     */
    @BeforeEach
    void setUp() {
        utilisateur = new Utilisateur();
        utilisateur.setNomU("Dupont");
        utilisateur.setPrenomU("Jean");
        utilisateurRepository.save(utilisateur);

        groupe = new Groupe();
        groupe.setNomGroupe("Groupe Test");
        groupe.setCreateur(utilisateur);
        groupeRepository.save(groupe);

        GroupeMembre membre = new GroupeMembre(
                MembreRole.ADMIN,
                LocalDateTime.now(),
                groupe,
                utilisateur
        );
        groupeMembreRepository.save(membre);
    }

    /**
     * Vérifie que la méthode {@code findByUtilisateurAndRole} retourne correctement les résultats attendus.
     */
    @Test
    void testFindByUtilisateurAndRole() {
        List<GroupeMembre> resultats = groupeMembreRepository.findByUtilisateurAndRole(utilisateur, MembreRole.ADMIN);

        assertEquals(1, resultats.size(), "Un seul membre ADMIN doit être trouvé");
        assertEquals(MembreRole.ADMIN, resultats.get(0).getRole());
        assertEquals(utilisateur.getIdU(), resultats.get(0).getUtilisateur().getIdU());
    }

    /**
     * Vérifie que la méthode {@code findByUtilisateur} retourne tous les groupes de l’utilisateur.
     */
    @Test
    void testFindByUtilisateur() {
        List<GroupeMembre> resultats = groupeMembreRepository.findByUtilisateur(utilisateur);

        assertFalse(resultats.isEmpty(), "L'utilisateur devrait appartenir à au moins un groupe");
        assertEquals(utilisateur.getIdU(), resultats.get(0).getUtilisateur().getIdU());
    }
}
