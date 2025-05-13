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

    // === Getters et Setters ===

    /**
     * @return AimerId retourne l'identifiant composite (utilisateur + publication)
     */
    public AimerId getId() {
        return id;
    }

    /**
     * @param id identifiant composite à définir (utilisateur + publication)
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
    public void setPublication(Post post) {
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
