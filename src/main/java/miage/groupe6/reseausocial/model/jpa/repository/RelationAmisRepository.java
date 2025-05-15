package miage.groupe6.reseausocial.model.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import miage.groupe6.reseausocial.model.entity.RelationAmis;
import miage.groupe6.reseausocial.model.entity.RelationAmisId;

/**
 * Repository JPA pour l'entité {@link RelationAmis}.
 * Fournit les opérations de base CRUD ainsi que des requêtes personnalisées.
 */
public interface RelationAmisRepository extends JpaRepository<RelationAmis, RelationAmisId>{

} 
