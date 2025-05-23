package miage.groupe6.reseausocial.model.jpa.repository;

import java.util.List;
import java.util.Optional;

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


    /**
     * Récupère un événement avec tous ses participants et commentaires.
     * <p>
     * La requête utilise des FETCH JOIN pour hydrater les collections
     * participants et commentaires dans l’entité {@code Evenement}.
     * </p>
     *
     * @param id l’identifiant de l’événement à charger
     * @return un {@link Optional} contenant l’événement détaillé si trouvé,
     *         sinon un Optional vide
     */
    @Query("""
      SELECT e
      FROM Evenement e
      LEFT JOIN FETCH e.utilisateur
      LEFT JOIN FETCH e.actionEvenement ae
      LEFT JOIN FETCH ae.utilisateur
      WHERE e.idE = :id
      """)
    Optional<Evenement> findByIdWithDetails(@Param("id") Long id);

    /**
     * Enregistre ou met à jour un événement.
     *
     * @param newEvenement l’événement à sauvegarder
     * @return l’événement sauvegardé
     */
    Evenement save(Evenement newEvenement);
    
    /**
     * Récupère tous les événements dans la base.
     *
     * @return la liste de tous les événements
     */
    List<Evenement> findAll();

}
