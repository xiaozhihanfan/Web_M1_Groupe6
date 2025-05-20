package miage.groupe6.reseausocial.model.jpa.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.GroupeMembre;
import miage.groupe6.reseausocial.model.entity.MembreRole;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.GroupeMembreRepository;
import miage.groupe6.reseausocial.model.jpa.repository.GroupeRepository;

@Service
public class GroupeService {

    @Autowired
    private GroupeRepository groupeRepository;

    @Autowired
    private GroupeMembreRepository groupeMembreRepository;

    public Groupe creerGroupe(Groupe groupe, Utilisateur createur) {
        groupe.setCreateur(createur);
        Groupe savedGroupe = groupeRepository.save(groupe);

        GroupeMembre membre = new GroupeMembre();
        membre.setGroupe(groupe);
        membre.setUtilisateur(createur);
        membre.setRole(MembreRole.ADMIN);
        membre.setDateAdhesion(LocalDateTime.now());

        groupeMembreRepository.save(membre);

        return savedGroupe;
    }
}
