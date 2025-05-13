package miage.groupe6.reseausocial.model.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idP;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenuP;

    private String imageP;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateP;

    // === Relations éventuelles ===
    @ManyToOne
    @JoinColumn(name = "idU")
    private Utilisateur utilisateur;

    // === Getters et Setters ===
    /**
     * @return Long return the idP (identifiant de la publication)
     */
    public Long getIdP() {
        return idP;
    }

    /**
     * @param idP the idP to set (identifiant de la publication)
     */
    public void setIdP(Long idP) {
        this.idP = idP;
    }

    /**
     * @return String return the contenuP (contenu textuel de la publication)
     */
    public String getContenuP() {
        return contenuP;
    }

    /**
     * @param contenuP the contenuP to set (contenu textuel de la publication)
     */
    public void setContenuP(String contenuP) {
        this.contenuP = contenuP;
    }

    /**
     * @return String return the imageP (chemin ou URL de l'image liée à la publication)
     */
    public String getImageP() {
        return imageP;
    }

    /**
     * @param imageP the imageP to set (chemin ou URL de l'image liée à la publication)
     */
    public void setImageP(String imageP) {
        this.imageP = imageP;
    }

    /**
     * @return Date return the dateP (date et heure de publication)
     */
    public Date getDateP() {
        return dateP;
    }

    /**
     * @param dateP the dateP to set (date et heure de publication)
     */
    public void setDateP(Date dateP) {
        this.dateP = dateP;
    }

    /**
     * @return Utilisateur return the utilisateur (auteur de la publication)
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * @param utilisateur the utilisateur to set (auteur de la publication)
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

}