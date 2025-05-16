package miage.groupe6.reseausocial.model.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Repository JPA pour l'entité {@link Utilisateur}.
 * Fournit les opérations CRUD pour les utilisateurs de la plateforme.
 */
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
}
