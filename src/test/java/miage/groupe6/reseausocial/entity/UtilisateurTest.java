package miage.groupe6.reseausocial.entity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

@SpringBootTest
public class UtilisateurTest {


    @Autowired
    private UtilisateurService utilisateurService;

    @Test
    public void testSaveUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNomU("abc");
        utilisateur.setPrenomU("def");
        utilisateur.setEmailU("abc.def@123.com");
        utilisateur.setMpU("123");
        utilisateur.setDescriptionU("a test user");
        utilisateur.setAvatarU("1.jpg");
        utilisateur.setDateInscription("2025-05-17");
        utilisateur.setBirthday(LocalDate.of(2025, 5, 18));
        utilisateur.setTelephone("1234567890");
        utilisateur.setUserName("abcdef");
        utilisateur.setUniversite("TestU");
        utilisateur.setVille("TestC");

        Utilisateur newU = utilisateurService.save(utilisateur);

        assertNotNull(newU.getIdU());
        assertEquals("abc", newU.getNomU());
        assertEquals("def", newU.getPrenomU());
        assertEquals("abc.def@123.com", newU.getEmailU());
        assertEquals("123", newU.getMpU());
        assertEquals("a test user", newU.getDescriptionU());
        assertEquals("1.jpg", newU.getAvatarU());
        assertEquals("2025-05-17", newU.getDateInscription());
        assertEquals(LocalDate.of(2025, 5, 18), newU.getBirthday());
        assertEquals("1234567890", newU.getTelephone());
        assertEquals("abcdef", newU.getUserName());
        assertEquals("TestU", newU.getUniversite());
        assertEquals("TestC", newU.getVille());
    }


}
