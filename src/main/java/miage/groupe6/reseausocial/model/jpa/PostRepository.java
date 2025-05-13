package miage.groupe6.reseausocial.model.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import miage.groupe6.reseausocial.model.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
