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
     * Date et heure.
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
     * Constructeur par défaut.
     */
    public ActionEvenement() {}

    public ActionEvenement(Date dateActionEvenemnt, StatutActionEvenement statut, Utilisateur utilisateur, Evenement evenement) {
        this.id = new ActionEvenementId(utilisateur.getIdU(), evenement.getIdE());
        this.dateActionEvenemnt = dateActionEvenemnt;
        this.statut = statut;
        this.utilisateur = utilisateur;
        this.evenement = evenement;
    }




    
    public ActionEvenementId getId() {
        return id;
    }

    public void setId(ActionEvenementId id) {
        this.id = id;
    }

    public Date getDateActionEvenemnt() {
        return dateActionEvenemnt;
    }

    public void setDateActionEvenemnt(Date dateActionEvenemnt) {
        this.dateActionEvenemnt = dateActionEvenemnt;
    }

    public StatutActionEvenement getStatut() {
        return statut;
    }

    public void setStatut(StatutActionEvenement statut) {
        this.statut = statut;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }



}
