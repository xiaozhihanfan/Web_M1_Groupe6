package miage.groupe6.reseausocial.model.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.GroupeRepository;

@Service
public class GroupeService {

    @Autowired
    private GroupeRepository groupeRepository;

    public Groupe creerGroupe(Groupe groupe, Utilisateur createur) {
        groupe.setCreateur(createur);
        return groupeRepository.save(groupe);
    }
}
