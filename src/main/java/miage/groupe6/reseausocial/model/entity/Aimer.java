package miage.groupe6.reseausocial.model.entity;

import jakarta.persistence.*;
import java.util.Date;

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

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLike;

    public Aimer() {}

    public Aimer(Utilisateur utilisateur, Post post, Date dateLike) {
        this.id = new AimerId(utilisateur.getIdU(), post.getIdP());
        this.utilisateur = utilisateur;
        this.post = post;
        this.dateLike = dateLike;
    }

    public AimerId getId() {
        return id;
    }

    public void setId(AimerId id) {
        this.id = id;
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

    public void setPublication(Post post) {
        this.post = post;
    }

    public Date getDateLike() {
        return dateLike;
    }

    public void setDateLike(Date dateLike) {
        this.dateLike = dateLike;
    }
}
