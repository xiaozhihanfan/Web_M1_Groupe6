package miage.groupe6.reseausocial.service;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import miage.groupe6.reseausocial.model.entity.Commentaire;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.CommentaireRepository;
import miage.groupe6.reseausocial.model.jpa.service.CommentaireService;

/**
 * Classe de test unitaire pour {@link CommentaireService}.
 * <p>
 * Vérifie le comportement des méthodes permettant d’ajouter et de récupérer des commentaires.
 */
public class CommentaireServiceTest {

    @Mock
    private CommentaireRepository commentaireRepository;

    @InjectMocks
    private CommentaireService commentaireService;

    private Utilisateur utilisateur;
    private Post post;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        utilisateur = new Utilisateur();
        utilisateur.setIdU(1L);

        post = new Post();
        post.setIdP(1L);
    }

    /**
     * Vérifie que l'ajout d’un commentaire associe correctement l’utilisateur, le post, le contenu et la date.
     */
    @Test
    void testAjouterCommentaire() {
        commentaireService.ajouterCommentaire(utilisateur, post, "Super article !");

        ArgumentCaptor<Commentaire> captor = ArgumentCaptor.forClass(Commentaire.class);
        verify(commentaireRepository).save(captor.capture());

        Commentaire saved = captor.getValue();
        assertEquals(utilisateur, saved.getUtilisateur());
        assertEquals(post, saved.getPost());
        assertEquals("Super article !", saved.getContenueC());
        assertNotNull(saved.getTempsC());
    }

    /**
     * Vérifie que la récupération des commentaires retourne bien les éléments attendus pour un post donné.
     */
    @Test
    void testGetCommentairesByPost() {
        Commentaire commentaire = new Commentaire();
        commentaire.setUtilisateur(utilisateur);
        commentaire.setPost(post);
        commentaire.setContenueC("Commentaire test");
        commentaire.setTempsC(new Date());
        when(commentaireRepository.findByPostOrderByTempsC(post)).thenReturn(List.of(commentaire));

        List<Commentaire> result = commentaireService.getCommentairesByPost(post);

        assertEquals(1, result.size());
        assertEquals("Commentaire test", result.get(0).getContenueC());
        assertEquals(post, result.get(0).getPost());
    }
}