package miage.groupe6.reseausocial.model.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import miage.groupe6.reseausocial.model.entity.Interesser;
import miage.groupe6.reseausocial.model.entity.InteresserId;

/**
 * Repository interface for accessing Interesser entities.
 * Represents the association between Utilisateur and Evenement (interest).
 */
@Repository
public interface InteresserRepository extends JpaRepository<Interesser, InteresserId> {

}
