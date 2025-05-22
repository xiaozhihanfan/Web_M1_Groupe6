package miage.groupe6.reseausocial.model.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Repository interface for accessing Evenement entities.
 */
@Repository
public interface EvenementRepository extends JpaRepository<Evenement, Long>{
    /**
     * Récupère tous les événements créés par un utilisateur donné.
     *
     * @param organisateur l’utilisateur qui a créé l’événement
     * @return la liste des événements organisés
     */
    List<Evenement> findByUtilisateur(Utilisateur organisateur);

    /**
     * Récupère tous les événements auxquels un utilisateur a participé
     * avec un statut précis (INSCRIRE ou INTERESSER).
     *
     * @param participant l’utilisateur participant
     * @param statut      le statut de l’action (INSCRIRE ou INTERESSER)
     * @return la liste des événements associés
     */
    @Query("SELECT e FROM Evenement e JOIN e.actionEvenement ae "
         + "WHERE ae.utilisateur = :participant AND ae.statut = :statut")
    List<Evenement> findByParticipantAndStatut(
        @Param("participant") Utilisateur participant,
        @Param("statut") StatutActionEvenement statut);


    /**
     * Récupère tous les événements que l’utilisateur n’a ni créés
     * ni rejoints (avec statut INSCRIRE ou INTERESSER),
     * triés par date de début décroissante.
     *
     * @param utilisateur l’utilisateur courant
     * @return la liste des événements à découvrir
     */
    @Query("""
      SELECT e
      FROM Evenement e
      WHERE e.utilisateur <> :utilisateur
        AND e.idE NOT IN (
          SELECT ae.evenement.idE
          FROM ActionEvenement ae
          WHERE ae.utilisateur = :utilisateur
        )
      ORDER BY e.dateDebut DESC
      """)
    List<Evenement> findExploreEvents(@Param("utilisateur") Utilisateur utilisateur);

    int countByUtilisateur(Utilisateur utilisateur);

    Evenement save(Evenement newEvenement);
    
    List<Evenement> findAll();
}
