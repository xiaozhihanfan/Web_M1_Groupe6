package miage.groupe6.reseausocial.model.entity;

import jakarta.persistence.*;
import java.util.Date;

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




    // ===== Constructors =====

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Commentaire() {}

    /**
     * Constructeur permettant d'initialiser un commentaire avec ses principales informations.
     *
     * @param contenueC le texte du commentaire
     * @param tempsC la date et l'heure de publication
     * @param utilisateur l'auteur du commentaire
     * @param post le post auquel ce commentaire est lié
     * @param parent le commentaire auquel celui-ci répond (peut être {@code null})
     */
    public Commentaire(String contenueC, Date tempsC, Utilisateur utilisateur, Post post, Commentaire parent) {
        this.contenueC = contenueC;
        this.tempsC = tempsC;
        this.utilisateur = utilisateur;
        this.post = post;
        this.commentaireParent = parent;
    }



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

    // // 可写可不写，用于双向导航，可以获取一条评论下所有回复的评论，之后可能会用到
    // // Liste des réponses à ce commentaire
    // @OneToMany(mappedBy = "commentaireParent", cascade = CascadeType.ALL)
    // private List<Commentaire> reponses;



    // ===== Getters & Setters =====

    /**
     * @return Long retourne l'identifiant unique du commentaire
     */
    public Long getIdC() {
        return idC;
    }

    /**
     * @param idC identifiant du commentaire à définir
     */
    public void setIdC(Long idC) {
        this.idC = idC;
    }

    /**
     * @return String retourne le contenu du commentaire (limité à 50 caractères)
     */
    public String getContenueC() {
        return contenueC;
    }

    /**
     * @param contenueC le texte du commentaire à définir
     */
    public void setContenueC(String contenueC) {
        this.contenueC = contenueC;
    }

    /**
     * @return Date retourne la date et l'heure où le commentaire a été posté
     */
    public Date getTempsC() {
        return tempsC;
    }

    /**
     * @param tempsC la date et l'heure à laquelle le commentaire a été écrit
     */
    public void setTempsC(Date tempsC) {
        this.tempsC = tempsC;
    }

    /**
     * @return Utilisateur retourne l'auteur du commentaire
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * @param utilisateur l'utilisateur (auteur) du commentaire
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * @return Post retourne le post auquel ce commentaire est associé
     */
    public Post getPost() {
        return post;
    }

    /**
     * @param post le post auquel rattacher ce commentaire
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * @return Commentaire retourne le commentaire auquel celui-ci répond, s'il y en a un
     */
    public Commentaire getCommentaireParent() {
        return commentaireParent;
    }

    /**
     * @param commentaireParent commentaire parent à définir (pour les réponses)
     */
    public void setCommentaireParent(Commentaire commentaireParent) {
        this.commentaireParent = commentaireParent;
    }

    // // 可写可不写，基于上面的那个方法要不要留
    // /**
    //  * @return List<Commentaire> retourne la liste des réponses associées à ce commentaire
    //  */
    // public List<Commentaire> getReponses() {
    //     return reponses;
    // }

    // // 可写可不写
    // /**
    //  * @param reponses la liste des commentaires enfants à associer
    //  */
    // public void setReponses(List<Commentaire> reponses) {
    //     this.reponses = reponses;
    // }

}
