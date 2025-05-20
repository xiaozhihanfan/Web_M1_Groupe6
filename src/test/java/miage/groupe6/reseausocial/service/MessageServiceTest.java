package miage.groupe6.reseausocial.service;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import miage.groupe6.reseausocial.model.entity.Message;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.MessageRepository;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;
import miage.groupe6.reseausocial.model.jpa.service.MessageService;

@SpringBootTest
class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private Utilisateur u1;
    private Utilisateur u2;

    @BeforeEach
    void setUp() {
        messageRepository.deleteAll();
        utilisateurRepository.deleteAll();

        u1 = new Utilisateur();
        u1.setNomU("Alice");
        u1.setPrenomU("Test");
        u1.setEmailU("alice@test.com");
        utilisateurRepository.save(u1);

        u2 = new Utilisateur();
        u2.setNomU("Bob");
        u2.setPrenomU("Test");
        u2.setEmailU("bob@test.com");
        utilisateurRepository.save(u2);
    }

    @Test
    void testEnvoyerMessage() {
        Message msg = messageService.envoyerMessage(u1, u2, "Bonjour Bob");
        assertThat(msg).isNotNull();
        assertThat(msg.getText()).isEqualTo("Bonjour Bob");
        assertThat(msg.getEnvoyeur()).isEqualTo(u1);
        assertThat(msg.getRecepteur()).isEqualTo(u2);
    }

    @Test
    void testGetMessageEntre() {
        messageService.envoyerMessage(u1, u2, "Salut");
        messageService.envoyerMessage(u2, u1, "Salut à toi !");
        messageService.envoyerMessage(u1, u2, "Comment ça va ?");

        List<Message> messages = messageService.getMessageEntre(u1, u2);
        assertThat(messages).hasSize(3);
        assertThat(messages.get(0).getText()).isEqualTo("Salut");
        assertThat(messages.get(1).getText()).isEqualTo("Salut à toi !");
        assertThat(messages.get(2).getText()).isEqualTo("Comment ça va ?");
    }
}

