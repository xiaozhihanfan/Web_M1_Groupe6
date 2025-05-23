package miage.groupe6.reseausocial.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import miage.groupe6.reseausocial.model.entity.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CommentaireTest {

    private Commentaire commentaire;
    private Utilisateur utilisateur;
    private Post post;
    private Commentaire commentaireParent;
    private Set<Commentaire> reponses;

    @BeforeEach
    void setUp() {
        // Initialisation des objets mock
        utilisateur = Mockito.mock(Utilisateur.class);
        post = Mockito.mock(Post.class);
        commentaireParent = Mockito.mock(Commentaire.class);
        reponses = new HashSet<>();

        // Initialisation de l'entité Commentaire
        commentaire = new Commentaire();
        commentaire.setIdC(1L);
        commentaire.setContenueC("Test Comment");
        commentaire.setTempsC(new Date());
        commentaire.setUtilisateur(utilisateur);
        commentaire.setPost(post);
        commentaire.setCommentaireParent(commentaireParent);
        commentaire.setReponses(reponses);
    }

    @Test
    void testCommentaireCreation() {
        // Vérification des valeurs initiales
        assertEquals(1L, commentaire.getIdC());
        assertEquals("Test Comment", commentaire.getContenueC());
        assertNotNull(commentaire.getTempsC());
        assertEquals(utilisateur, commentaire.getUtilisateur());
        assertEquals(post, commentaire.getPost());
        assertEquals(commentaireParent, commentaire.getCommentaireParent());
        assertEquals(reponses, commentaire.getReponses());
    }

    @Test
    void testCommentaireSetters() {
        // Modification des valeurs
        commentaire.setContenueC("New Comment");
        commentaire.setTempsC(new Date());
        Utilisateur newUtilisateur = Mockito.mock(Utilisateur.class);
        Post newPost = Mockito.mock(Post.class);
        Commentaire newCommentaireParent = Mockito.mock(Commentaire.class);
        Set<Commentaire> newReponses = new HashSet<>();

        commentaire.setUtilisateur(newUtilisateur);
        commentaire.setPost(newPost);
        commentaire.setCommentaireParent(newCommentaireParent);
        commentaire.setReponses(newReponses);

        // Vérification des nouvelles valeurs
        assertEquals("New Comment", commentaire.getContenueC());
        assertNotNull(commentaire.getTempsC());
        assertEquals(newUtilisateur, commentaire.getUtilisateur());
        assertEquals(newPost, commentaire.getPost());
        assertEquals(newCommentaireParent, commentaire.getCommentaireParent());
        assertEquals(newReponses, commentaire.getReponses());
    }

    @Test
    void testCommentaireNoArgsConstructor() {
        // Création d'un commentaire avec le constructeur par défaut
        Commentaire newCommentaire = new Commentaire();

        // Vérification que les valeurs sont correctement initialisées à null ou par défaut
        assertNull(newCommentaire.getIdC());
        assertNull(newCommentaire.getContenueC());
        assertNull(newCommentaire.getTempsC());
        assertNull(newCommentaire.getUtilisateur());
        assertNull(newCommentaire.getPost());
        assertNull(newCommentaire.getCommentaireParent());
        assertNotNull(newCommentaire.getReponses());
        assertTrue(newCommentaire.getReponses().isEmpty());
    }

    @Test
    void testCommentaireAllArgsConstructor() {
        // Création d'un commentaire avec le constructeur complet
        Set<Commentaire> reponses = new HashSet<>();
        Commentaire newCommentaire = new Commentaire(2L, "Full Comment", new Date(), utilisateur, post, commentaireParent, reponses);

        // Vérification des valeurs
        assertEquals(2L, newCommentaire.getIdC());
        assertEquals("Full Comment", newCommentaire.getContenueC());
        assertNotNull(newCommentaire.getTempsC());
        assertEquals(utilisateur, newCommentaire.getUtilisateur());
        assertEquals(post, newCommentaire.getPost());
        assertEquals(commentaireParent, newCommentaire.getCommentaireParent());
        assertEquals(reponses, newCommentaire.getReponses());
    }
}
