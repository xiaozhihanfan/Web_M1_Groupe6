package miage.groupe6.reseausocial.model.entity;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "republier")
public class Republier {

    @EmbeddedId
    private RepublierId id;

    @ManyToOne
    @MapsId("idU")
    @JoinColumn(name = "idU")
    private Utilisateur utilisateur;

    @ManyToOne
    @MapsId("idP")
    @JoinColumn(name = "idP")
    private Post post;

    // 记录转发时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRepublier;



    // ==== Constructors ====
    public Republier() {}

    public Republier(RepublierId id, Utilisateur utilisateur, Post post, Date dateRepublier) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.post = post;
        this.dateRepublier = dateRepublier;
    }



    // ==== Getter et Setter
        public RepublierId getId() {
        return id;
    }

    public void setId(RepublierId id) {
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

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getDateRepublier() {
        return dateRepublier;
    }

    public void setDateRepublier(Date dateRepublier) {
        this.dateRepublier = dateRepublier;
    }

}
