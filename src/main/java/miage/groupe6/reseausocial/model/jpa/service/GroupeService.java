package miage.groupe6.reseausocial.model.jpa.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.GroupeMembre;
import miage.groupe6.reseausocial.model.entity.GroupeMembreId;
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

    // -------------- us 3.1 créer une groupe ------------------
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

    // -------------- us 3.2 inviter mes amis dans une groupe ------------------
    public List<Utilisateur> listerMembres(Groupe groupe) {
        Set<GroupeMembre> membres = groupe.getMembres();
        List<Utilisateur> utilisateurs = new ArrayList<>();
        for (GroupeMembre membre : membres) {
            utilisateurs.add(membre.getUtilisateur());
        }
        return utilisateurs;
    }

    public void ajouterMembreAuGroupe(Utilisateur utilisateur, Groupe groupe) {
        
        if (groupeMembreRepository.existsById(new GroupeMembreId(utilisateur.getIdU(), groupe.getIdGroupe()))) {
            return;
        }

        GroupeMembre membre = new GroupeMembre();
        membre.setGroupe(groupe);
        membre.setUtilisateur(utilisateur);
        membre.setRole(MembreRole.MEMBRE); 
        membre.setDateAdhesion(LocalDateTime.now());

        groupeMembreRepository.save(membre);
    }

    public Optional<Groupe> getGroupeById(Long idGroupe) {
        return groupeRepository.findById(idGroupe);
    }

}
