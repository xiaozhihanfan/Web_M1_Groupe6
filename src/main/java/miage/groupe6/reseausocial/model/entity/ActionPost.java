package miage.groupe6.reseausocial.model.entity;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Entité représentant une action d’un utilisateur sur une publication {@link Post}.
 * <p>
 * Cette action peut être un "like", un "unlike", ou une "republier".
 * L'entité utilise une clé composite {@link ActionPostId} combinant l'utilisateur et la publication.
 * Elle enregistre également la date de l’action et le type d’action via l’énumération {@link StatutActionPost}.
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
     * Constructeur permettant d’instancier une action utilisateur sur une publication.
     *
     * @param utilisateur l’utilisateur qui a réalisé l’action
     * @param post la publication concernée
     * @param dateActionPost la date de l’action
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
     * @return Utilisateur retourne l'utilisateur qui a fait une action le post
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * @param utilisateur l'utilisateur à associer à cette action
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * @return Post retourne le post qui a été fait cette action
     */
    public Post getPost() {
        return post;
    }

    /**
     * @param post le post à associer à cette action
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * @return Date retourne la date à laquelle le like/unlike/republier a été effectué
     */
    public Date getDateActionPost() {
        return dateActionPost;
    }

    /**
     * @param dateActionPost définit la date de l’action (like/unlike/republier)
     */
    public void setDateActionPost(Date dateActionPost) {
        this.dateActionPost = dateActionPost;
    }



    /**
     * @return le type d’action effectuée (LIKE, UNLIKE, REPUBLIER)
     */
    public StatutActionPost getStatut() {
        return statut;
    }

    /**
     * @param statut définit le type d’action effectuée
     */
    public void setStatut(StatutActionPost statut) {
        this.statut = statut;
    }
}
