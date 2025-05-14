package miage.groupe6.reseausocial.model.entity;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Entité représentant une action "J'aime" (like) d'un utilisateur sur un post.
 * Cette entité utilise une clé composite {@link ActionPostId} pour représenter la relation
 * entre l'utilisateur et la publication aimée, et stocke également la date du like.
 */
@Entity
@Table(name = "action_post")
public class ActionPost {

    @EmbeddedId
    private ActionPostId id;

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
    private Date dateActionPost;

    /**
     * Statut actuel de la post (like, unlike, republier).
     */
    @Enumerated(EnumType.STRING)
    private StatutActionPost statut;


    // === Constructors ===

    /**
     * Constructeur par défaut requis par JPA.
     */
    public ActionPost() {}

    /**
     * Constructeur permettant d'initialiser un like entre un utilisateur et un post.
     *
     * @param utilisateur l'utilisateur qui a aimé
     * @param post le post concerné
     * @param dateActionPost la date à laquelle l'action "j'aime" a été effectuée
     */
    public ActionPost(Utilisateur utilisateur, Post post, Date dateActionPost) {
        this.id = new ActionPostId(utilisateur.getIdU(), post.getIdP());
        this.utilisateur = utilisateur;
        this.post = post;
        this.dateActionPost = dateActionPost;
    }



    // === Getters et Setters ===

    /**
     * @return ActionPostId retourne l'identifiant composite (utilisateur + post)
     */
    public ActionPostId getId() {
        return id;
    }

    /**
     * @param id identifiant composite à définir (utilisateur + post)
     */
    public void setId(ActionPostId id) {
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
    public Date getdateActionPost() {
        return dateActionPost;
    }

    /**
     * @param dateActionPost la date à laquelle l'utilisateur a aimé le post
     */
    public void setdateActionPost(Date dateActionPost) {
        this.dateActionPost = dateActionPost;
    }

}
