package miage.groupe6.reseausocial.model.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    Optional<RelationAmis> findByUtilisateurDemandeIdUAndUtilisateurRecuIdU(Long idDemande, Long idRecu);

    boolean existsById(RelationAmisId id);



    /**
     * Récupère toutes les relations où l'utilisateur est receveur et le statut est ACCETPTEE.
     *
     * @param utilisateurRecu l'utilisateur ayant reçu la demande d'amitié
     * @param statut          le statut de la relation (doit être ACCEPTEE)
     * @return la liste des relations d'amitié où l'utilisateur a accepté une demande
     */
    List<RelationAmis> findByUtilisateurRecuAndStatut(Utilisateur utilisateurRecu, StatutRelation statut);

    /**
     * Récupère toutes les relations où l'utilisateur est demandeur et le statut est ACCEPTEE.
     * 
     *
     * @param utilisateurDemande l'utilisateur ayant envoyé la demande d'amitié
     * @param statut             le statut de la relation (doit être ACCEPTEE)
     * @return la liste des relations d'amitié où l'utilisateur est à l'origine de la demande
     */
    List<RelationAmis> findByUtilisateurDemandeAndStatut(Utilisateur utilisateurDemande, StatutRelation statut);
    
    @Query("SELECT r FROM RelationAmis r WHERE (r.utilisateurDemande.idU = :idU1 AND r.utilisateurRecu.idU = :idU2) OR (r.utilisateurDemande.idU = :idU2 AND r.utilisateurRecu.idU = :idU1)")
    RelationAmis findRelationByIds(Long idU1, Long idU2);    
    
    
    @Modifying
    @Query("DELETE FROM RelationAmis r WHERE (r.utilisateurDemande.idU = :idU1 AND r.utilisateurRecu.idU = :idU2) OR (r.utilisateurDemande.idU = :idU2 AND r.utilisateurRecu.idU = :idU1)")
    void deleteRelationByIds( Long idU1, Long idU2);
} 
