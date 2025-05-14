package miage.groupe6.reseausocial.model.jpa;

import miage.groupe6.reseausocial.model.entity.ActionPost;
import miage.groupe6.reseausocial.model.entity.ActionPostId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository JPA pour l'entité {@link ActionPost}.
 * Gère les opérations de persistance concernant les relations de type "like"
 * entre les utilisateurs et les publications.
 * <p>
 * Utilise {@link ActionPostId} comme clé primaire composite.
 */
public interface ActionPostRepository extends JpaRepository<ActionPost, ActionPostId> {
}
