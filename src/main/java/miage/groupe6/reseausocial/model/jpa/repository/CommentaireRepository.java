package miage.groupe6.reseausocial.model.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import miage.groupe6.reseausocial.model.entity.Commentaire;

/**
 * Repository JPA pour l'entité {@link Commentaire}.
 * Fournit les opérations de base CRUD ainsi que des requêtes personnalisées.
 */
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

}
