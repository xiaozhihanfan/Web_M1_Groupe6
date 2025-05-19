package miage.groupe6.reseausocial.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import miage.groupe6.reseausocial.model.entity.*;

@SpringBootTest
class CommentaireTest {

    private Commentaire commentaire;
    private Utilisateur utilisateur;
    private Post post;
    private Commentaire parentComment;
    private Set<Commentaire> reponses;

    @BeforeEach
    void setUp() {
        // Initialize test objects
        utilisateur = new Utilisateur();
        post = new Post();
        parentComment = new Commentaire();
        reponses = new HashSet<>();
        
        commentaire = new Commentaire();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(commentaire);
        assertNull(commentaire.getIdC());
        assertNull(commentaire.getContenueC());
        assertNull(commentaire.getTempsC());
        assertNull(commentaire.getUtilisateur());
        assertNull(commentaire.getPost());
        assertNull(commentaire.getCommentaireParent());
        assertNotNull(commentaire.getReponses());
        assertTrue(commentaire.getReponses().isEmpty());
    }

    @Test
    void testParameterizedConstructor() {
        Long id = 1L;
        String content = "Test comment";
        Date now = new Date();
        
        Commentaire fullComment = new Commentaire(
            id, content, now, utilisateur, post, parentComment, reponses);
        
        assertEquals(id, fullComment.getIdC());
        assertEquals(content, fullComment.getContenueC());
        assertEquals(now, fullComment.getTempsC());
        assertEquals(utilisateur, fullComment.getUtilisateur());
        assertEquals(post, fullComment.getPost());
        assertEquals(parentComment, fullComment.getCommentaireParent());
        assertEquals(reponses, fullComment.getReponses());
    }

    @Test
    void testSettersAndGetters() {
        Long id = 2L;
        String content = "Updated comment";
        Date now = new Date();
        
        commentaire.setIdC(id);
        commentaire.setContenueC(content);
        commentaire.setTempsC(now);
        commentaire.setUtilisateur(utilisateur);
        commentaire.setPost(post);
        commentaire.setCommentaireParent(parentComment);
        commentaire.setReponses(reponses);
        
        assertEquals(id, commentaire.getIdC());
        assertEquals(content, commentaire.getContenueC());
        assertEquals(now, commentaire.getTempsC());
        assertEquals(utilisateur, commentaire.getUtilisateur());
        assertEquals(post, commentaire.getPost());
        assertEquals(parentComment, commentaire.getCommentaireParent());
        assertEquals(reponses, commentaire.getReponses());
    }

    @Test
    void testReponsesCollection() {
        Commentaire reply1 = new Commentaire();
        Commentaire reply2 = new Commentaire();
        
        Set<Commentaire> replies = new HashSet<>();
        replies.add(reply1);
        replies.add(reply2);
        
        commentaire.setReponses(replies);
        
        assertEquals(2, commentaire.getReponses().size());
        assertTrue(commentaire.getReponses().contains(reply1));
        assertTrue(commentaire.getReponses().contains(reply2));
    }

    @Test
    void testEmptyReponsesCollection() {
        Set<Commentaire> emptyReplies = new HashSet<>();
        commentaire.setReponses(emptyReplies);
        
        assertNotNull(commentaire.getReponses());
        assertTrue(commentaire.getReponses().isEmpty());
    }

    @Test
    void testCommentaireParentRelationship() {
        Commentaire childComment = new Commentaire();
        childComment.setCommentaireParent(commentaire);
        
        assertEquals(commentaire, childComment.getCommentaireParent());
    }

    
}
