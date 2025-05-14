package miage.groupe6.reseausocial.model.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import miage.groupe6.reseausocial.model.entity.Republier;
import miage.groupe6.reseausocial.model.entity.RepublierId;

/**
 * Repository JPA pour l'entité {@link Republier}.
 * Fournit les opérations CRUD pour les utilisateurs de la plateforme.
 */
public interface RepublierRepository extends JpaRepository<Republier, RepublierId>{

}
