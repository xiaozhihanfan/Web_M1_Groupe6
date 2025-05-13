package miage.groupe6.reseausocial.model.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{

}
