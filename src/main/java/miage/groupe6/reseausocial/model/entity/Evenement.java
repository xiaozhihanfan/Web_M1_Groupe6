package miage.groupe6.reseausocial.model.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Représente un événement créé par un utilisateur.
 * Un événement contient un titre, un lieu, une description,
 * une date de début et de fin, ainsi qu'un utilisateur créateur.
 */
@Entity
@Table(name = "Evenement")
public class Evenement {
    
    /**
     * Identifiant unique de l'événement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idE;

    /**
     * Titre de l'événement.
     * Lieu où se déroule l'événement.
     */
    private String titre;
    private String lieu;

    /**
     * Description détaillée de l'événement (max 500 caractères).
     */
    @Column(length = 500)
    private String descriptionE;

    /**
     * Date et heure de début de l'événement.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebut;

    /**
     * Date et heure de fin de l'événement.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFin;

    /**
     * Utilisateur ayant créé l'événement.
     */
    @ManyToOne
    @JoinColumn(name = "idU")
    private Utilisateur utilisateur;    // Créateur de l'événement

    /**
     * Constructeur par défaut.
     */
    public Evenement() {

    }

    /**
     * Constructeur avec paramètres.
     *
     * @param dateDebut     Date de début
     * @param dateFin       Date de fin
     * @param descriptionE  Description de l'événement
     * @param lieu          Lieu de l'événement
     * @param titre         Titre de l'événement
     * @param utilisateur   Utilisateur créateur
     */
    public Evenement(Date dateDebut, Date dateFin, String descriptionE, String lieu, String titre, Utilisateur utilisateur) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.descriptionE = descriptionE;
        this.lieu = lieu;
        this.titre = titre;
        this.utilisateur = utilisateur;
    }

    /**
     * Retourne l'identifiant unique de l'événement.
     * @return idE identifiant
     */
    public Long getIdE() {
        return idE;
    }

    /**
     * Définit l'identifiant unique de l'événement.
     * @param idE identifiant
     */
    public void setIdE(Long idE) {
        this.idE = idE;
    }

    /**
     * Retourne le titre de l'événement.
     * @return titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Définit le titre de l'événement.
     * @param titre titre
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Retourne le lieu de l'événement.
     * @return lieu
     */
    public String getLieu() {
        return lieu;
    }

    /**
     * Définit le lieu de l'événement.
     * @param lieu lieu
     */
    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    /**
     * Retourne la description de l'événement.
     * @return descriptionE
     */
    public String getDescriptionE() {
        return descriptionE;
    }

    /**
     * Définit la description de l'événement.
     * @param descriptionE description
     */
    public void setDescriptionE(String descriptionE) {
        this.descriptionE = descriptionE;
    }

    /**
     * Retourne la date de début de l'événement.
     * @return dateDebut
     */
    public Date getDateDebut() {
        return dateDebut;
    }

    /**
     * Définit la date de début de l'événement.
     * @param dateDebut date de début
     */
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * Retourne la date de fin de l'événement.
     * @return dateFin
     */
    public Date getDateFin() {
        return dateFin;
    }

    /**
     * Définit la date de fin de l'événement.
     * @param dateFin date de fin
     */
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * Retourne l'utilisateur créateur de l'événement.
     * @return utilisateur
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

     /**
     * Définit l'utilisateur créateur de l'événement.
     * @param utilisateur utilisateur
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    

}
