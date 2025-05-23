package miage.groupe6.reseausocial.service;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.Message;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.GroupeRepository;
import miage.groupe6.reseausocial.model.jpa.repository.MessageRepository;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;
import miage.groupe6.reseausocial.model.jpa.service.MessageService;

/**
 * Tests d’intégration pour la classe de service {@link MessageService}.
 * <p>
 * Vérifie l’envoi de messages privés et de messages de groupe,
 * ainsi que la récupération des conversations.
 */
@SpringBootTest
class MessageServiceTest {

    @Autowired
    private MessageService messageService;

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
     * Prépare les données avant chaque test : deux utilisateurs et un groupe.
     */
    @BeforeEach
    void setUp() {
        messageRepository.deleteAll();
        utilisateurRepository.deleteAll();
        groupeRepository.deleteAll();

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

        groupe = new Groupe();
        groupe.setNomGroupe("Groupe Test");
        groupe.setCreateur(u1);
        groupeRepository.save(groupe);
    }

    /**
     * Vérifie que l’envoi d’un message privé est correctement enregistré.
     */
    @Test
    void testEnvoyerMessage() {
        Message msg = messageService.envoyerMessage(u1, u2, "Bonjour Bob");

        assertThat(msg).isNotNull();
        assertThat(msg.getText()).isEqualTo("Bonjour Bob");
        assertThat(msg.getEnvoyeur()).isEqualTo(u1);
        assertThat(msg.getRecepteur()).isEqualTo(u2);
        assertThat(msg.getTemps()).isBeforeOrEqualTo(new Date());
    }

    /**
     * Vérifie que tous les messages entre deux utilisateurs sont correctement récupérés.
     */
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

    /**
     * Vérifie que l’envoi d’un message dans un groupe est correctement enregistré.
     */
    @Test
    void testEnvoyerMessageGroupe() {
        Message msg = messageService.envoyerMessageGroupe("Hello groupe", u1, groupe);

        assertThat(msg).isNotNull();
        assertThat(msg.getText()).isEqualTo("Hello groupe");
        assertThat(msg.getGroupe()).isEqualTo(groupe);
        assertThat(msg.getEnvoyeur()).isEqualTo(u1);
    }

    /**
     * Vérifie que les messages d’un groupe sont correctement triés et récupérés.
     */
    @Test
    void testGetMessagesGroupe() {
        messageService.envoyerMessageGroupe("Bienvenue", u1, groupe);
        messageService.envoyerMessageGroupe("On commence ?", u1, groupe);

        List<Message> messages = messageService.getMessagesGroupe(groupe);

        assertThat(messages).hasSize(2);
        assertThat(messages.get(0).getText()).isEqualTo("Bienvenue");
        assertThat(messages.get(1).getText()).isEqualTo("On commence ?");
    }
}