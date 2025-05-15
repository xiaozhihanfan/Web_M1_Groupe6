package miage.groupe6.reseausocial.model.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Représente une post dans le réseau social.
 * Chaque post contient un contenu textuel, une image optionnelle,
 * une date de post et un auteur (utilisateur).
 */
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


    // === Relations ===
    @ManyToOne
    @JoinColumn(name = "idU")
    private Utilisateur auteur;

    // Like/Unlike/Republier
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<ActionPost> utilisateurs = new HashSet<>();

    // un post a des commentaires
    @OneToMany(mappedBy = "idC", cascade = CascadeType.ALL)
    private Set<Commentaire> commentaires = new HashSet<>();


    // ===== Constructors ====

    public Post() {}

    public Post(Long idP, String contenuP, String imageP, Date dateP, Utilisateur auteur, Set<ActionPost> utilisateurs,
            Set<Commentaire> commentaires) {
        this.idP = idP;
        this.contenuP = contenuP;
        this.imageP = imageP;
        this.dateP = dateP;
        this.auteur = auteur;
        this.utilisateurs = utilisateurs;
        this.commentaires = commentaires;
    }





    
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

    public Utilisateur getAuteur() {
        return auteur;
    }

    public void setAuteur(Utilisateur auteur) {
        this.auteur = auteur;
    }

    public Set<ActionPost> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Set<ActionPost> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public Set<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    
    

}