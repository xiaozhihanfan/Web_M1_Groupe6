package miage.groupe6.reseausocial.model.jpa.service;

import java.util.Date;

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

}
