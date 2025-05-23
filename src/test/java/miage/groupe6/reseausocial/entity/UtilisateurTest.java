package miage.groupe6.reseausocial.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import miage.groupe6.reseausocial.model.entity.ActionEvenement;
import miage.groupe6.reseausocial.model.entity.ActionPost;
import miage.groupe6.reseausocial.model.entity.Commentaire;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.GroupeMembre;
import miage.groupe6.reseausocial.model.entity.Message;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.RelationAmis;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Tests unitaires pour la classe {@link Utilisateur}.
 */
class UtilisateurTest {

    @Test
    @DisplayName("Constructeur par défaut initialise les champs simples à null et les collections vides")
    void testDefaultConstructor() {
        Utilisateur user = new Utilisateur();
        // Champs simples
        assertNull(user.getIdU(), "idU doit être null après le constructeur par défaut");
        assertNull(user.getNomU(), "nomU doit être null après le constructeur par défaut");
        assertNull(user.getPrenomU(), "prenomU doit être null après le constructeur par défaut");
        assertNull(user.getEmailU(), "emailU doit être null après le constructeur par défaut");
        assertNull(user.getMpU(), "mpU doit être null après le constructeur par défaut");
        assertNull(user.getDescriptionU(), "descriptionU doit être null après le constructeur par défaut");
        assertNull(user.getDateInscription(), "dateInscription doit être null après le constructeur par défaut");
        assertNull(user.getBirthday(), "birthday doit être null après le constructeur par défaut");
        assertNull(user.getTelephone(), "telephone doit être null après le constructeur par défaut");
        assertNull(user.getIne(), "ine doit être null après le constructeur par défaut");
        assertNull(user.getUniversite(), "universite doit être null après le constructeur par défaut");
        assertNull(user.getVille(), "ville doit être null après le constructeur par défaut");
        // Avatar par défaut
        assertEquals("/assets/images/avatar/placeholder.jpg", user.getAvatarU(),
            "Avatar par défaut doit être le placeholder");
        assertEquals("/assets/images/avatar/placeholder.jpg", user.getAvatar(),
            "getAvatar() doit retourner le même placeholder que getAvatarU()");
        // Collections
        assertNotNull(user.getPosts());
        assertTrue(user.getPosts().isEmpty());
        assertNotNull(user.getActionPosts());
        assertTrue(user.getActionPosts().isEmpty());
        assertNotNull(user.getCommentaires());
        assertTrue(user.getCommentaires().isEmpty());
        assertNotNull(user.getActionEvenements());
        assertTrue(user.getActionEvenements().isEmpty());
        assertNotNull(user.getEvenements());
        assertTrue(user.getEvenements().isEmpty());
        assertNotNull(user.getGroupeMembres());
        assertTrue(user.getGroupeMembres().isEmpty());
        assertNotNull(user.getGroupes());
        assertTrue(user.getGroupes().isEmpty());
        assertNotNull(user.getMessagesEnvoyes());
        assertTrue(user.getMessagesEnvoyes().isEmpty());
        assertNotNull(user.getMessagesRecus());
        assertTrue(user.getMessagesRecus().isEmpty());
        assertNotNull(user.getAmisDemandes());
        assertTrue(user.getAmisDemandes().isEmpty());
        assertNotNull(user.getAmisRecus());
        assertTrue(user.getAmisRecus().isEmpty());
    }

    @Test
    @DisplayName("Constructeur complet initialise correctement tous les champs simples")
    void testAllArgsConstructor() {
        String avatar = "/avatars/user.png";
        String dateIns = "2025-05-23";
        String desc = "Bio utilisateur";
        String email = "test@example.com";
        Long id = 123L;
        String mp = "secret";
        String nom = "Dupont";
        String prenom = "Alice";
        LocalDate bday = LocalDate.of(2000, 1, 1);
        String tel = "0123456789";
        String ine = "INE123456";
        String uni = "MIAGE";
        String ville = "Paris";

        Utilisateur user = new Utilisateur(
            avatar,
            dateIns,
            desc,
            email,
            id,
            mp,
            nom,
            prenom,
            bday,
            tel,
            ine,
            uni,
            ville
        );

        assertEquals(id, user.getIdU());
        assertEquals(nom, user.getNomU());
        assertEquals(prenom, user.getPrenomU());
        assertEquals(email, user.getEmailU());
        assertEquals(mp, user.getMpU());
        assertEquals(desc, user.getDescriptionU());
        assertEquals(dateIns, user.getDateInscription());
        assertEquals(bday, user.getBirthday());
        assertEquals(tel, user.getTelephone());
        assertEquals(ine, user.getIne());
        assertEquals(uni, user.getUniversite());
        assertEquals(ville, user.getVille());
        // Avatar
        assertEquals(avatar, user.getAvatarU());
        assertEquals(avatar, user.getAvatar());
    }

