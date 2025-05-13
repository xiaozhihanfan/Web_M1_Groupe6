package miage.groupe6.reseausocial.model.jpa;

import miage.groupe6.reseausocial.model.entity.Aimer;
import miage.groupe6.reseausocial.model.entity.AimerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AimerRepository extends JpaRepository<Aimer, AimerId> {
}

