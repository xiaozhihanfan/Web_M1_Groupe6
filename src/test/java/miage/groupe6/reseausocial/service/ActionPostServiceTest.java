package miage.groupe6.reseausocial.service;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import miage.groupe6.reseausocial.model.entity.ActionPost;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.StatutActionPost;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.ActionPostRepository;
import miage.groupe6.reseausocial.model.jpa.service.ActionPostService;

/**
 * Classe de test pour le service {@link ActionPostService}.
 * Vérifie le bon comportement des méthodes relatives aux actions sur les publications.
 */
public class ActionPostServiceTest {

    @Mock
    private ActionPostRepository actionPostRepository;

    @InjectMocks
    private ActionPostService actionPostService;

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
     * Vérifie qu'un like est ajouté si l'utilisateur ne l'a pas déjà fait.
     */
    @Test
    void testLikePost_ajout() {
        when(actionPostRepository.findByUtilisateurAndPostAndStatut(utilisateur, post, StatutActionPost.LIKE))
            .thenReturn(Optional.empty());

        actionPostService.likePost(utilisateur, post);

        verify(actionPostRepository).save(any(ActionPost.class));
    }

    /**
     * Vérifie qu'aucune sauvegarde n'est effectuée si le like existe déjà.
     */
    @Test
    void testLikePost_dejaExistant() {
        when(actionPostRepository.findByUtilisateurAndPostAndStatut(utilisateur, post, StatutActionPost.LIKE))
            .thenReturn(Optional.of(new ActionPost()));

        actionPostService.likePost(utilisateur, post);

        verify(actionPostRepository, never()).save(any(ActionPost.class));
    }

    /**
     * Vérifie que le nombre de likes est correctement retourné.
     */
    @Test
    void testCountLikes() {
        when(actionPostRepository.countByPostAndStatut(post, StatutActionPost.LIKE)).thenReturn(5);
        int result = actionPostService.countLikes(post);
        assertThat(result).isEqualTo(5);
    }

    /**
     * Vérifie que la méthode de recherche retourne bien l'action attendue.
     */
    @Test
    void testFindByUtilisateurAndPostAndStatut() {
        ActionPost expected = new ActionPost();
        when(actionPostRepository.findByUtilisateurAndPostAndStatut(utilisateur, post, StatutActionPost.LIKE))
            .thenReturn(Optional.of(expected));

        Optional<ActionPost> result = actionPostService.findByUtilisateurAndPostAndStatut(utilisateur, post, StatutActionPost.LIKE);

        assertThat(result).contains(expected);
    }

    /**
     * Vérifie que la suppression d'une action est bien exécutée.
     */
    @Test
    void testDeleteByUtilisateurAndPostAndStatut() {
        actionPostService.deleteByUtilisateurAndPostAndStatut(utilisateur, post, StatutActionPost.LIKE);
        verify(actionPostRepository).deleteByUtilisateurAndPostAndStatut(utilisateur, post, StatutActionPost.LIKE);
    }
}