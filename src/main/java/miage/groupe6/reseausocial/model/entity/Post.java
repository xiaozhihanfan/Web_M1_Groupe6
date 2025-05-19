package miage.groupe6.reseausocial.model.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Représente une post dans le réseau social.
 * Chaque post contient un contenu textuel, une image optionnelle,
 * une date de post et un auteur (utilisateur).
 */
@Entity
@Table(name = "post")
public class Post {

    /**
     * Identifiant unique du post.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idP;

    /* *
     * contenu textuel du post.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenuP;

    /**
     * Chemin ou URL de l’image associée au post (optionnelle).
     */
    private String imageP;

    /**
     * Date et heure de création du post.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateP;

    private transient int nombreLikes;


    // === Relations ===

    /**
     * Utilisateur ayant publié le post.
     */
    @ManyToOne
    @JoinColumn(name = "idU")
    private Utilisateur auteur;

    /**
     * Ensemble des interactions utilisateur sur ce post.
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<ActionPost> utilisateurs = new HashSet<>();
    
    /**
     * Ensemble des commentaires associés à ce post.
     */
    @OneToMany(mappedBy = "idC", cascade = CascadeType.ALL)
    private Set<Commentaire> commentaires = new HashSet<>();


    // ===== Constructors ====

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Post() {}

    /**
     * Constructeur complet.
     *
     * @param idP identifiant du post
     * @param contenuP contenu textuel
     * @param imageP chemin ou URL image
     * @param dateP date de création
     * @param auteur utilisateur auteur
     * @param utilisateurs interactions utilisateurs
     * @param commentaires commentaires associés
     */
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

    // ===== Getters et Setters =====

    /**
     * Retourne l’identifiant du post.
     *
     * @return idP
     */
    public Long getIdP() {
        return idP;
    }

    /**
     * Définit l’identifiant du post.
     *
     * @param idP nouvel identifiant
     */
    public void setIdP(Long idP) {
        this.idP = idP;
    }

    /**
     * Retourne le contenu textuel du post.
     *
     * @return contenu du post
     */
    public String getContenuP() {
        return contenuP;
    }

    /**
     * Définit le contenu textuel du post.
     *
     * @param contenuP nouveau contenu
     */
    public void setContenuP(String contenuP) {
        this.contenuP = contenuP;
    }

    /**
     * Retourne le chemin ou l’URL de l’image associée.
     *
     * @return image
     */
    public String getImageP() {
        return imageP;
    }

    /**
     * Définit le chemin ou l’URL de l’image.
     *
     * @param imageP nouvelle image
     */
    public void setImageP(String imageP) {
        this.imageP = imageP;
    }

    /**
     * Retourne la date de création du post.
     *
     * @return date
     */
    public Date getDateP() {
        return dateP;
    }

    /**
     * Définit la date de création du post.
     *
     * @param dateP nouvelle date
     */
    public void setDateP(Date dateP) {
        this.dateP = dateP;
    }

    /**
     * Retourne l’auteur du post.
     *
     * @return utilisateur auteur
     */
    public Utilisateur getAuteur() {
        return auteur;
    }

    /**
     * Définit l’auteur du post.
     *
     * @param auteur nouvel auteur
     */
    public void setAuteur(Utilisateur auteur) {
        this.auteur = auteur;
    }

    /**
     * Retourne l’ensemble des interactions utilisateurs (likes, republis, etc.).
     *
     * @return ensemble des interactions
     */
    public Set<ActionPost> getUtilisateurs() {
        return utilisateurs;
    }

    /**
     * Définit les interactions utilisateurs.
     *
     * @param utilisateurs ensemble des interactions
     */
    public void setUtilisateurs(Set<ActionPost> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    /**
     * Retourne l’ensemble des commentaires associés à ce post.
     *
     * @return ensemble des commentaires
     */
    public Set<Commentaire> getCommentaires() {
        return commentaires;
    }

    /**
     * Définit les commentaires associés à ce post.
     *
     * @param commentaires ensemble des commentaires
     */
    public void setCommentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public int getNombreLikes() {
        return nombreLikes;
    }

    public void setNombreLikes(int nombreLikes) {
        this.nombreLikes = nombreLikes;
    }

    

}