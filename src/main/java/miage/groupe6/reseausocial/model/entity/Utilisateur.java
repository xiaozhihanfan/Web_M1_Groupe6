package miage.groupe6.reseausocial.model.entity;



import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.HashSet;

import java.util.Set;
/**
 * Représente un utilisateur du réseau social.
 * 
 * Un utilisateur possède des informations personnelles telles que son nom, prénom, email, mot de passe, description, avatar, 
 * et la date de son inscription. Il peut aimer des publications, envoyer et recevoir des demandes d’amis.
 * 
 * Auteur : Mengyi YANG
 */

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idU;

    private String nomU;
    private String prenomU;
    private String emailU;
    private String mpU;
    private String descriptionU;
    private String avatarU;
    private Date dateInscription;



    /** Ensemble des publications aimées par l'utilisateur. */
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private Set<ActionPost> actionPosts = new HashSet<>();


    /** Demandes d’amis envoyées par l'utilisateur. */
    @OneToMany(mappedBy = "idUtilisateurDemande", cascade = CascadeType.ALL)
    private Set<RelationAmis> amisDemandes = new HashSet<>();

    /** Demandes d’amis reçues par l'utilisateur. */
    @OneToMany(mappedBy = "idUtilisateurRecu", cascade = CascadeType.ALL)
    private Set<RelationAmis> amisRecus = new HashSet<>();

    /** Événements auxquels l'utilisateur s'est déclaré intéressé ou inscrit. */
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)    
    private Set<Evenement> actionEvenements = new HashSet<>();



    // ==== Getter and Setter

    /**
     * @return Long return the idU
     */
    public Long getIdU() {
        return idU;
    }

    /**
     * @param idU the idU to set
     */
    public void setIdU(Long idU) {
        this.idU = idU;
    }

    /**
     * @return String return the nomU
     */
    public String getNomU() {
        return nomU;
    }

    /**
     * @param nomU the nomU to set
     */
    public void setNomU(String nomU) {
        this.nomU = nomU;
    }

    /**
     * @return String return the prenomU
     */
    public String getPrenomU() {
        return prenomU;
    }

    /**
     * @param prenomU the prenomU to set
     */
    public void setPrenomU(String prenomU) {
        this.prenomU = prenomU;
    }

    /**
     * @return String return the emailU
     */
    public String getEmailU() {
        return emailU;
    }

    /**
     * @param emailU the emailU to set
     */
    public void setEmailU(String emailU) {
        this.emailU = emailU;
    }

    /**
     * @return String return the mpU
     */
    public String getMpU() {
        return mpU;
    }

    /**
     * @param mpU the mpU to set
     */
    public void setMpU(String mpU) {
        this.mpU = mpU;
    }

    /**
     * @return String return the descriptionU
     */
    public String getDescriptionU() {
        return descriptionU;
    }

    /**
     * @param descriptionU the descriptionU to set
     */
    public void setDescriptionU(String descriptionU) {
        this.descriptionU = descriptionU;
    }

    /**
     * @return String return the avatarU
     */
    public String getAvatarU() {
        return avatarU;
    }

    /**
     * @param avatarU the avatarU to set
     */
    public void setAvatarU(String avatarU) {
        this.avatarU = avatarU;
    }

    /**
     * @return Date return the dateInscription
     */
    public Date getDateInscription() {
        return dateInscription;
    }



    /**
     * @param dateInscription the dateInscription to set
     */
    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    /**
     * @return Set<Post> return the ActionPosts
     */
    public Set<ActionPost> getActionPosts() {
        return actionPosts;
    }



    /**
     * @param ActionPosts the ActionPosts to set
     */
    public void setActionPosts(Set<ActionPost> actionPosts) {
        this.actionPosts = actionPosts;
    }

    /**
     * @return Set<RelationAmis> return the amisDemandes
     */
    public Set<RelationAmis> getAmisDemandes() {
        return amisDemandes;
    }

    /**
     * @param amisDemandes the amisDemandes to set
     */
    public void setAmisDemandes(Set<RelationAmis> amisDemandes) {
        this.amisDemandes = amisDemandes;
    }

    /**
     * @return Set<RelationAmis> return the amisRecus
     */
    public Set<RelationAmis> getAmisRecus() {
        return amisRecus;
    }

    /**
     * @param amisRecus the amisRecus to set
     */
    public void setAmisRecus(Set<RelationAmis> amisRecus) {
        this.amisRecus = amisRecus;
    }



    /**
     * @return Set<Evenement> return the ActionEvenements
     */
    public Set<Evenement> getActionEvenements() {
        return actionEvenements;
    }

    /**
     * @param actionEvenements the actionEvenements to set
     */
    public void setActionEvenements(Set<Evenement> actionEvenements) {
        this.actionEvenements = actionEvenements;
    }

   
}

