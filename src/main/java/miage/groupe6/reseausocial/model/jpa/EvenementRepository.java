package miage.groupe6.reseausocial.model.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import miage.groupe6.reseausocial.model.entity.Evenement;

/**
 * Repository interface for accessing Evenement entities.
 */
@Repository
public interface EvenementRepository extends JpaRepository<Evenement, Long>{

}
