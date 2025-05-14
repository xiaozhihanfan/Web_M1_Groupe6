package miage.groupe6.reseausocial.model.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import miage.groupe6.reseausocial.model.entity.ActionEvenement;
import miage.groupe6.reseausocial.model.entity.ActionEvenementId;

/**
 * Repository interface for accessing Inscrire entities.
 * Represents the association between Utilisateur and Evenement.
 */
@Repository
public interface ActionEvenementRepository extends JpaRepository<ActionEvenement, ActionEvenementId>{

}
