package miage.groupe6.reseausocial.model.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import miage.groupe6.reseausocial.model.entity.Post;

/**
 * Repository JPA pour l'entité {@link Post}.
 * Fournit les opérations CRUD de base pour les publications.
 */
public interface PostRepository extends JpaRepository<Post, Long> {
}
