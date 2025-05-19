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
class PostTest {

    private Post post;
    private Utilisateur auteur;
    private Set<ActionPost> utilisateurs;
    private Set<Commentaire> commentaires;

    @BeforeEach
    void setUp() {
        
        auteur = new Utilisateur();
        auteur.setIdU(1L);
        auteur.setNomU("Test User");
        
        post = new Post();
        post.setIdP(1L);
        post.setAuteur(auteur);
        post.setContenuP("Test content");
        post.setDateP(new Date());
        
        utilisateurs = new HashSet<>();
        commentaires = new HashSet<>();
        
        post.setUtilisateurs(utilisateurs);
        post.setCommentaires(commentaires);
    }

    @Test
    void testPostCreation() {
        assertNotNull(post);
        assertEquals(1L, post.getIdP());
        assertEquals("Test content", post.getContenuP());
        assertEquals(auteur, post.getAuteur());
        assertNotNull(post.getDateP());
        assertTrue(post.getUtilisateurs().isEmpty());
        assertTrue(post.getCommentaires().isEmpty());
    }

    @Test
    void testAddActionPost() {
       
        ActionPost action = new ActionPost(
            new Date(),
            StatutActionPost.LIKE,
            auteur,
            post
        );
        
       
        post.getUtilisateurs().add(action);
        
       
        assertEquals(1, post.getUtilisateurs().size());
        assertTrue(post.getUtilisateurs().contains(action));
        assertEquals(post, action.getPost());
    }

    @Test
    void testRemoveActionPost() {
     
        ActionPost action = new ActionPost(
            new Date(),
            StatutActionPost.LIKE,
            auteur,
            post
        );
        post.getUtilisateurs().add(action);
        
        
        post.getUtilisateurs().remove(action);
        
       
        assertTrue(post.getUtilisateurs().isEmpty());
    }

    @Test
    void testAddCommentaire() {
   
        Commentaire commentaire = new Commentaire();
        commentaire.setIdC(1L);
        commentaire.setContenueC("Test comment");
        commentaire.setTempsC(new Date());
        commentaire.setUtilisateur(auteur);
        commentaire.setPost(post);
        
      
        post.getCommentaires().add(commentaire);
        
    
        assertEquals(1, post.getCommentaires().size());
        assertTrue(post.getCommentaires().contains(commentaire));
        assertEquals(post, commentaire.getPost());
    }

    @Test
    void testRemoveCommentaire() {

        Commentaire commentaire = new Commentaire();
        commentaire.setIdC(1L);
        commentaire.setContenueC("Test comment");
        commentaire.setTempsC(new Date());
        commentaire.setUtilisateur(auteur);
        commentaire.setPost(post);
        post.getCommentaires().add(commentaire);
        
 
        post.getCommentaires().remove(commentaire);
        

        assertTrue(post.getCommentaires().isEmpty());
    }

    @Test
    void testSetUtilisateurs() {

        Set<ActionPost> newActions = new HashSet<>();
        ActionPost action1 = new ActionPost(
            new Date(),
            StatutActionPost.LIKE,
            auteur,
            post
        );
        ActionPost action2 = new ActionPost(
            new Date(),
            StatutActionPost.REPUBLIER,
            auteur,
            post
        );
        newActions.add(action1);
        newActions.add(action2);
        

        post.setUtilisateurs(newActions);
        

        assertEquals(2, post.getUtilisateurs().size());
        assertTrue(post.getUtilisateurs().contains(action1));
        assertTrue(post.getUtilisateurs().contains(action2));
    }

    @Test
    void testSetCommentaires() {
        Set<Commentaire> newComments = new HashSet<>();
        Commentaire comment1 = new Commentaire();
        comment1.setIdC(1L);
        comment1.setContenueC("Comment 1");
        comment1.setTempsC(new Date());
        comment1.setUtilisateur(auteur);
        comment1.setPost(post);
        
        Commentaire comment2 = new Commentaire();
        comment2.setIdC(2L);
        comment2.setContenueC("Comment 2");
        comment2.setTempsC(new Date());
        comment2.setUtilisateur(auteur);
        comment2.setPost(post);
        
        newComments.add(comment1);
        newComments.add(comment2);
        
        post.setCommentaires(newComments);

        assertEquals(2, post.getCommentaires().size());
        assertTrue(post.getCommentaires().contains(comment1));
        assertTrue(post.getCommentaires().contains(comment2));
    }
}
