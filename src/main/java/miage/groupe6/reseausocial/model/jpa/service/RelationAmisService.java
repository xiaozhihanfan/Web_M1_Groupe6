package miage.groupe6.reseausocial.model.jpa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import miage.groupe6.reseausocial.model.entity.RelationAmis;
import miage.groupe6.reseausocial.model.entity.RelationAmisId;
import miage.groupe6.reseausocial.model.entity.StatutRelation;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.RelationAmisRepository;

/**
 * Service de la couche métier pour les relations d’amitié (RelationAmis).
 * Fournit des opérations de comptage sur les relations acceptées,
 * en distinguant les utilisateurs suivis (« following »)
 * et les abonnés (« followers »).
 */
@Service
public class RelationAmisService {

    @Autowired
    private RelationAmisRepository rar;

    /**
     * Calcule le nombre total d'amis d'un utilisateur, en combinant les relations
     * acceptées où il est initiateur et celles où il est destinataire.
     *
     * @param utilisateur l'utilisateur dont on veut compter les amis
     * @return le nombre d'amis distincts
     */
    public int countAmis(Utilisateur utilisateur) {
        List<RelationAmis> envoyeurs = rar.findByUtilisateurDemandeAndStatut(utilisateur, StatutRelation.ACCEPTEE);
        List<RelationAmis> receveurs = rar.findByUtilisateurRecuAndStatut(utilisateur, StatutRelation.ACCEPTEE);

        Set<Utilisateur> amis = new HashSet<>();
        envoyeurs.forEach(r -> {
            if (!r.getUtilisateurRecu().getIdU().equals(utilisateur.getIdU())) {
                amis.add(r.getUtilisateurRecu());
            }
        });
        receveurs.forEach(r -> {
            if (!r.getUtilisateurDemande().getIdU().equals(utilisateur.getIdU())) {
                amis.add(r.getUtilisateurDemande());
            }
        });

        return amis.size();
    }

    
    /**
     * Vérifie si une demande d'amitié existe déjà entre deux utilisateurs.
     *
     * @param idDemandeur identifiant de l'utilisateur ayant envoyé la demande
     * @param idRecu      identifiant de l'utilisateur ayant reçu la demande
     * @return true si une relation existe déjà, false sinon
     */
    public boolean demandeExisteDeja(Long idDemandeur, Long idRecu) {
        return rar.existsById(new RelationAmisId(idDemandeur, idRecu));
    }

    /**
     * Envoie une nouvelle demande d'amitié d'un utilisateur à un autre si aucune
     * demande préalable n'existe.
     *
     * @param demandeur l'utilisateur qui envoie la demande
     * @param receveur  l'utilisateur qui reçoit la demande
     * @return true si la demande a été créée, false si une demande existait déjà
     */
    public boolean envoyerDemandeAmi(Utilisateur demandeur, Utilisateur receveur) {
        if(rar.findByUtilisateurDemandeIdUAndUtilisateurRecuIdU(demandeur.getIdU(), receveur.getIdU()).isPresent()) {
            return false;
        }

        RelationAmis relation = new RelationAmis(demandeur, receveur, new Date(), StatutRelation.TRAITEE);
        rar.save(relation);
        return true;
    }

    /**
     * Récupère la liste des demandes d'amitié reçues par un utilisateur,
     * dont le statut est TRAITEE.
     *
     * @param utilisateur l'utilisateur qui a reçu les demandes
     * @return liste des relations en attente de décision
     */
    public List<RelationAmis> getDemandesRecues(Utilisateur utilisateur) {
        return rar.findByUtilisateurRecuAndStatut(utilisateur, StatutRelation.TRAITEE);
    }


    /**
     * Accepte une demande d'amitié existante entre deux utilisateurs
     * en mettant à jour son statut en ACCEPTEE.
     *
     * @param idDemandeur identifiant de l'utilisateur ayant émis la demande
     * @param idReceveur  identifiant de l'utilisateur ayant reçu la demande
     * @return true si la mise à jour a été effectuée, false si la relation n'existe pas
     */
    public boolean accepterDemandeAmis(Long idDemandeur, Long idReceveur) {
        Optional<RelationAmis> relationOpt = rar.findById(new RelationAmisId(idDemandeur, idReceveur));
        if(relationOpt.isPresent()) {
            RelationAmis relation = relationOpt.get();
            relation.setStatut(StatutRelation.ACCEPTEE);
            rar.save(relation);
            
            return true;
        }
        return false;
    }

    /**
     * Refuse (supprime) une demande d'amitié entre deux utilisateurs.
     *
     * @param idDemandeur identifiant de l'utilisateur ayant émis la demande
     * @param idReceveur  identifiant de l'utilisateur ayant reçu la demande
     * @return true si la relation a été supprimée, false si elle n'existait pas
     */
    public boolean refuserDemandeAmis(Long idDemandeur, Long idReceveur) {
        if(rar.existsById(new RelationAmisId(idDemandeur, idReceveur))) {
            rar.deleteById(new RelationAmisId(idDemandeur, idReceveur));
            return true;
        }
        return false;
    }

    /**
     * Liste tous les amis d'un utilisateur, qu'il ait
     * envoyé ou reçu la demande, et dont le statut est ACCEPTEE.
     *
     * @param utilisateur l'utilisateur cible
     * @return la liste de ses amis
     */
    public List<Utilisateur> listerAmis(Utilisateur utilisateur) {
        // 1) Les relations qu'il a envoyées et qui ont été acceptées
        List<RelationAmis> envoyees = rar.findByUtilisateurDemandeAndStatut(utilisateur, StatutRelation.ACCEPTEE);
        // 2) Les relations qu'il a reçues et qu'il a acceptées
        List<RelationAmis> recues   = rar.findByUtilisateurRecuAndStatut(utilisateur, StatutRelation.ACCEPTEE);

        // 3) Fusionner en évitant les doublons
        Set<Utilisateur> amis = new HashSet<>();
        envoyees.forEach(r -> {
            if (!r.getUtilisateurRecu().getIdU().equals(utilisateur.getIdU())) {
                amis.add(r.getUtilisateurRecu());
            }
        });

        recues.forEach(r -> {
            if (!r.getUtilisateurDemande().getIdU().equals(utilisateur.getIdU())) {
                amis.add(r.getUtilisateurDemande());
            }
        });

        return new ArrayList<>(amis);
    }
    
    /**
     * Recherche toutes les relations d'amitié possibles entre deux utilisateurs,
     * quel que soit leur statut.
     *
     * @param idU1 identifiant du premier utilisateur
     * @param idU2 identifiant du second utilisateur
     * @return liste des relations correspondant aux deux identifiants fournis
     */
    public List<RelationAmis> findRelationByIds(Long idU1,Long idU2) {
        return rar.findRelationByIds(idU1, idU2);
    }
    
    /**
     * Supprime toutes les relations d'amitié entre deux utilisateurs,
     * identifiés par leurs IDs respectifs, dans une transaction.
     *
     * @param idU1 identifiant du premier utilisateur
     * @param idU2 identifiant du second utilisateur
     */
    @Transactional
    public void deleteRelationByIds(Long idU1,Long idU2) {
    	rar.deleteRelationByIds(idU1, idU2);
    }
    
    
    
}
