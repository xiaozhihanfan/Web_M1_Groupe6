package miage.groupe6.reseausocial.model.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import miage.groupe6.reseausocial.model.entity.ActionEvenement;
import miage.groupe6.reseausocial.model.entity.ActionEvenementId;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Repository JPA pour l'entité {@link ActionEvenement}.
 * Gère les opérations de persistance concernant les relations de type "like"
 * entre les utilisateurs et les évenement.
 * <p>
 * Utilise {@link ActionEvenementId} comme clé primaire composite.
 */
public interface ActionEvenementRepository extends JpaRepository<ActionEvenement, ActionEvenementId>{

    /**
     * Recherche l’action (INSCRIRE / INTERESSER) d’un utilisateur sur un événement.
     */
    Optional<ActionEvenement> findByUtilisateurAndEvenement(
        Utilisateur utilisateur, Evenement evenement
    );


    /**
     * Compte le nombre d'utilisateurs ayant un certain statut (ex: INSCRIRE, INTERESSER)
     * pour un événement spécifique.
     *
     * @param idE l'identifiant de l'événement
     * @param statut le statut recherché
     * @return le nombre d'actions correspondant
     */
    long countByEvenementIdEAndStatut(Long idE, StatutActionEvenement statut);

    /**
     * Compte le nombre total d’actions d’un certain type effectuées par un utilisateur.
     *
     * @param utilisateur l'utilisateur concerné
     * @param statut le type d'action (INSCRIRE, INTERESSER, etc.)
     * @return le nombre d'actions de ce type pour cet utilisateur
     */
    int countByUtilisateurAndStatut(Utilisateur utilisateur, StatutActionEvenement statut);

}
