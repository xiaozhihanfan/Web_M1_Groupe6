package miage.groupe6.reseausocial.model.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import miage.groupe6.reseausocial.model.entity.Commentaire;
import miage.groupe6.reseausocial.model.entity.Post;

/**
 * Repository JPA pour l'entité {@link Commentaire}.
 * Fournit les opérations de base CRUD ainsi que des requêtes personnalisées.
 */

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    List<Commentaire> findByPostOrderByTempsC(Post post);
    List<Commentaire> findByPost_IdP(Long postId);

}
