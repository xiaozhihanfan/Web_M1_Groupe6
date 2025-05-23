package miage.groupe6.reseausocial.repository;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.Message;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.GroupeRepository;
import miage.groupe6.reseausocial.model.jpa.repository.MessageRepository;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;

/**
 * Tests d'intégration pour le repository {@link MessageRepository}.
 * Vérifie la persistance et la récupération des messages privés et de groupe.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private GroupeRepository groupeRepository;

    private Utilisateur u1;
    private Utilisateur u2;
    private Groupe groupe;

    /**
     * Initialise les entités de test avant chaque cas.
     */
    @BeforeEach
    void setUp() {
        messageRepository.deleteAll();
        utilisateurRepository.deleteAll();
        groupeRepository.deleteAll();

        u1 = new Utilisateur();
        u1.setNomU("Alice");
        u1.setPrenomU("Dupont");
        u1.setEmailU("alice@test.com");
        utilisateurRepository.save(u1);

        u2 = new Utilisateur();
        u2.setNomU("Bob");
        u2.setPrenomU("Durand");
        u2.setEmailU("bob@test.com");
        utilisateurRepository.save(u2);

        groupe = new Groupe();
        groupe.setNomGroupe("Groupe Test");
        groupe.setCreateur(u1);
        groupeRepository.save(groupe);
    }

    /**
     * Vérifie que les messages échangés entre deux utilisateurs sont correctement récupérés.
     */
    @Test
    void testFindMessagesEntreUtilisateurs() {
        messageRepository.save(new Message(u1, null, null, u2, new Date(), "Salut Bob"));
        messageRepository.save(new Message(u2, null, null, u1, new Date(), "Salut Alice"));

        List<Message> messages = messageRepository
                .findByEnvoyeurAndRecepteurOrRecepteurAndEnvoyeurOrderByTempsAsc(u1, u2, u1, u2);

        assertThat(messages).hasSize(2);
        assertThat(messages.get(0).getEnvoyeur()).isIn(u1, u2);
    }

    /**
     * Vérifie que les messages associés à un groupe sont correctement triés et récupérés.
     */
    @Test
    void testFindMessagesParGroupe() {
        messageRepository.save(new Message(u1, groupe, null, null, new Date(), "Message 1"));
        messageRepository.save(new Message(u1, groupe, null, null, new Date(), "Message 2"));

        List<Message> messages = messageRepository.findByGroupeOrderByTempsAsc(groupe);

        assertThat(messages).hasSize(2);
        assertThat(messages.get(0).getGroupe()).isEqualTo(groupe);
    }
}
