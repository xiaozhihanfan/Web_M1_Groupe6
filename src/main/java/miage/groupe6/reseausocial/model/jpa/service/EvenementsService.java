package miage.groupe6.reseausocial.model.jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.ActionEvenementRepository;
import miage.groupe6.reseausocial.model.jpa.repository.EvenementRepository;

/**
 * Service de gestion des opérations sur les entités Evenement.
 */
@Service
public class EvenementsService {

	@Autowired
    private ActionEvenementRepository actionEvenementRepository;

	private final EvenementRepository evenementRepository;

	@Autowired
    public EvenementsService(EvenementRepository evenementRepository) {
		this.evenementRepository = evenementRepository;
    }


    /**
     * Enregistre un nouvel événement ou met à jour un événement existant.
     *
     * @param newEvenement l'entité {@link Evenement} à sauvegarder
     * @return l'entité {@link Evenement} sauvegardée avec son identifiant
     */
	public Evenement save(Evenement newEvenement) {
		return evenementRepository.save(newEvenement);
	}
	
	/**
     * Renvoie la liste des événements à découvrir pour un utilisateur,
     * c’est-à-dire qu’il n’a ni organisés ni rejoints.
     *
     * @param utilisateur l’utilisateur courant
     * @return liste des événements à afficher sur la page d’accueil
     */
    public List<Evenement> findExploreEvents(Utilisateur utilisateur) {
        return evenementRepository.findExploreEvents(utilisateur);
    }

    public int countEvenements(Utilisateur utilisateur) {
        int creeE = evenementRepository.countByUtilisateur(utilisateur);
        int inscrireE = actionEvenementRepository.countByUtilisateurAndStatut(utilisateur, StatutActionEvenement.INSCRIRE);

        return creeE + inscrireE;
    }
	
    /**
     * Récupère un événement par son identifiant, sans charger les relations.
     *
     * @param id l’identifiant de l’événement
     * @return l’entité {@link Evenement} trouvée
     * @throws EntityNotFoundException si aucun événement n’existe pour cet identifiant
     */
    public Evenement getEvenement(Long id) {
        return evenementRepository.findById(id)
            .orElseThrow(() ->
                new EntityNotFoundException("Événement introuvable pour id = " + id));
    }

    /**
     * Récupère un événement par son identifiant en chargeant toutes ses relations
     * (participants, commentaires).
     *
     * @param id l’identifiant de l’événement
     * @return l’entité {@link Evenement} entièrement peuplée
     * @throws EntityNotFoundException si aucun événement n’existe pour cet identifiant
     */
    public Evenement getEvenementAvecDetails(Long id) {
        return evenementRepository.findByIdWithDetails(id)
            .orElseThrow(() ->
                new EntityNotFoundException("Événement introuvable pour id = " + id));
    }

    /**
     * Retourne la liste de tous les événements.
     * @return tous les Evenement
     */
    public List<Evenement> findAll() {
        return evenementRepository.findAll();

    }
	
}
