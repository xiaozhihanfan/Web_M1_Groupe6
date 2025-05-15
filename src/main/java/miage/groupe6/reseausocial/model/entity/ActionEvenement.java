package miage.groupe6.reseausocial.model.entity;

import java.util.Date;

import jakarta.persistence.*;

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

    /**
     * Date et heure.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateActionEvenemnt;


    /**
     * Statut actuel de la évement (s'inscrire, s'intéresser).
     */
    @Enumerated(EnumType.STRING)
    private StatutActionEvenement statut;





    // ==== Constructors ====

    /**
     * Constructeur par défaut.
     */
    public ActionEvenement() {}

    /**
     * Constructeur avec paramètres.
     *
     * @param dateActionEvenemnt date de cette action
     * @param evenement       événement
     * @param utilisateur     utilisateur
     */
    public ActionEvenement(Date dateActionEvenemnt, Evenement evenement, Utilisateur utilisateur) {
        this.dateActionEvenemnt = dateActionEvenemnt;
        this.evenement = evenement;
        this.utilisateur = utilisateur;
        this.id = new ActionEvenementId(utilisateur.getIdU(), evenement.getIdE());
    }

    /**
     * Retourne la clé composite de cette action.
     * @return id
     */
    public ActionEvenementId getId() {
        return id;
    }

    /**
     * Définit la clé composite de cette action.
     * @param id clé composite
     */
    public void setId(ActionEvenementId id) {
        this.id = id;
    }

    /**
     * Retourne l'utilisateur fait action.
     * @return utilisateur
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * Définit l'utilisateur fait action.
     * @param utilisateur utilisateur
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * Retourne l'événement concerné par l'action.
     * @return événement
     */
    public Evenement getEvenement() {
        return evenement;
    }

    /**
     * Définit l'événement concerné par l'action.
     * @param evenement événement
     */
    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }


    
    /**
     * Retourne la date de cette action.
     * @return dateActionEvenemnt
     */
    public Date getDateActionEvenemnt() {
        return dateActionEvenemnt;
    }

     /**
     * Définit la date de cette action.
     * @param dateActionEvenemnt date de cette action
     */
    public void setDateActionEvenemnt(Date dateActionEvenemnt) {
        this.dateActionEvenemnt = dateActionEvenemnt;
    }



    /**
     * @return le type d’action effectuée (INSCRIRE, INTERESSER)
     */
    public StatutActionEvenement getStatut() {
        return statut;
    }

    /**
     * @param statut définit le type d’action effectuée
     */
    public void setStatut(StatutActionEvenement statut) {
        this.statut = statut;
    }

    




}
