package miage.groupe6.reseausocial.repository;

import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import miage.groupe6.reseausocial.model.entity.Commentaire;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.jpa.repository.CommentaireRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentaireRepositoryTest {
	
	@MockBean
	private CommentaireRepository cr;
	
	@Test
	public void findByPostOrderByTempsC(){
		Post p = new Post();
		List<Commentaire> lc = cr.findByPostOrderByTempsC(p);
		assertEquals(0,lc.size(),"error");
		
	}
	
	@Test
    public void findByPost_IdP(){
		Post p = new Post();
		p.setIdP(1L);
		assertEquals(0,cr.findByPost_IdP(p.getIdP()).size(),"error");

	}
	
}
