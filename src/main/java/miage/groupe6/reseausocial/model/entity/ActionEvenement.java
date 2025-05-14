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
     * Utilisateur inscrit à l'événement.
     */
    @ManyToOne
    @MapsId("idU")
    @JoinColumn(name = "idU")
    private Utilisateur utilisateur;

    /**
     * Événement auquel l'utilisateur est inscrit.
     */
    @ManyToOne
    @MapsId("idE")
    @JoinColumn(name = "idE")
    private Evenement evenement;

    /**
     * Date et heure d'inscription.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateInscription;


    /**
     * Statut actuel de la post (like, unlike, republier).
     */
    @Enumerated(EnumType.STRING)
    private StatutActionEvenement statut;







    /**
     * Constructeur par défaut.
     */
    public ActionEvenement() {

    }

    /**
     * Constructeur avec paramètres.
     *
     * @param dateInscription date d'inscription
     * @param evenement       événement inscrit
     * @param utilisateur     utilisateur inscrit
     */
    public ActionEvenement(Date dateInscription, Evenement evenement, Utilisateur utilisateur) {
        this.dateInscription = dateInscription;
        this.evenement = evenement;
        this.utilisateur = utilisateur;
        this.id = new ActionEvenementId(utilisateur.getIdU(), evenement.getIdE());
    }

    /**
     * Retourne la clé composite d'inscription.
     * @return id
     */
    public ActionEvenementId getId() {
        return id;
    }

    /**
     * Définit la clé composite d'inscription.
     * @param id clé composite
     */
    public void setId(ActionEvenementId id) {
        this.id = id;
    }

    /**
     * Retourne l'utilisateur inscrit.
     * @return utilisateur
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * Définit l'utilisateur inscrit.
     * @param utilisateur utilisateur
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * Retourne l'événement concerné par l'inscription.
     * @return événement
     */
    public Evenement getEvenement() {
        return evenement;
    }

    /**
     * Définit l'événement concerné par l'inscription.
     * @param evenement événement
     */
    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    /**
     * Retourne la date d'inscription.
     * @return dateInscription
     */
    public Date getDateInscription() {
        return dateInscription;
    }

     /**
     * Définit la date d'inscription.
     * @param dateInscription date d'inscription
     */
    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }






}
