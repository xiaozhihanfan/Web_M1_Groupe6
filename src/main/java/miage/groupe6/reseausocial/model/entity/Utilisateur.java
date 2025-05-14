package miage.groupe6.reseausocial.model.entity;

import jakarta.persistence.*;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.Date;
import java.util.HashSet;

import java.util.Set;

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


    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private Set<Aimer> postAime = new HashSet<>();

    //L'utilisateur demande à devenir ami
    @OneToMany(mappedBy = "idUtilisateurDemande", cascade = CascadeType.ALL)
    private Set<RelationAmis> amisDemandes = new HashSet<>();

    //L'utilisateur reçoit une demande d'ami
    @OneToMany(mappedBy = "idUtilisateurRecu", cascade = CascadeType.ALL)
    private Set<RelationAmis> amisRecus = new HashSet<>();

    // @ManyToMany
    // @JoinTable(name = "interesser", joinColumns = @JoinColumn(name = "idU"), inverseJoinColumns = @JoinColumn(name = "idE"))
    // private Set<Evenement> evenementsInteresse = new HashSet<>();

    // @ManyToMany
    // @JoinTable(name = "inscrire", joinColumns = @JoinColumn(name = "idU"), inverseJoinColumns = @JoinColumn(name = "idE"))
    // private Set<Evenement> evenementsInscrit = new HashSet<>();


    

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
     * @return Set<Post> return the postAime
     */
    public Set<Aimer> getPostAime() {
        return postAime;
    }

    /**
     * @param postAime the postAime to set
     */
    public void setPostAime(Set<Aimer> postAime) {
        this.postAime = postAime;
    }

    /**
     * @return Set<EtreAmis> return the amisDemandes
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
     * @return Set<EtreAmis> return the amisRecus
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

    // /**
    //  * @return Set<Evenement> return the evenementsInteresse
    //  */
    // public Set<Evenement> getEvenementsInteresse() {
    //     return evenementsInteresse;
    // }

    // /**
    //  * @param evenementsInteresse the evenementsInteresse to set
    //  */
    // public void setEvenementsInteresse(Set<Evenement> evenementsInteresse) {
    //     this.evenementsInteresse = evenementsInteresse;
    // }

    // /**
    //  * @return Set<Evenement> return the evenementsInscrit
    //  */
    // public Set<Evenement> getEvenementsInscrit() {
    //     return evenementsInscrit;
    // }

    // /**
    //  * @param evenementsInscrit the evenementsInscrit to set
    //  */
    // public void setEvenementsInscrit(Set<Evenement> evenementsInscrit) {
    //     this.evenementsInscrit = evenementsInscrit;
    // }

}