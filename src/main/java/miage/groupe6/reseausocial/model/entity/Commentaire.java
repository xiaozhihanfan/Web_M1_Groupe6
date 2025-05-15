package miage.groupe6.reseausocial.model.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant un commentaire publié par un utilisateur sur un post.
 * <p>
 * Un commentaire peut être :
 * <ul>
 *   <li>lié à un post</li>
 *   <li>écrit par un utilisateur</li>
 *   <li>optionnellement une réponse à un autre commentaire</li>
 * </ul>
 * Cette entité supporte les commentaires hiérarchiques grâce à une auto-référence.
 */
@Entity
@Table(name = "commentaire")
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idC;

    @Column(length = 50, nullable = false)
    private String contenueC;

    @Temporal(TemporalType.TIMESTAMP)
    private Date tempsC;


    // ===== Relations =====

    // Auteur du commentaire
    @ManyToOne
    @JoinColumn(name = "idU")
    private Utilisateur utilisateur;

    // Publication ou post auquel ce commentaire appartient
    @ManyToOne
    @JoinColumn(name = "idP")
    private Post post;

    // Répond à un autre commentaire
    @ManyToOne
    @JoinColumn(name = "idC_Repondre")
    private Commentaire commentaireParent;

    // Set des réponses à ce commentaire
    @OneToMany(mappedBy = "commentaireParent", cascade = CascadeType.ALL)
    private Set<Commentaire> reponses = new HashSet<>();






    // ===== Constructors =====

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Commentaire() {}

    public Commentaire(Long idC, String contenueC, Date tempsC, Utilisateur utilisateur, Post post,
            Commentaire commentaireParent, Set<Commentaire> reponses) {
        this.idC = idC;
        this.contenueC = contenueC;
        this.tempsC = tempsC;
        this.utilisateur = utilisateur;
        this.post = post;
        this.commentaireParent = commentaireParent;
        this.reponses = reponses;
    }




    public Long getIdC() {
        return idC;
    }

    public void setIdC(Long idC) {
        this.idC = idC;
    }

    public String getContenueC() {
        return contenueC;
    }

    public void setContenueC(String contenueC) {
        this.contenueC = contenueC;
    }

    public Date getTempsC() {
        return tempsC;
    }

    public void setTempsC(Date tempsC) {
        this.tempsC = tempsC;
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

    public Commentaire getCommentaireParent() {
        return commentaireParent;
    }

    public void setCommentaireParent(Commentaire commentaireParent) {
        this.commentaireParent = commentaireParent;
    }

    public Set<Commentaire> getReponses() {
        return reponses;
    }

    public void setReponses(Set<Commentaire> reponses) {
        this.reponses = reponses;
    }


}
