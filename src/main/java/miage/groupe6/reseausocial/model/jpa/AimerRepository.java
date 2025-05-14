package miage.groupe6.reseausocial.model.jpa;

import miage.groupe6.reseausocial.model.entity.Aimer;
import miage.groupe6.reseausocial.model.entity.AimerId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository JPA pour l'entité {@link Aimer}.
 * Gère les opérations de persistance concernant les relations de type "like"
 * entre les utilisateurs et les publications.
 * <p>
 * Utilise {@link AimerId} comme clé primaire composite.
 */
public interface AimerRepository extends JpaRepository<Aimer, AimerId> {
}
