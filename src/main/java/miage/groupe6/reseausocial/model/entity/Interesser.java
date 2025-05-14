package miage.groupe6.reseausocial.model.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

/**
 * Représente l'intérêt d'un utilisateur pour un événement.
 * Association entre un utilisateur et un événement.
 */
@Entity
@Table(name = "s_interesser")
public class Interesser {

    /**
     * Clé primaire composite.
     */
    @EmbeddedId
    private InteresserId id = new InteresserId();

    /**
     * Utilisateur intéressé par l'événement.
     */
    @ManyToOne
    @MapsId("idU")
    @JoinColumn(name = "idU")
    private Utilisateur utilisateur;

    /**
     * Événement qui intéresse l'utilisateur.
     */
    @ManyToOne
    @MapsId("idE")
    @JoinColumn(name = "idE")
    private Evenement evenement;

    /**
     * Constructeur par défaut.
     */
    public Interesser() {
    }

    /**
     * Constructeur avec paramètres.
     *
     * @param evenement   événement
     * @param utilisateur utilisateur
     */
    public Interesser(Evenement evenement, Utilisateur utilisateur) {
        this.evenement = evenement;
        this.utilisateur = utilisateur;
        this.id = new InteresserId(utilisateur.getIdU(), evenement.getIdE());
    }

     /**
     * Retourne la clé composite.
     * @return id
     */
    public InteresserId getId() {
        return id;
    }

    /**
     * Définit la clé composite.
     * @param id clé composite
     */
    public void setId(InteresserId id) {
        this.id = id;
    }

    /**
     * Retourne l'utilisateur intéressé.
     * @return utilisateur
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

     /**
     * Définit l'utilisateur intéressé.
     * @param utilisateur utilisateur
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * Retourne l'événement concerné.
     * @return événement
     */
    public Evenement getEvenement() {
        return evenement;
    }

    /**
     * Définit l'événement concerné.
     * @param evenement événement
     */
    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

}
