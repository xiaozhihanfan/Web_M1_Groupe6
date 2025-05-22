package miage.groupe6.reseausocial.model.jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.ActionEvenementRepository;
import miage.groupe6.reseausocial.model.jpa.repository.EvenementRepository;

@Service
public class EvenementsService {

	@Autowired
    private ActionEvenementRepository actionEvenementRepository;

	private final EvenementRepository evenementRepository;

	@Autowired
    public EvenementsService(EvenementRepository evenementRepository) {
		this.evenementRepository = evenementRepository;
    }


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
	
    public List<Evenement> findAll(){
    	return evenementRepository.findAll();
    }
	
}
