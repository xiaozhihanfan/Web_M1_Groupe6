package miage.groupe6.reseausocial.model.entity;

import java.util.Date;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Représente l'inscription d'un utilisateur à un événement.
 * Cette entité est une table de jointure entre Utilisateur et Evenement.
 */
@Entity
@Table(name = "action_evenement")
public class ActionEvenement {
    
    /**
     * Clé primaire composite composée de idU (Utilisateur) et idE (Evenement).
     */
    @EmbeddedId
    private ActionEvenementId id = new ActionEvenementId();

    /**
     * Date et heure de l'action sur l'événement.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateActionEvenemnt;

    /**
     * Statut actuel de la évement (s'inscrire, s'intéresser).
     */
    @Enumerated(EnumType.STRING)
    private StatutActionEvenement statut;

    /**
     * Utilisateur s'inscrit/s'intéresse à l'événement.
     */
    @ManyToOne
    @MapsId("idU")
    @JoinColumn(name = "idU")
    private Utilisateur utilisateur;

    /**
     * Événement auquel l'utilisateur est inscrit/intéressé.
     */
    @ManyToOne
    @MapsId("idE")
    @JoinColumn(name = "idE")
    private Evenement evenement;

    // ==== Constructors ====

    /**
     * Constructeur par défaut requis par JPA.
     */
    public ActionEvenement() {}

    /**
     * Constructeur principal.
     *
     * @param dateActionEvenemnt la date de l'action
     * @param statut             le statut de l'action
     * @param utilisateur        l'utilisateur ayant effectué l'action
     * @param evenement          l'événement concerné
     */
    public ActionEvenement(Date dateActionEvenemnt, StatutActionEvenement statut, Utilisateur utilisateur, Evenement evenement) {
        this.id = new ActionEvenementId(utilisateur.getIdU(), evenement.getIdE());
        this.dateActionEvenemnt = dateActionEvenemnt;
        this.statut = statut;
        this.utilisateur = utilisateur;
        this.evenement = evenement;
    }

    // ==== Getters et Setters ====

    /**
     * Retourne la clé composite de l'action.
     *
     * @return l'identifiant composite
     */
    public ActionEvenementId getId() {
        return id;
    }

    /**
     * Définit la clé composite de l'action.
     *
     * @param id la nouvelle clé composite
     */
    public void setId(ActionEvenementId id) {
        this.id = id;
    }

    /**
     * Retourne la date de l'action.
     *
     * @return la date de l'action
     */
    public Date getDateActionEvenemnt() {
        return dateActionEvenemnt;
    }

     /**
     * Définit la date de l'action.
     *
     * @param dateActionEvenemnt la nouvelle date
     */
    public void setDateActionEvenemnt(Date dateActionEvenemnt) {
        this.dateActionEvenemnt = dateActionEvenemnt;
    }

    /**
     * Retourne le statut de l'action.
     *
     * @return le statut
     */
    public StatutActionEvenement getStatut() {
        return statut;
    }

     /**
     * Définit le statut de l'action.
     *
     * @param statut le nouveau statut
     */
    public void setStatut(StatutActionEvenement statut) {
        this.statut = statut;
    }

    /**
     * Retourne l'utilisateur ayant réalisé l'action.
     *
     * @return l'utilisateur
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * Définit l'utilisateur ayant réalisé l'action.
     *
     * @param utilisateur le nouvel utilisateur
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * Retourne l'événement concerné par l'action.
     *
     * @return l'événement
     */
    public Evenement getEvenement() {
        return evenement;
    }

    /**
     * Définit l'événement concerné par l'action.
     *
     * @param evenement le nouvel événement
     */
    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

}
