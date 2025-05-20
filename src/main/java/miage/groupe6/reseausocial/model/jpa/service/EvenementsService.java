package miage.groupe6.reseausocial.model.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.EvenementRepository;
import miage.groupe6.reseausocial.model.jpa.repository.PostRepository;

@Service
public class EvenementsService {
	@Autowired
    private final EvenementRepository EvenementRepository;

	@Autowired
    public EvenementsService(EvenementRepository EvenementRepository) {
        this.EvenementRepository = EvenementRepository;
    }


	public Evenement save(Evenement newEvenement) {
		return EvenementRepository.save(newEvenement);
	}
	
	
	
}
