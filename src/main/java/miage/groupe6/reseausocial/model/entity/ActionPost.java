package miage.groupe6.reseausocial.model.entity;

import java.util.Date;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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

    /**
     * Clé composite combinant l’identifiant de l’utilisateur et de la publication.
     */
    @EmbeddedId
    private ActionPostId id;

    /**
     * Date et heure de l’action sur la publication.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateActionPost;

    /**
     * Type d’action effectuée (LIKE, UNLIKE, REPUBLIER).
     */
    @Enumerated(EnumType.STRING)
    private StatutActionPost statut;

    /**
     * Utilisateur ayant effectué l’action.
     */
    @ManyToOne
    @MapsId("idU")
    @JoinColumn(name = "idU")
    private Utilisateur utilisateur;

    /**
     * Publication concernée par l’action.
     */
    @ManyToOne
    @MapsId("idP")
    @JoinColumn(name = "idP")
    private Post post;

    /**
     * Constructeur par défaut requis par JPA.
     */
    public ActionPost() {}

    /**
     * Constructeur principal.
     *
     * @param dateActionPost date de l’action
     * @param statut         type d’action
     * @param utilisateur    utilisateur ayant réalisé l’action
     * @param post           publication concernée
     */
    public ActionPost(Date dateActionPost, StatutActionPost statut, Utilisateur utilisateur,
            Post post) {
        this.id = new ActionPostId(utilisateur.getIdU(), post.getIdP());
        this.dateActionPost = dateActionPost;
        this.statut = statut;
        this.utilisateur = utilisateur;
        this.post = post;
    }

     /**
     * Retourne la clé composite.
     *
     * @return id de l’action
     */
    public ActionPostId getId() {
        return id;
    }

    /**
     * Définit la clé composite.
     *
     * @param id nouvelle clé
     */
    public void setId(ActionPostId id) {
        this.id = id;
    }

    /**
     * Retourne la date de l’action.
     *
     * @return date de l’action
     */
    public Date getDateActionPost() {
        return dateActionPost;
    }

    /**
     * Définit la date de l’action.
     *
     * @param dateActionPost nouvelle date
     */
    public void setDateActionPost(Date dateActionPost) {
        this.dateActionPost = dateActionPost;
    }

    /**
     * Retourne le type d’action.
     *
     * @return type d’action
     */
    public StatutActionPost getStatut() {
        return statut;
    }

    /**
     * Définit le type d’action.
     *
     * @param statut nouveau statut
     */
    public void setStatut(StatutActionPost statut) {
        this.statut = statut;
    }

    /**
     * Retourne l’utilisateur ayant effectué l’action.
     *
     * @return utilisateur
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * Définit l’utilisateur ayant effectué l’action.
     *
     * @param utilisateur nouvel utilisateur
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * Retourne la publication concernée par l’action.
     *
     * @return publication
     */
    public Post getPost() {
        return post;
    }

    /**
     * Définit la publication concernée par l’action.
     *
     * @param post nouvelle publication
     */
    public void setPost(Post post) {
        this.post = post;
    }

}
