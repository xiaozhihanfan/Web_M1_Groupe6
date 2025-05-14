package miage.groupe6.reseausocial.model.entity;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Entité représentant une action "J'aime" (like) d'un utilisateur sur un post.
 * Cette entité utilise une clé composite {@link AimerId} pour représenter la relation
 * entre l'utilisateur et la publication aimée, et stocke également la date du like.
 */
@Entity
@Table(name = "aimer")
public class Aimer {

    @EmbeddedId
    private AimerId id;

    @ManyToOne
    @MapsId("idU")
    @JoinColumn(name = "idU")
    private Utilisateur utilisateur;

    @ManyToOne
    @MapsId("idP")
    @JoinColumn(name = "idP")
    private Post post;

    // 记录点赞时间，可能用得上可能用不上，看进度
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLike;



    // === Constructors ===

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Aimer() {}

    /**
     * Constructeur permettant d'initialiser un like entre un utilisateur et un post.
     *
     * @param utilisateur l'utilisateur qui a aimé
     * @param post le post concerné
     * @param dateLike la date à laquelle l'action "j'aime" a été effectuée
     */
    public Aimer(Utilisateur utilisateur, Post post, Date dateLike) {
        this.id = new AimerId(utilisateur.getIdU(), post.getIdP());
        this.utilisateur = utilisateur;
        this.post = post;
        this.dateLike = dateLike;
    }



    // === Getters et Setters ===

    /**
     * @return AimerId retourne l'identifiant composite (utilisateur + post)
     */
    public AimerId getId() {
        return id;
    }

    /**
     * @param id identifiant composite à définir (utilisateur + post)
     */
    public void setId(AimerId id) {
        this.id = id;
    }

    /**
     * @return Utilisateur retourne l'utilisateur qui a aimé le post
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * @param utilisateur l'utilisateur à associer à ce like
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * @return Post retourne le post qui a été aimé
     */
    public Post getPost() {
        return post;
    }

    /**
     * @param post le post à associer à ce like
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * @return Date retourne la date à laquelle le like a été effectué
     */
    public Date getDateLike() {
        return dateLike;
    }

    /**
     * @param dateLike la date à laquelle l'utilisateur a aimé le post
     */
    public void setDateLike(Date dateLike) {
        this.dateLike = dateLike;
    }

}
