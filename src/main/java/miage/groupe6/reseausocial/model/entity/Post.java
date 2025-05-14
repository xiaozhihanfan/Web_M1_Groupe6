package miage.groupe6.reseausocial.model.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Représente une post dans le réseau social.
 * Chaque post contient un contenu textuel, une image optionnelle,
 * une date de post et un auteur (utilisateur).
 */
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idP;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenuP;

    private String imageP;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateP;


    // === Relations ===
    @ManyToOne
    @JoinColumn(name = "idU")
    private Utilisateur auteur;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Aimer> utilisateurs = new HashSet<>();



    // ==== Constructors ====

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Post() {}

    /**
     * Constructeur permettant de créer un post avec ses principales informations.
     *
     * @param contenuP le contenu textuel de la post
     * @param imageP le chemin ou l'URL de l'image liée à la post
     * @param dateP la date et l'heure de post
     * @param auteur l'utilisateur auteur de la post
     */
    public Post(String contenuP, String imageP, Date dateP, Utilisateur auteur) {
        this.contenuP = contenuP;
        this.imageP = imageP;
        this.dateP = dateP;
        this.auteur = auteur;
    }



    // === Getters et Setters ===
    /**
     * @return Long return the idP (identifiant de la post)
     */
    public Long getIdP() {
        return idP;
    }

    /**
     * @param idP the idP to set (identifiant de la post)
     */
    public void setIdP(Long idP) {
        this.idP = idP;
    }

    /**
     * @return String return the contenuP (contenu textuel de la post)
     */
    public String getContenuP() {
        return contenuP;
    }

    /**
     * @param contenuP the contenuP to set (contenu textuel de la post)
     */
    public void setContenuP(String contenuP) {
        this.contenuP = contenuP;
    }

    /**
     * @return String return the imageP (chemin ou URL de l'image liée à la post)
     */
    public String getImageP() {
        return imageP;
    }

    /**
     * @param imageP the imageP to set (chemin ou URL de l'image liée à la post)
     */
    public void setImageP(String imageP) {
        this.imageP = imageP;
    }

    /**
     * @return Date return the dateP (date et heure de post)
     */
    public Date getDateP() {
        return dateP;
    }

    /**
     * @param dateP the dateP to set (date et heure de post)
     */
    public void setDateP(Date dateP) {
        this.dateP = dateP;
    }

    /**
     * @return Utilisateur return the utilisateur (auteur de la post)
     */
    public Utilisateur getUtilisateur() {
        return auteur;
    }

    /**
     * @param utilisateur the utilisateur to set (auteur de la post)
     */
    public void setUtilisateur(Utilisateur auteur) {
        this.auteur = auteur;
    }

}