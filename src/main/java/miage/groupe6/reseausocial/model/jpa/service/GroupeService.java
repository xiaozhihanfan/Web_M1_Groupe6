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

/**
 * Service de gestion des groupes dans le réseau social.
 * <p>
 * Ce service permet la création de groupes, l'ajout de membres,
 * la récupération des membres d'un groupe et la liste des groupes
 * auxquels un utilisateur participe.
 */
@Service
public class GroupeService {

    @Autowired
    private GroupeRepository groupeRepository;

    @Autowired
    private GroupeMembreRepository groupeMembreRepository;

    // -------------- us 3.1 créer une groupe ------------------
    /**
     * Crée un nouveau groupe et y ajoute le créateur comme membre avec un rôle ADMIN.
     *
     * @param groupe   entité du groupe à créer
     * @param createur utilisateur créateur du groupe
     * @return le groupe enregistré
     */
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
    /**
     * Retourne la liste des utilisateurs membres d’un groupe donné.
     *
     * @param groupe le groupe concerné
     * @return liste des utilisateurs membres
     */
    public List<Utilisateur> listerMembres(Groupe groupe) {
        Set<GroupeMembre> membres = groupe.getMembres();
        List<Utilisateur> utilisateurs = new ArrayList<>();
        for (GroupeMembre membre : membres) {
            utilisateurs.add(membre.getUtilisateur());
        }
        return utilisateurs;
    }

    /**
     * Ajoute un utilisateur au groupe s’il n’en est pas déjà membre.
     * L’utilisateur reçoit le rôle MEMBRE par défaut.
     *
     * @param utilisateur utilisateur à ajouter
     * @param groupe      groupe auquel l'utilisateur est ajouté
     */
    public void ajouterMembreAuGroupe(Utilisateur utilisateur, Groupe groupe) {
        GroupeMembreId id = new GroupeMembreId(groupe.getIdGroupe(), utilisateur.getIdU());

        if (groupeMembreRepository.existsById(id)) {
            return;
        }

        GroupeMembre membre = new GroupeMembre();
        membre.setId(id);
        membre.setGroupe(groupe);
        membre.setUtilisateur(utilisateur);
        membre.setRole(MembreRole.MEMBRE); 
        membre.setDateAdhesion(LocalDateTime.now());

        // groupe.getMembres().add(membre);

        groupeMembreRepository.save(membre);
    }

    /**
     * Récupère un groupe à partir de son identifiant.
     *
     * @param idGroupe identifiant du groupe
     * @return groupe correspondant s’il existe, sinon un Optional vide
     */
    public Optional<Groupe> getGroupeById(Long idGroupe) {
        return groupeRepository.findById(idGroupe);
    }

    // --------------- us 4.2 chat de gourpe ----------------------------
    /**
     * Retourne la liste des groupes dans lesquels l’utilisateur est membre.
     *
     * @param utilisateur utilisateur concerné
     * @return liste des groupes où il est membre
     */
    public List<Groupe> getGroupesOuEtreMembre(Utilisateur utilisateur) {
        List<GroupeMembre> membres = groupeMembreRepository.findByUtilisateur(utilisateur);
        List<Groupe> groupes = new ArrayList<>();

        for(GroupeMembre membre : membres) {
            groupes.add(membre.getGroupe());
        }

        return groupes;
    }
}
