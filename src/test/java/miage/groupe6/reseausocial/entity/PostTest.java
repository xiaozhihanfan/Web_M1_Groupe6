package miage.groupe6.reseausocial.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import miage.groupe6.reseausocial.model.entity.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostTest {

    private Post post;
    private Utilisateur auteur;
    private ActionPost actionPost;
    private Commentaire commentaire;
    private Post originalPost;

    @BeforeEach
    void setUp() {

    	auteur = new Utilisateur();
        auteur.setIdU(1L);
        auteur.setNomU("Test User");
        ActionPostId id = new ActionPostId();
        actionPost = new ActionPost();
        actionPost.setId(id);
        actionPost.setStatut(StatutActionPost.LIKE);
        
        commentaire = new Commentaire();
        commentaire.setIdC(1L);
        commentaire.setContenueC("Test comment");
        commentaire.setTempsC(new Date());
        
        originalPost = new Post();
        originalPost.setIdP(2L);
        originalPost.setContenuP("Original post");
        
        post = new Post();
        post.setIdP(1L);
        post.setContenuP("Test post content");
        post.setImageP("test.jpg");
        post.setDateP(new Date());
        post.setAuteur(auteur);
        post.setNombreLikes(5);
        
        Set<ActionPost> actions = new HashSet<>();
        actions.add(actionPost);
        post.setUtilisateurs(actions);
        
        List<Commentaire> comments = new ArrayList<>();
        comments.add(commentaire);
        post.setCommentaires(comments);
        
        post.setOriginalPost(originalPost);
        
        List<Post> reposts = new ArrayList<>();
        Post repost = new Post();
        repost.setIdP(3L);
        repost.setContenuP("Repost content");
        repost.setOriginalPost(post);
        reposts.add(repost);
        post.setReposts(reposts);
    }

    @Test
    void testPostCreation() {
        assertNotNull(post);
        assertEquals(1L, post.getIdP());
        assertEquals("Test post content", post.getContenuP());
        assertEquals("test.jpg", post.getImageP());
        assertNotNull(post.getDateP());
        assertEquals(auteur, post.getAuteur());
        assertEquals(5, post.getNombreLikes());
    }

    @Test
    void testPostRelationships() {
    	
        Set<ActionPost> actions = post.getUtilisateurs();
        assertNotNull(actions);
        assertFalse(actions.isEmpty());
        assertTrue(actions.contains(actionPost));
        
        List<Commentaire> comments = post.getCommentaires();
        assertNotNull(comments);
        assertFalse(comments.isEmpty());
        assertEquals(commentaire, comments.get(0));
        
        assertEquals(originalPost, post.getOriginalPost());
        
        List<Post> reposts = post.getReposts();
        assertNotNull(reposts);
        assertFalse(reposts.isEmpty());
        assertEquals(1, reposts.size());
        assertEquals("Repost content", reposts.get(0).getContenuP());
        assertEquals(post, reposts.get(0).getOriginalPost());
    }

    @Test
    void testDefaultConstructor() {
        Post emptyPost = new Post();
        assertNotNull(emptyPost);
        assertNull(emptyPost.getIdP());
        assertNull(emptyPost.getContenuP());
        assertNull(emptyPost.getImageP());
        assertNull(emptyPost.getDateP());
        assertNull(emptyPost.getAuteur());
        assertEquals(0, emptyPost.getNombreLikes());
        assertNotNull(emptyPost.getUtilisateurs());
        assertTrue(emptyPost.getUtilisateurs().isEmpty());
        assertNotNull(emptyPost.getCommentaires());
        assertTrue(emptyPost.getCommentaires().isEmpty());
        assertNull(emptyPost.getOriginalPost());
        assertNotNull(emptyPost.getReposts());
        assertTrue(emptyPost.getReposts().isEmpty());
    }

    @Test
    void testFullConstructor() {
        Post fullPost = new Post(4L, "Full post", "full.jpg", new Date(), auteur, new HashSet<>(), new ArrayList<>());
        
        assertEquals(4L, fullPost.getIdP());
        assertEquals("Full post", fullPost.getContenuP());
        assertEquals("full.jpg", fullPost.getImageP());
        assertNotNull(fullPost.getDateP());
        assertEquals(auteur, fullPost.getAuteur());
        assertEquals(0, fullPost.getNombreLikes());
        assertNotNull(fullPost.getUtilisateurs());
        assertTrue(fullPost.getUtilisateurs().isEmpty());
        assertNotNull(fullPost.getCommentaires());
        assertTrue(fullPost.getCommentaires().isEmpty());
        assertNull(fullPost.getOriginalPost());
        assertNotNull(fullPost.getReposts());
        assertTrue(fullPost.getReposts().isEmpty());
    }

    @Test
    void testGettersAndSetters() {
        Post testPost = new Post();
        
        testPost.setIdP(10L);
        assertEquals(10L, testPost.getIdP());
        
        testPost.setContenuP("Updated content");
        assertEquals("Updated content", testPost.getContenuP());
        
        testPost.setImageP("updated.jpg");
        assertEquals("updated.jpg", testPost.getImageP());
        
        Date testDate = new Date();
        testPost.setDateP(testDate);
        assertEquals(testDate, testPost.getDateP());
        
        Utilisateur newAuthor = new Utilisateur();
        newAuthor.setIdU(2L);
        testPost.setAuteur(newAuthor);
        assertEquals(newAuthor, testPost.getAuteur());
        
        testPost.setNombreLikes(10);
        assertEquals(10, testPost.getNombreLikes());
        
        Set<ActionPost> newActions = new HashSet<>();
        ActionPost newAction = new ActionPost();
        ActionPostId id = new ActionPostId();
        newAction.setId(id);
        newActions.add(newAction);
        testPost.setUtilisateurs(newActions);
        assertEquals(newActions, testPost.getUtilisateurs());
        
        List<Commentaire> newComments = new ArrayList<>();
        Commentaire newComment = new Commentaire();
        newComment.setIdC(2L);
        newComments.add(newComment);
        testPost.setCommentaires(newComments);
        assertEquals(newComments, testPost.getCommentaires());
        
        Post newOriginal = new Post();
        newOriginal.setIdP(20L);
        testPost.setOriginalPost(newOriginal);
        assertEquals(newOriginal, testPost.getOriginalPost());
        
        List<Post> newReposts = new ArrayList<>();
        Post newRepost = new Post();
        newRepost.setIdP(30L);
        newReposts.add(newRepost);
        testPost.setReposts(newReposts);
        assertEquals(newReposts, testPost.getReposts());
    }
}
