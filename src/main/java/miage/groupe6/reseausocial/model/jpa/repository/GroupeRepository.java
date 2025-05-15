package miage.groupe6.reseausocial.model.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import miage.groupe6.reseausocial.model.entity.Groupe;

/**
 * Repository JPA pour l'entité {@link Groupe}.
 * <p>
 * Permet d'effectuer des opérations CRUD (Create, Read, Update, Delete)
 * sur les groupes du réseau social dans la base de données.
 * </p>
 * 
 * Cette interface hérite de {@link JpaRepository} et utilise {@link Long}
 * comme type d'identifiant du groupe.
 * 
 */
public interface GroupeRepository extends JpaRepository<Groupe, Long>{

}
