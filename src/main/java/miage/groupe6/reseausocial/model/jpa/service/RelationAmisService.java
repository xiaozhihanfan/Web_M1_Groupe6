package miage.groupe6.reseausocial.model.jpa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * 
 */

@Service
public class RelationAmisService {

    @Autowired
    private RelationAmisRepository rar;

    /**
     * Compte le nombre d’utilisateurs que l’on suit et dont la demande a été acceptée.
     * Sélectionne dans la table des relations d’amis les enregistrements
     * où l’utilisateur est l’initiateur (utilisateurDemande)
     * et dont le statut est {@link StatutRelation#ACCEPTEE}.
     *
     * @param utilisateur l’entité utilisateur dont on veut compter les suivis acceptés
     * @return le nombre de suivis acceptés
     */
    public int countFollowingAccepte(Utilisateur utilisateur){
        int res = rar.countByUtilisateurDemandeAndStatut(utilisateur, StatutRelation.ACCEPTEE);
        return res;
    }

    /**
     * Compte le nombre d’utilisateurs qui suivent l’utilisateur
     * et dont la relation a été acceptée.
     * Sélectionne dans la table des relations d’amis les enregistrements
     * où l’utilisateur est le destinataire (utilisateurRecu)
     * et dont le statut est {@link StatutRelation#ACCEPTEE}.
     *
     * @param utilisateur l’entité utilisateur dont on veut compter les abonnés acceptés
     * @return le nombre d’abonnés acceptés
     */
    public int countFollowersAccepte(Utilisateur utilisateur){
        int res = rar.countByUtilisateurRecuAndStatut(utilisateur, StatutRelation.ACCEPTEE);
        return res;
    }

    public int countRelationsAcceptees(Utilisateur utilisateur) {
        int countDemande = rar.countByUtilisateurDemandeAndStatut(utilisateur, StatutRelation.ACCEPTEE);
        int countRecu = rar.countByUtilisateurRecuAndStatut(utilisateur, StatutRelation.ACCEPTEE);
        return countDemande + countRecu;
    }

    // ----------------------- us 1.4 Envoyer une demande d’ami ---------------------

    public boolean demandeExisteDeja(Long idDemandeur, Long idRecu) {
        return rar.existsById(new RelationAmisId(idDemandeur, idRecu));
    }

    public boolean envoyerDemandeAmi(Utilisateur demandeur, Utilisateur receveur) {
        if(rar.findByUtilisateurDemandeIdUAndUtilisateurRecuIdU(demandeur.getIdU(), receveur.getIdU()).isPresent()) {
            return false;
        }

        RelationAmis relation = new RelationAmis(demandeur, receveur, new Date(), StatutRelation.TRAITEE);
        rar.save(relation);
        return true;
    }

    // ----------------------- us 1.5 Accepter ou refuser une demande d’ami ---------------------
    public List<RelationAmis> getDemandesRecues(Utilisateur utilisateur) {
        return rar.findByUtilisateurRecuAndStatut(utilisateur, StatutRelation.TRAITEE);
    }


    public boolean accepterDemandeAmis(Long idDemandeur, Long idReceveur) {
        Optional<RelationAmis> relationOpt = rar.findById(new RelationAmisId(idDemandeur, idReceveur));
        if(relationOpt.isPresent()) {
            RelationAmis relation = relationOpt.get();
            relation.setStatut(StatutRelation.ACCEPTEE);
            rar.save(relation);
            
            RelationAmisId miroirId = new RelationAmisId(idReceveur, idDemandeur);
            if(!rar.existsById(miroirId)) {
                RelationAmis miroir = new RelationAmis(
                    relation.getUtilisateurRecu(),
                    relation.getUtilisateurRecu(),
                    new Date(),
                    StatutRelation.ACCEPTEE
                );
                rar.save(miroir);
            }
            
            return true;
        }
        return false;
    }

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
        envoyees.forEach(r -> amis.add(r.getUtilisateurRecu()));
        recues  .forEach(r -> amis.add(r.getUtilisateurDemande()));

        return new ArrayList<>(amis);
    }
}
