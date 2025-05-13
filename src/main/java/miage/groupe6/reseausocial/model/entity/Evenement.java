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

@Entity
@Table(name = "Evenement")
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idE;

    private String titre;
    private String lieu;

    @Column(length = 500)
    private String descriptionE;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebut;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFin;

    @ManyToOne
    @JoinColumn(name = "idU")
    private Utilisateur utilisateur;    // Créateur de l'événement

    public Evenement() {

    }

    public Evenement(Date dateDebut, Date dateFin, String descriptionE, String lieu, String titre, Utilisateur utilisateur) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.descriptionE = descriptionE;
        this.lieu = lieu;
        this.titre = titre;
        this.utilisateur = utilisateur;
    }

    public Long getIdE() {
        return idE;
    }

    public void setIdE(Long idE) {
        this.idE = idE;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescriptionE() {
        return descriptionE;
    }

    public void setDescriptionE(String descriptionE) {
        this.descriptionE = descriptionE;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    

}
