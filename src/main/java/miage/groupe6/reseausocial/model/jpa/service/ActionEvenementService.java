package miage.groupe6.reseausocial.model.jpa.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import miage.groupe6.reseausocial.model.entity.ActionEvenement;
import miage.groupe6.reseausocial.model.entity.ActionEvenementId;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.ActionEvenementRepository;
import miage.groupe6.reseausocial.model.jpa.repository.EvenementRepository;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;

/**
 * Service pour gérer les inscriptions et intérêts aux événements.
 * <p>
 * Fournit des opérations de création, mise à jour, et comptage des actions
 * (INSCRIRE, INTERESSER) réalisées par les utilisateurs sur les événements.
 * </p>
 */
@Service
public class ActionEvenementService {

    private final ActionEvenementRepository aer;
    private final UtilisateurRepository    ur;
    private final EvenementRepository      er;

    @Autowired
    public ActionEvenementService(ActionEvenementRepository aer,
                                  UtilisateurRepository ur,
                                  EvenementRepository er) {
        this.aer = aer;
        this.ur   = ur;
        this.er   = er;
    }

    /**
     * Effectue ou met à jour une action d’un utilisateur sur un événement.
     * <p>
     * Cherche l’utilisateur et l’événement en base. Si une action existe déjà,
     * elle est mise à jour ; sinon, une nouvelle entité est créée.
     * Le statut et la date de l’action sont mis à jour avant sauvegarde.
     * </p>
     *
     * @param idUtilisateur l’identifiant de l’utilisateur réalisant l’action
     * @param idEvenement   l’identifiant de l’événement ciblé
     * @param statut        le {@link StatutActionEvenement} à appliquer
     * @return l’entité {@link ActionEvenement} sauvegardée
     * @throws ResponseStatusException si l’utilisateur ou l’événement n’est pas trouvé (404)
     */
    public ActionEvenement actOnEvent(Long idUtilisateur,
                                      Long idEvenement,
                                      StatutActionEvenement statut) {
        Utilisateur u = ur.findById(idUtilisateur)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Utilisateur non trouvé : " + idUtilisateur));
        Evenement e = er.findById(idEvenement)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Événement non trouvé : " + idEvenement));

        // 1) Si déjà une action existante, on la met à jour
        Optional<ActionEvenement> exist = aer.findByUtilisateurAndEvenement(u, e);
        ActionEvenement ae = exist.orElseGet(() -> {
            ActionEvenement newAe = new ActionEvenement();
            newAe.setUtilisateur(u);
            newAe.setEvenement(e);
            return newAe;
        });

        // 2) Mettre à jour statut et date
        ae.setStatut(statut);
        ae.setDateActionEvenemnt(new Date());

        // 3) Sauvegarder
        return aer.save(ae);
    }

    /**
     * Inscrit l’utilisateur à un événement.
     *
     * @param idUtilisateur identifiant de l’utilisateur
     * @param idEvenement   identifiant de l’événement
     * @return l’entité {@link ActionEvenement} résultante
     */
    public ActionEvenement inscrireEvent(Long idUtilisateur, Long idEvenement) {
        return actOnEvent(idUtilisateur, idEvenement, StatutActionEvenement.INSCRIRE);
    }

    /**
     * Marque l’utilisateur comme intéressé par un événement.
     *
     * @param idUtilisateur identifiant de l’utilisateur
     * @param idEvenement   identifiant de l’événement
     * @return l’entité {@link ActionEvenement} résultante
     */
    public ActionEvenement interesserEvent(Long idUtilisateur, Long idEvenement) {
        return actOnEvent(idUtilisateur, idEvenement, StatutActionEvenement.INTERESSER);
    }


    /**
     * Ajoute une nouvelle action si elle n’existe pas déjà.
     * <p>
     * Crée une entité {@link ActionEvenement} avec date et statut, et l’associe
     * à l’utilisateur et à l’événement spécifiés. Ne fait rien si l’action existe déjà.
     * </p>
     *
     * @param idUtilisateur identifiant de l’utilisateur
     * @param idEvenement   identifiant de l’événement
     * @param statut        statut de l’action à créer
     * @throws RuntimeException si l’utilisateur ou l’événement n’est pas trouvé
     */
    public void ajouterAction(Long idUtilisateur, Long idEvenement, StatutActionEvenement statut) {
        ActionEvenementId id = new ActionEvenementId(idUtilisateur, idEvenement);

        if (!aer.existsById(id)) {
            ActionEvenement action = new ActionEvenement();
            action.setId(id);
            action.setStatut(statut);

            Date dateAction = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
            action.setDateActionEvenemnt(dateAction);

            Evenement evt = er.findById(idEvenement)
                .orElseThrow(() -> new RuntimeException("Evenement introuvable : " + idEvenement));
            action.setEvenement(evt);

            Utilisateur user = ur.findById(idUtilisateur)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable : " + idUtilisateur));
            action.setUtilisateur(user);

            aer.save(action);
        }
    }

    /**
     * Compte le nombre d’inscriptions (statut INSCRIRE)
     * pour un événement donné.
     *
     * @param idEvenement identifiant de l’événement
     * @return nombre total d’inscriptions
     */
    public long countInscriptions(Long idEvenement) {
        return aer.countByEvenementIdEAndStatut(idEvenement, StatutActionEvenement.INSCRIRE);
    }

    /**
     * Compte le nombre d’intérêts (statut INTERESSER)
     * pour un événement donné.
     *
     * @param idEvenement identifiant de l’événement
     * @return nombre total d’intérêts
     */
    public long countInteresses(Long idEvenement) {
        return aer.countByEvenementIdEAndStatut(idEvenement, StatutActionEvenement.INTERESSER);
    }

    /**
     * Recherche l’action d’un utilisateur sur un événement, si elle existe.
     *
     * @param idUtilisateur identifiant de l’utilisateur
     * @param idEvenement   identifiant de l’événement
     * @return {@link Optional} contenant l’action si présente, sinon empty
     * @throws ResponseStatusException si l’utilisateur ou l’événement n’est pas trouvé
     */
    public Optional<ActionEvenement> findByUserAndEvent(Long idUtilisateur, Long idEvenement) {
        Utilisateur u = ur.findById(idUtilisateur)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Utilisateur non trouvé : " + idUtilisateur));
        Evenement e = er.findById(idEvenement)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Événement non trouvé : " + idEvenement));
        return aer.findByUtilisateurAndEvenement(u, e);
    }

}
