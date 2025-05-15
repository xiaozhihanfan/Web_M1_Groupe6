package miage.groupe6.reseausocial.model.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import miage.groupe6.reseausocial.model.entity.ActionEvenement;
import miage.groupe6.reseausocial.model.entity.ActionEvenementId;

/**
 * Repository JPA pour l'entité {@link ActionEvenement}.
 * Gère les opérations de persistance concernant les relations de type "like"
 * entre les utilisateurs et les évenement.
 * <p>
 * Utilise {@link ActionEvenementId} comme clé primaire composite.
 */
public interface ActionEvenementRepository extends JpaRepository<ActionEvenement, ActionEvenementId>{

}
