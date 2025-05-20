package miage.groupe6.reseausocial.model.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import miage.groupe6.reseausocial.model.entity.ActionEvenement;
import miage.groupe6.reseausocial.model.entity.ActionEvenementId;
import miage.groupe6.reseausocial.model.entity.Evenement;
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
}
