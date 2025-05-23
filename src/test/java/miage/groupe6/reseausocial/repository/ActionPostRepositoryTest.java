package miage.groupe6.reseausocial.repository;

import miage.groupe6.reseausocial.model.entity.*;
import miage.groupe6.reseausocial.model.jpa.repository.ActionPostRepository;
import miage.groupe6.reseausocial.model.jpa.repository.PostRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

@SpringBootTest
public class ActionPostRepositoryTest {

    @MockBean
    private PostRepository pr;

    @MockBean
    private ActionPostRepository apr;


    @Test
    public void TestcountByPostAndStatut() {
    	Post post = new Post();
    	post.setContenuP("1");
        assertEquals(0,apr.countByPostAndStatut(post, StatutActionPost.LIKE),"probleme sur countByPostAndStatut");
    }
    
    public void TestfindByUtilisateurAndPostAndStatut(){
    	
    	Utilisateur u = new Utilisateur();
    	Post p = new Post();
    	assertEquals(null,apr.findByUtilisateurAndPostAndStatut(u, p, StatutActionPost.LIKE),"error:findByUtilisateurAndPostAndStatut");
    }

    public void deleteByUtilisateurAndPostAndStatut(Utilisateur utilisateur, Post post ,StatutActionPost statut) {
    	Utilisateur u = new Utilisateur();
    	Post p = new Post();
    	apr.deleteByUtilisateurAndPostAndStatut(u, p, StatutActionPost.LIKE);
    	assertEquals(null,apr.findByUtilisateurAndPostAndStatut(u, p, StatutActionPost.LIKE),"error");
    }
    
    
    
    public void save() {
    	ActionPost ap = new ActionPost();
    	assertEquals(ap,apr.save(ap),"error");
    	
    }


}