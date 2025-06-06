package miage.groupe6.reseausocial.model.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Représente un groupe dans le réseau social.
 * 
 * Chaque groupe est créé par un utilisateur et peut contenir plusieurs membres.
 * Il contient des informations de base telles que le nom, la description et le créateur du groupe.
 * 
 */

@Entity
@Table(name = "groupe")
public class Groupe {

    /**
     * Identifiant unique du groupe.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGroupe;

    /**
     * Nom du groupe.
     * Description du groupe.
     * Avatar du groupe
     */
    private String nomGroupe;
    private String description;
    private String avatarG;


    /**
     * Utilisateur créateur du groupe.
     */
    @ManyToOne
    @JoinColumn(name = "idUtilisateurCreateur")
    private Utilisateur createur;

    /**
     * Événement associé à ce groupe (facultatif).
     */
    @ManyToOne
    @JoinColumn(name = "idGroupeEvenement")
    private Evenement evenement;
    
    /**
     * Membres du groupe.
     */
    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GroupeMembre> membres = new HashSet<>();

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Groupe() {}
    
    /**
     * Constructeur complet.
     *
     * @param idGroupe identifiant du groupe
     * @param nomGroupe nom du groupe
     * @param description description du groupe
     * @param createur utilisateur créateur
     * @param evenement événement associé
     * @param membres membres du groupe
     */
    public Groupe(Long idGroupe, String nomGroupe, String description, String avatarG, Utilisateur createur, Evenement evenement,
            Set<GroupeMembre> membres) {
        this.idGroupe = idGroupe;
        this.nomGroupe = nomGroupe;
        this.description = description;
        this.avatarG = avatarG;
        this.createur = createur;
        this.evenement = evenement;
        this.membres = membres;
    }

    /**
     * Retourne l’identifiant du groupe.
     *
     * @return idGroupe
     */
    public Long getIdGroupe() {
        return idGroupe;
    }

    /**
     * Définit l’identifiant du groupe.
     *
     * @param idGroupe nouvelle valeur
     */
    public void setIdGroupe(Long idGroupe) {
        this.idGroupe = idGroupe;
    }

    /**
     * Retourne le nom du groupe.
     *
     * @return nomGroupe
     */
    public String getNomGroupe() {
        return nomGroupe;
    }

    /**
     * Définit le nom du groupe.
     *
     * @param nomGroupe nouveau nom
     */
    public void setNomGroupe(String nomGroupe) {
        this.nomGroupe = nomGroupe;
    }

    /**
     * Retourne la description du groupe.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description du groupe.
     *
     * @param description nouvelle description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    

    /**
     * Retourne l’utilisateur créateur du groupe.
     *
     * @return utilisateur créateur
     */
    public Utilisateur getCreateur() {
        return createur;
    }

    /**
     * Définit l’utilisateur créateur du groupe.
     *
     * @param createur nouvel utilisateur
     */
    public void setCreateur(Utilisateur createur) {
        this.createur = createur;
    }

    /**
     * Retourne l’événement associé à ce groupe.
     *
     * @return événement ou {@code null} si aucun
     */
    public Evenement getEvenement() {
        return evenement;
    }

    /**
     * Définit l’événement associé à ce groupe.
     *
     * @param evenement nouvel événement
     */
    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    /**
     * Retourne les membres du groupe.
     *
     * @return ensemble des membres
     */
    public Set<GroupeMembre> getMembres() {
        return membres;
    }

    /**
     * Définit les membres du groupe.
     *
     * @param membres nouvel ensemble de membres
     */
    public void setMembres(Set<GroupeMembre> membres) {
        this.membres = membres;
    }
    
    /**
     * Retourne l'avatar du groupe.
     *
     * @return avatarG Chemin ou URL de l'avatar
     */
    public String getAvatarG() {
        return avatarG;
    }

    /**
     * Définit l'avatar du groupe.
     *
     * @param avatarG Nouvel avatar (chemin ou URL)
     */
    public void setAvatarG(String avatarG) {
        this.avatarG = avatarG;
    }

    /**
     * Retourne une représentation textuelle du groupe.
     *
     * @return Chaîne de caractères décrivant le groupe
     */
    @Override
    public String toString() {
        return "Groupe [idGroupe=" + idGroupe + ", nomGroupe=" + nomGroupe + ", description=" + description + "]";
    }

    
}
