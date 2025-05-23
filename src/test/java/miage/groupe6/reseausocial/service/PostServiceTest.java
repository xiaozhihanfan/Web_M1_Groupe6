package miage.groupe6.reseausocial.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.PostRepository;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository pr;

    @Mock
    private UtilisateurService us;

    @InjectMocks
    private PostService postService;

    @Test
    @DisplayName("countPostByUtilisateur retourne la valeur fournie par le repository")
    void testCountPostByUtilisateur() {
        Utilisateur u = new Utilisateur();
        when(pr.countByAuteur(u)).thenReturn(7);
        int count = postService.countPostByUtilisateur(u);
        assertEquals(7, count);
        verify(pr).countByAuteur(u);
    }

    @Test
    @DisplayName("save délègue au repository et retourne l'entité sauvée")
    void testSave() {
        Post p = new Post();
        when(pr.save(p)).thenReturn(p);
        Post result = postService.save(p);
        assertSame(p, result);
        verify(pr).save(p);
    }

    @Test
    @DisplayName("findAllOrderedByDate récupère tous les posts (ordre croissant)")
    void testFindAllOrderedByDate() {
        Post p1 = new Post(), p2 = new Post();
        List<Post> list = Arrays.asList(p1, p2);
        when(pr.findAllByOrderByDateP()).thenReturn(list);
        List<Post> result = postService.findAllOrderedByDate();
        assertEquals(list, result);
        verify(pr).findAllByOrderByDateP();
    }

    @Test
    @DisplayName("findById retourne Optional vide ou contenant selon le repository")
    void testFindById() {
        Post p = new Post();
        when(pr.findById(1L)).thenReturn(Optional.of(p));
        Optional<Post> found = postService.findById(1L);
        assertTrue(found.isPresent());
        assertSame(p, found.get());

        when(pr.findById(2L)).thenReturn(Optional.empty());
        Optional<Post> notFound = postService.findById(2L);
        assertFalse(notFound.isPresent());
    }

    @Test
    @DisplayName("findByAuteurOrderByDateDesc délègue au repository")
    void testFindByAuteurOrderByDateDesc() {
        Utilisateur u = new Utilisateur();
        Post p = new Post();
        List<Post> list = Collections.singletonList(p);
        when(pr.findByAuteurOrderByDatePDesc(u)).thenReturn(list);
        List<Post> result = postService.findByAuteurOrderByDateDesc(u);
        assertEquals(list, result);
        verify(pr).findByAuteurOrderByDatePDesc(u);
    }

    @Test
    @DisplayName("findAllOrderedByDateDesc délègue au repository")
    void testFindAllOrderedByDateDesc() {
        Post p = new Post();
        List<Post> list = Collections.singletonList(p);
        when(pr.findAllByOrderByDatePDesc()).thenReturn(list);
        List<Post> result = postService.findAllOrderedByDateDesc();
        assertEquals(list, result);
        verify(pr).findAllByOrderByDatePDesc();
    }

    @Test
    @DisplayName("findAllPostsWithCommentaires délègue au repository")
    void testFindAllPostsWithCommentaires() {
        List<Post> list = Arrays.asList(new Post(), new Post());
        when(pr.findAllWithCommentaires()).thenReturn(list);
        List<Post> result = postService.findAllPostsWithCommentaires();
        assertEquals(list, result);
        verify(pr).findAllWithCommentaires();
    }

    @Test
    @DisplayName("repostPost lève une exception si le post original est introuvable")
    void testRepostPostOriginalNotFound() {
        when(pr.findById(10L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> postService.repostPost(10L, 1L));
        assertTrue(ex.getMessage().contains("Post original introuvable"));
    }

    @Test
    @DisplayName("repostPost lève une exception si l'utilisateur est introuvable")
    void testRepostPostUserNotFound() {
        Post original = new Post();
        when(pr.findById(20L)).thenReturn(Optional.of(original));
        when(us.getUtilisateurById(2L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> postService.repostPost(20L, 2L));
        assertTrue(ex.getMessage().contains("Utilisateur introuvable"));
    }

    @Test
    @DisplayName("repostPost crée et sauvegarde correctement un repost")
    void testRepostPostSuccess() {
        // Préparer le post original
        Post original = new Post();
        original.setContenuP("contenu");
        original.setImageP("image.png");
        when(pr.findById(30L)).thenReturn(Optional.of(original));

        // Préparer l'utilisateur
        Utilisateur user = new Utilisateur();
        user.setIdU(3L);
        when(us.getUtilisateurById(3L)).thenReturn(Optional.of(user));

        // Capturer l'objet passé à save
        ArgumentCaptor<Post> captor = ArgumentCaptor.forClass(Post.class);
        when(pr.save(captor.capture())).thenAnswer(invocation -> invocation.getArgument(0));

        // Appel du service
        Post repost = postService.repostPost(30L, 3L);

        // Vérifications sur le repost
        Post captured = captor.getValue();
        assertEquals("contenu", captured.getContenuP());
        assertEquals("image.png", captured.getImageP());
        assertSame(original, captured.getOriginalPost());
        assertSame(user, captured.getAuteur());
        assertNotNull(captured.getDateP(), "La date du repost doit être définie");
        // Le service retourne bien l'objet sauvegardé
        assertSame(captured, repost);

        verify(pr).findById(30L);
        verify(us).getUtilisateurById(3L);
        verify(pr).save(any(Post.class));
    }
}

