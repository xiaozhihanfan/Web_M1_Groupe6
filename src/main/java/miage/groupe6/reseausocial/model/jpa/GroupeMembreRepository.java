package miage.groupe6.reseausocial.model.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import miage.groupe6.reseausocial.model.entity.GroupeMembre;
import miage.groupe6.reseausocial.model.entity.GroupeMembreId;

/**
 * Repository JPA pour l'entité {@link GroupeMembre}.
 * <p>
 * Permet d'effectuer des opérations CRUD (Create, Read, Update, Delete) 
 * sur les relations membres-groupe dans la base de données.
 * </p>
 * 
 * Cette interface hérite de {@link JpaRepository} et utilise {@link GroupeMembreId}
 * comme clé primaire composite.
 * 
 */
public interface GroupeMembreRepository extends JpaRepository<GroupeMembre, GroupeMembreId>{

}
