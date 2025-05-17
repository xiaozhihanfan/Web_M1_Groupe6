package miage.groupe6.reseausocial.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.PostRepository;
import miage.groupe6.reseausocial.model.jpa.service.PostService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    void findByAuteurOrderByDateDesc_returnsPostsInDescendingOrder() throws Exception {
        Utilisateur user = new Utilisateur();
        user.setIdU(1L);

        // ① 把字符串解析成 java.util.Date
        Date olderDate = sdf.parse("2025-05-01T09:00:00");
        Date newerDate = sdf.parse("2025-05-02T15:30:00");

        Post older = new Post();
        older.setDateP(olderDate);
        Post newer = new Post();
        newer.setDateP(newerDate);

        // mock 仓库返回倒序列表
        when(postRepository.findByAuteurOrderByDatePDesc(user))
            .thenReturn(List.of(newer, older));

        // 调用 service
        List<Post> result = postService.findByAuteurOrderByDateDesc(user);

        // 验证
        assertEquals(2, result.size());
        assertSame(newer, result.get(0));
        assertSame(older,  result.get(1));
        verify(postRepository).findByAuteurOrderByDatePDesc(user);
    }

    @Test
    void countPostByUtilisateur_returnsCorrectCount() {
        Utilisateur user = new Utilisateur();
        user.setIdU(1L);

        when(postRepository.countByAuteur(user)).thenReturn(5);

        int count = postService.countPostByUtilisateur(user);

        assertEquals(5, count);
        verify(postRepository).countByAuteur(user);
    }
}
