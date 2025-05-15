package miage.groupe6.reseausocial.model.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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

    /**
     * Identifiant unique du commentaire.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idC;

    /**
     * Contenu du commentaire.
     */
    @Column(length = 50, nullable = false)
    private String contenueC;

    /**
     * Date et heure de création du commentaire.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date tempsC;


    // ===== Relations =====

    /**
     * Utilisateur ayant écrit le commentaire.
     */
    @ManyToOne
    @JoinColumn(name = "idU")
    private Utilisateur utilisateur;

    /**
     * Publication concernée par le commentaire.
     */
    @ManyToOne
    @JoinColumn(name = "idP")
    private Post post;

    /**
     * Commentaire parent auquel ce commentaire répond (peut être null si c'est un commentaire principal).
     */
    @ManyToOne
    @JoinColumn(name = "idC_Repondre")
    private Commentaire commentaireParent;

    /**
     * Ensemble des réponses à ce commentaire.
     */
    @OneToMany(mappedBy = "commentaireParent", cascade = CascadeType.ALL)
    private Set<Commentaire> reponses = new HashSet<>();






    // ===== Constructors =====

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Commentaire() {}

    /**
     * Constructeur complet.
     *
     * @param idC               identifiant du commentaire
     * @param contenueC         contenu textuel du commentaire
     * @param tempsC            date et heure de publication
     * @param utilisateur       auteur du commentaire
     * @param post              publication liée
     * @param commentaireParent commentaire auquel celui-ci répond (peut être null)
     * @param reponses          ensemble des réponses associées
     */
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




    /**
     * Retourne l’identifiant du commentaire.
     *
     * @return idC
     */
    public Long getIdC() {
        return idC;
    }

    /**
     * Définit l’identifiant du commentaire.
     *
     * @param idC nouvelle valeur
     */
    public void setIdC(Long idC) {
        this.idC = idC;
    }

    /**
     * Retourne le contenu du commentaire.
     *
     * @return texte du commentaire
     */
    public String getContenueC() {
        return contenueC;
    }

    /**
     * Définit le contenu du commentaire.
     *
     * @param contenueC nouveau texte
     */
    public void setContenueC(String contenueC) {
        this.contenueC = contenueC;
    }

    /**
     * Retourne la date de publication.
     *
     * @return date et heure
     */
    public Date getTempsC() {
        return tempsC;
    }

    /**
     * Définit la date de publication.
     *
     * @param tempsC nouvelle date
     */
    public void setTempsC(Date tempsC) {
        this.tempsC = tempsC;
    }

    /**
     * Retourne l’auteur du commentaire.
     *
     * @return utilisateur
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * Définit l’auteur du commentaire.
     *
     * @param utilisateur nouvel utilisateur
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * Retourne la publication liée au commentaire.
     *
     * @return publication
     */
    public Post getPost() {
        return post;
    }

    /**
     * Définit la publication liée au commentaire.
     *
     * @param post nouvelle publication
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * Retourne le commentaire parent (si réponse).
     *
     * @return commentaire parent ou {@code null}
     */
    public Commentaire getCommentaireParent() {
        return commentaireParent;
    }

    /**
     * Définit le commentaire parent.
     *
     * @param commentaireParent nouveau commentaire parent
     */
    public void setCommentaireParent(Commentaire commentaireParent) {
        this.commentaireParent = commentaireParent;
    }

    /**
     * Retourne les réponses à ce commentaire.
     *
     * @return ensemble des réponses
     */
    public Set<Commentaire> getReponses() {
        return reponses;
    }

    /**
     * Définit les réponses à ce commentaire.
     *
     * @param reponses nouvel ensemble de réponses
     */
    public void setReponses(Set<Commentaire> reponses) {
        this.reponses = reponses;
    }


}
