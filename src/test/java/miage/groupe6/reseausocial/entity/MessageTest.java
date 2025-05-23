package miage.groupe6.reseausocial.entity;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.Message;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Tests unitaires pour l'entité {@link Message}.
 * <p>
 * Vérifie les accesseurs, les constructeurs et les associations.
 */
public class MessageTest {
    private Message message;
    private Utilisateur envoyeur;
    private Utilisateur recepteur;
    private Groupe groupe;
    private Date date;

    /**
     * Initialise des objets de test avant chaque test.
     */
    @BeforeEach
    void setUp() {
        envoyeur = new Utilisateur();
        envoyeur.setIdU(1L);

        recepteur = new Utilisateur();
        recepteur.setIdU(2L);

        groupe = new Groupe();
        groupe.setIdGroupe(100L);

        date = new Date();

        message = new Message(envoyeur, groupe, 10L, recepteur, date, "Bonjour à tous !");
    }

    /**
     * Vérifie le constructeur complet.
     */
    @Test
    void testConstructeurComplet() {
        assertEquals(10L, message.getIdMessage());
        assertEquals("Bonjour à tous !", message.getText());
        assertEquals(date, message.getTemps());
        assertEquals(envoyeur, message.getEnvoyeur());
        assertEquals(recepteur, message.getRecepteur());
        assertEquals(groupe, message.getGroupe());
    }

    /**
     * Vérifie les setters et getters.
     */
    @Test
    void testSettersEtGetters() {
        Message m = new Message();
        m.setIdMessage(5L);
        m.setText("Test");
        m.setTemps(date);
        m.setEnvoyeur(envoyeur);
        m.setRecepteur(recepteur);
        m.setGroupe(groupe);

        assertEquals(5L, m.getIdMessage());
        assertEquals("Test", m.getText());
        assertEquals(date, m.getTemps());
        assertEquals(envoyeur, m.getEnvoyeur());
        assertEquals(recepteur, m.getRecepteur());
        assertEquals(groupe, m.getGroupe());
    }

    /**
     * Vérifie qu’un message sans recepteur est considéré comme message de groupe.
     */
    @Test
    void testMessageDeGroupeSansRecepteur() {
        Message m = new Message(envoyeur, groupe, 11L, null, date, "Message au groupe");
        assertNull(m.getRecepteur());
        assertNotNull(m.getGroupe());
    }

    /**
     * Vérifie qu’un message sans groupe est considéré comme message privé.
     */
    @Test
    void testMessagePriveSansGroupe() {
        Message m = new Message(envoyeur, null, 12L, recepteur, date, "Message privé");
        assertNotNull(m.getRecepteur());
        assertNull(m.getGroupe());
    }
}
