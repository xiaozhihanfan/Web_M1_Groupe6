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

    // === Constructeurs ===
    public Post() {}

    public Post(String contenuP, String imageP, Date dateP, Utilisateur utilisateur) {
        this.contenuP = contenuP;
        this.imageP = imageP;
        this.dateP = dateP;
        this.utilisateur = utilisateur;
    }

    // === Getters et Setters ===
    public Long getIdP() {
        return idP;
    }

    public void setIdP(Long idP) {
        this.idP = idP;
    }

    public String getContenuP() {
        return contenuP;
    }

    public void setContenuP(String contenuP) {
        this.contenuP = contenuP;
    }

    public String getImageP() {
        return imageP;
    }

    public void setImageP(String imageP) {
        this.imageP = imageP;
    }

    public Date getDateP() {
        return dateP;
    }

    public void setDateP(Date dateP) {
        this.dateP = dateP;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
