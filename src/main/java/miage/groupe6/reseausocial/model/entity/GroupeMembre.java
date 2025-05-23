package miage.groupe6.reseausocial.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;


/**
 * Représente l'association entre un {@link Utilisateur} et un {@link Groupe} 
 * dans le cadre d'une relation de type "membre de groupe" dans le réseau social.
 * 
 * Cette entité utilise une clé composite {@link GroupeMembreId} qui contient 
 * les identifiants du groupe et de l'utilisateur.
 * 
 * Elle stocke également des informations contextuelles comme le rôle du membre
 * et la date d'adhésion.
 * 
 */

@Entity
public class GroupeMembre {

    /**
     * Clé composite représentant l'association groupe-utilisateur.
     */
    @EmbeddedId
    private GroupeMembreId id = new GroupeMembreId();

    /**
     * Rôle du membre dans le groupe (ex: ADMIN, MEMBRE).
     */
    @Enumerated(EnumType.STRING)
    private MembreRole role;

    /**
     * Date d’adhésion de l’utilisateur au groupe.
     */
    private LocalDateTime dateAdhesion;

    /** 
     * Groupe auquel appartient le membre. 
     */
    @ManyToOne
    @MapsId("idGroupe")
    @JoinColumn(name = "id_groupe")
    private Groupe groupe;

    /** 
     * Utilisateur membre du groupe. 
     */
    @ManyToOne
    @MapsId("idUtilisateur")
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    // ===== Constructeurs =====

    /**
     * Constructeur par défaut requis par JPA.
     */
    public GroupeMembre() {}

    /**
     * Constructeur complet.
     *
     * @param role rôle du membre
     * @param dateAdhesion date d’adhésion
     * @param groupe groupe concerné
     * @param utilisateur utilisateur membre
     */
    public GroupeMembre(MembreRole role, LocalDateTime dateAdhesion, Groupe groupe,
            Utilisateur utilisateur) {
        this.id = new GroupeMembreId(groupe.getIdGroupe(), utilisateur.getIdU());
        this.role = role;
        this.dateAdhesion = dateAdhesion;
        this.groupe = groupe;
        this.utilisateur = utilisateur;
    }

    // ===== Getters et Setters =====

    /**
     * Retourne la clé composite.
     *
     * @return {@link GroupeMembreId}
     */
    public GroupeMembreId getId() {
        return id;
    }

    /**
     * Définit la clé composite.
     *
     * @param id nouvelle clé {@link GroupeMembreId}
     */
    public void setId(GroupeMembreId id) {
        this.id = id;
    }

    /**
     * Retourne le rôle du membre.
     *
     * @return rôle
     */
    public MembreRole getRole() {
        return role;
    }

    /**
     * Définit le rôle du membre.
     *
     * @param role nouveau rôle
     */
    public void setRole(MembreRole role) {
        this.role = role;
    }

    /**
     * Retourne la date d’adhésion du membre.
     *
     * @return date d’adhésion
     */
    public LocalDateTime getDateAdhesion() {
        return dateAdhesion;
    }

    /**
     * Définit la date d’adhésion du membre.
     *
     * @param dateAdhesion nouvelle date d’adhésion
     */
    public void setDateAdhesion(LocalDateTime dateAdhesion) {
        this.dateAdhesion = dateAdhesion;
    }

    /**
     * Retourne le groupe auquel appartient ce membre.
     *
     * @return groupe
     */
    public Groupe getGroupe() {
        return groupe;
    }

    /**
     * Définit le groupe du membre.
     *
     * @param groupe nouveau groupe
     */
    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    /**
     * Retourne l’utilisateur membre du groupe.
     *
     * @return utilisateur
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * Définit l’utilisateur membre du groupe.
     *
     * @param utilisateur nouvel utilisateur
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

}