    @Test
    @DisplayName("Getters et setters pour tous les champs simples et collections")
    void testSettersAndGetters() {
        Utilisateur user = new Utilisateur();

        // Champs simples
        user.setIdU(1L);
        user.setNomU("Martin");
        user.setPrenomU("Bob");
        user.setEmailU("bob.martin@example.com");
        user.setMpU("pwd");
        user.setDescriptionU("Développeur");
        user.setDateInscription("2025-05-24");
        user.setBirthday(LocalDate.of(1995, 12, 31));
        user.setTelephone("0987654321");
        user.setIne("INE654321");
        user.setUniversite("Université X");
        user.setVille("Lyon");
        user.setAvatarU("");

        assertEquals(1L, user.getIdU());
        assertEquals("Martin", user.getNomU());
        assertEquals("Bob", user.getPrenomU());
        assertEquals("bob.martin@example.com", user.getEmailU());
        assertEquals("pwd", user.getMpU());
        assertEquals("Développeur", user.getDescriptionU());
        assertEquals("2025-05-24", user.getDateInscription());
        assertEquals(LocalDate.of(1995, 12, 31), user.getBirthday());
        assertEquals("0987654321", user.getTelephone());
        assertEquals("INE654321", user.getIne());
        assertEquals("Université X", user.getUniversite());
        assertEquals("Lyon", user.getVille());
        // Avatar vide doit retomber sur placeholder
        assertEquals("/assets/images/avatar/placeholder.jpg", user.getAvatarU());
        assertEquals("/assets/images/avatar/placeholder.jpg", user.getAvatar());

        // Collections : créer des ensembles de mocks
        Set<Post> posts = new HashSet<>();
        posts.add(mock(Post.class));
        user.setPosts(posts);
        assertSame(posts, user.getPosts());

        Set<ActionPost> actionPosts = new HashSet<>();
        actionPosts.add(mock(ActionPost.class));
        user.setActionPosts(actionPosts);
        assertSame(actionPosts, user.getActionPosts());

        Set<Commentaire> commentaires = new HashSet<>();
        commentaires.add(mock(Commentaire.class));
        user.setCommentaires(commentaires);
        assertSame(commentaires, user.getCommentaires());

        Set<ActionEvenement> aevts = new HashSet<>();
        aevts.add(mock(ActionEvenement.class));
        user.setActionEvenements(aevts);
        assertSame(aevts, user.getActionEvenements());

        Set<Evenement> evts = new HashSet<>();
        evts.add(mock(Evenement.class));
        user.setEvenements(evts);
        assertSame(evts, user.getEvenements());

        Set<GroupeMembre> gm = new HashSet<>();
        gm.add(mock(GroupeMembre.class));
        user.setGroupeMembres(gm);
        assertSame(gm, user.getGroupeMembres());

        Set<Groupe> groupes = new HashSet<>();
        groupes.add(mock(Groupe.class));
        user.setGroupes(groupes);
        assertSame(groupes, user.getGroupes());

        Set<Message> msgsEnv = new HashSet<>();
        msgsEnv.add(mock(Message.class));
        user.setMessagesEnvoyes(msgsEnv);
        assertSame(msgsEnv, user.getMessagesEnvoyes());

        Set<Message> msgsRec = new HashSet<>();
        msgsRec.add(mock(Message.class));
        user.setMessagesRecus(msgsRec);
        assertSame(msgsRec, user.getMessagesRecus());

        Set<RelationAmis> amisDem = new HashSet<>();
        amisDem.add(mock(RelationAmis.class));
        user.setAmisDemandes(amisDem);
        assertSame(amisDem, user.getAmisDemandes());

        Set<RelationAmis> amisRec = new HashSet<>();
        amisRec.add(mock(RelationAmis.class));
        user.setAmisRecus(amisRec);
        assertSame(amisRec, user.getAmisRecus());
    }

}

