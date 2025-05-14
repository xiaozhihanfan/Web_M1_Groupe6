package miage.groupe6.reseausocial.model.entity;

import java.util.Date;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@Table(name = "s_inscrire")
public class Inscrire {
    
    /**
     * Clé primaire composite composée de idU (Utilisateur) et idE (Evenement).
     */
    @EmbeddedId
    private InscrireId id = new InscrireId();

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
     * Constructeur par défaut.
     */
    public Inscrire() {

    }

    /**
     * Constructeur avec paramètres.
     *
     * @param dateInscription date d'inscription
     * @param evenement       événement inscrit
     * @param utilisateur     utilisateur inscrit
     */
    public Inscrire(Date dateInscription, Evenement evenement, Utilisateur utilisateur) {
        this.dateInscription = dateInscription;
        this.evenement = evenement;
        this.utilisateur = utilisateur;
        this.id = new InscrireId(utilisateur.getIdU(), evenement.getIdE());
    }

    /**
     * Retourne la clé composite d'inscription.
     * @return id
     */
    public InscrireId getId() {
        return id;
    }

    /**
     * Définit la clé composite d'inscription.
     * @param id clé composite
     */
    public void setId(InscrireId id) {
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
