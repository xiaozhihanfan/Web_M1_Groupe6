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
     * Effectue (ou met à jour) une action d’un utilisateur sur un événement.
     *
     * @param idUtilisateur l’identifiant de l’utilisateur
     * @param idEvenement   l’identifiant de l’événement
     * @param statut        INSCRIRE ou INTERESSER
     * @return l’entité ActionEvenement sauvegardée
     * @throws ResponseStatusException 404 si utilisateur ou événement introuvable
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
     * Inscrit l’utilisateur à l’événement.
     */
    public ActionEvenement inscrireEvent(Long idUtilisateur, Long idEvenement) {
        return actOnEvent(idUtilisateur, idEvenement, StatutActionEvenement.INSCRIRE);
    }

    /**
     * Marque l’utilisateur comme intéressé à l’événement.
     */
    public ActionEvenement interesserEvent(Long idUtilisateur, Long idEvenement) {
        return actOnEvent(idUtilisateur, idEvenement, StatutActionEvenement.INTERESSER);
    }



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

    public long countInscriptions(Long idEvenement) {
        return aer.countByEvenementIdEAndStatut(idEvenement, StatutActionEvenement.INSCRIRE);
    }

    public long countInteresses(Long idEvenement) {
        return aer.countByEvenementIdEAndStatut(idEvenement, StatutActionEvenement.INTERESSER);
    }

}
