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
 * Représente l'action d'un utilisateur sur un événement (inscription ou intérêt).
 * <p>
 * Cette entité constitue la table de jointure entre {@link Utilisateur} et {@link Evenement}.
 * </p>
 */
@Entity
@Table(name = "action_evenement")
public class ActionEvenement {
    
    /**
     * Clé primaire composite composée de l'identifiant de l'utilisateur ({@code idU})
     * et de l'identifiant de l'événement ({@code idE}).
     */
    @EmbeddedId
    private ActionEvenementId id = new ActionEvenementId();

    /**
     * Date et heure auxquelles l'utilisateur a effectué l'action sur l'événement.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateActionEvenemnt;

    /**
     * Statut de l'action réalisée par l'utilisateur
     * (par exemple : INSCRIT, INTERESSE).
     */
    @Enumerated(EnumType.STRING)
    private StatutActionEvenement statut;

    /**
     * L'utilisateur qui réalise l'action.
     */
    @ManyToOne
    @MapsId("idU")
    @JoinColumn(name = "idU")
    private Utilisateur utilisateur;

    /**
     * L'événement concerné par l'action de l'utilisateur.
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
        this.id = new ActionEvenementId(evenement.getIdE(), utilisateur.getIdU());
        this.dateActionEvenemnt = dateActionEvenemnt;
        this.statut = statut;
        this.utilisateur = utilisateur;
        this.evenement = evenement;
    }

    // ==== Getters et Setters ====

    /**
     * Retourne la clé composite de cette action.
     *
     * @return {@link ActionEvenementId} représentant la clé primaire composite
     */
    public ActionEvenementId getId() {
        return id;
    }

    /**
     * Définit la clé composite de cette action.
     *
     * @param id nouvelle clé primaire composite
     */
    public void setId(ActionEvenementId id) {
        this.id = id;
    }

    /**
     * Retourne la date et l'heure de l'action.
     *
     * @return date et heure de l'action
     */
    public Date getDateActionEvenement() {
        return dateActionEvenemnt;
    }

    /**
     * Définit la date et l'heure de l'action.
     *
     * @param dateActionEvenement nouvelle date et heure de l'action
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
     * @return instance de {@link Utilisateur}
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

   /**
     * Définit l'utilisateur ayant réalisé l'action.
     *
     * @param utilisateur nouvel utilisateur lié à cette action
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * Retourne l'événement concerné par cette action.
     *
     * @return instance de {@link Evenement}
     */
    public Evenement getEvenement() {
        return evenement;
    }

    /**
     * Définit l'événement concerné par cette action.
     *
     * @param evenement nouvel événement lié à cette action
     */
    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

}
