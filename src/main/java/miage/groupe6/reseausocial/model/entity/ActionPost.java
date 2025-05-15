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

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateActionPost;

    @Enumerated(EnumType.STRING)
    private StatutActionPost statut;



    @ManyToOne
    @MapsId("idU")
    @JoinColumn(name = "idU")
    private Utilisateur utilisateur;

    @ManyToOne
    @MapsId("idP")
    @JoinColumn(name = "idP")
    private Post post;


    

    public ActionPost() {}

    public ActionPost(Date dateActionPost, StatutActionPost statut, Utilisateur utilisateur,
            Post post) {
        this.id = new ActionPostId(utilisateur.getIdU(), post.getIdP());
        this.dateActionPost = dateActionPost;
        this.statut = statut;
        this.utilisateur = utilisateur;
        this.post = post;
    }




    public ActionPostId getId() {
        return id;
    }

    public void setId(ActionPostId id) {
        this.id = id;
    }

    public Date getDateActionPost() {
        return dateActionPost;
    }

    public void setDateActionPost(Date dateActionPost) {
        this.dateActionPost = dateActionPost;
    }

    public StatutActionPost getStatut() {
        return statut;
    }

    public void setStatut(StatutActionPost statut) {
        this.statut = statut;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
