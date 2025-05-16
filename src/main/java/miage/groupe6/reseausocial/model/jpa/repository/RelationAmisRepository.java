package miage.groupe6.reseausocial.model.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import miage.groupe6.reseausocial.model.entity.RelationAmis;
import miage.groupe6.reseausocial.model.entity.RelationAmisId;
import miage.groupe6.reseausocial.model.entity.StatutRelation;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Repository JPA pour l'entité {@link RelationAmis}.
 * Fournit les opérations de base CRUD ainsi que des requêtes personnalisées.
 */
public interface RelationAmisRepository extends JpaRepository<RelationAmis, RelationAmisId>{

    // @Query("SELECT COUNT(*) FROM relation_amis WHERE (id_utilisateur_demande = :id OR id_utilisateur_recu = :id) AND statut = :statut")
    // Integer getNombreAmis(@Param("id") Long idUtilisateur, @Param("statut") StatutRelation statutRelation);

    //Count following
    int countByUtilisateurDemandeAndStatut(Utilisateur utilisateur, StatutRelation statut);

    //Count followers
    int countByUtilisateurRecuAndStatut(Utilisateur utilisateur, StatutRelation statut);
} 
