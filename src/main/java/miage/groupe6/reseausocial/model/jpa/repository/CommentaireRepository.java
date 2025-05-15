package miage.groupe6.reseausocial.model.jpa.repository;

import miage.groupe6.reseausocial.model.entity.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository JPA pour l'entité {@link Commentaire}.
 * Fournit les opérations de base CRUD ainsi que des requêtes personnalisées.
 */
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

}
