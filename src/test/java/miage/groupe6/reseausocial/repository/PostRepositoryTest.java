package miage.groupe6.reseausocial.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.PostRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PostRepositoryTest {

    @MockBean
    private PostRepository pr;

    @Test
    public void TestcountByAuteur() {
        Utilisateur u = new Utilisateur();
        assertEquals(0, pr.countByAuteur(u), "error");
    }

    @Test
    public void TestdAllByOrderByDateP() {
        assertEquals(0, pr.findAllByOrderByDateP().size(), "error");
    }

    @Test
    public void findByAuteurOrderByDatePDesc() {
        Utilisateur u = new Utilisateur();
        assertEquals(0, pr.findByAuteurOrderByDatePDesc(u).size(), "error");
    }

    @Test
    public void findAllByOrderByDatePDesc() {
        assertEquals(0, pr.findAllByOrderByDatePDesc().size(), "error");
    }

    @Test
    public void findAllWithCommentaires() {
        assertEquals(0, pr.findAllWithCommentaires().size(), "error");
    }
}
