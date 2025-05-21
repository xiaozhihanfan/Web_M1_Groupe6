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
 * Représente un événement créé par un utilisateur.
 * Un événement contient un titre, un lieu, une description,
 * une date de début et de fin, ainsi qu'un utilisateur créateur.
 */
@Entity
@Table(name = "evenement")
public class Evenement {
    
    /**
     * Identifiant unique de l'événement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idE;

    /**
     * Titre de l'événement.
     * Lieu où se déroule l'événement.
     */
    private String titre;
    private String lieu;
    private String avatarE;

    /**
     * Description détaillée de l'événement.
     */
    @Column(length = 500)
    private String descriptionE;

    /**
     * Date et heure de début de l'événement.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebut;

    /**
     * Date et heure de fin de l'événement.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFin;

    /**
     * Utilisateur ayant créé l'événement.
     */
    @ManyToOne
    @JoinColumn(name = "idU")
    private Utilisateur utilisateur;    // Créateur de l'événement

    /**
     * Ensemble des actionEvenement des utilisateurs à cet événement.
     */
    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL)
    private Set<ActionEvenement> actionEvenement = new HashSet<>();

    /**
     * Groupes associés à cet événement.
     */
    @OneToMany(mappedBy = "idGroupe", cascade = CascadeType.ALL)
    private Set<Groupe> groupes = new HashSet<>();
    



    
    /**
     * Constructeur par défaut.
     */
    public Evenement() {}

    /**
     * Constructeur complet.
     *
     * @param idE identifiant
     * @param titre titre
     * @param lieu lieu
     * @param descriptionE description
     * @param dateDebut date de début
     * @param dateFin date de fin
     * @param utilisateur créateur
     * @param actionEvenement participations
     * @param groupes groupes liés
     */
    public Evenement(Long idE, String titre, String lieu, String descriptionE, String avatarE, Date dateDebut, Date dateFin,
            Utilisateur utilisateur, Set<ActionEvenement> actionEvenement, Set<Groupe> groupes) {
        this.idE = idE;
        this.titre = titre;
        this.lieu = lieu;
        this.descriptionE = descriptionE;
        this.avatarE = avatarE;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.utilisateur = utilisateur;
        this.actionEvenement = actionEvenement;
        this.groupes = groupes;
    }

    /**
     * Retourne l’identifiant de l’événement.
     *
     * @return identifiant
     */
    public Long getIdE() {
        return idE;
    }

    /**
     * Définit l’identifiant de l’événement.
     *
     * @param idE nouvel identifiant
     */
    public void setIdE(Long idE) {
        this.idE = idE;
    }

    /**
     * Retourne le titre de l’événement.
     *
     * @return titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Définit le titre de l’événement.
     *
     * @param titre nouveau titre
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Retourne le lieu de l’événement.
     *
     * @return lieu
     */
    public String getLieu() {
        return lieu;
    }

    /**
     * Définit le lieu de l’événement.
     *
     * @param lieu nouveau lieu
     */
    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    /**
     * Retourne la description de l’événement.
     *
     * @return description
     */
    public String getDescriptionE() {
        return descriptionE;
    }

    /**
     * Définit la description de l’événement.
     *
     * @param descriptionE nouvelle description
     */
    public void setDescriptionE(String descriptionE) {
        this.descriptionE = descriptionE;
    }

    /**
     * Retourne la date de début de l’événement.
     *
     * @return date de début
     */
    public Date getDateDebut() {
        return dateDebut;
    }

    /**
     * Définit la date de début de l’événement.
     *
     * @param dateDebut nouvelle date de début
     */
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * Retourne la date de fin de l’événement.
     *
     * @return date de fin
     */
    public Date getDateFin() {
        return dateFin;
    }

    /**
     * Définit la date de fin de l’événement.
     *
     * @param dateFin nouvelle date de fin
     */
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * Retourne l’utilisateur ayant créé l’événement.
     *
     * @return utilisateur créateur
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * Définit l’utilisateur créateur de l’événement.
     *
     * @param utilisateur nouvel utilisateur
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * Retourne les actions des utilisateurs sur cet événement.
     *
     * @return ensemble des actions
     */
    public Set<ActionEvenement> getActionEvenement() {
        return actionEvenement;
    }

    /**
     * Définit les actions des utilisateurs pour cet événement.
     *
     * @param actionEvenement nouvel ensemble d’actions
     */
    public void setActionEvenement(Set<ActionEvenement> actionEvenement) {
        this.actionEvenement = actionEvenement;
    }

    /**
     * Retourne les groupes associés à cet événement.
     *
     * @return ensemble des groupes
     */
    public Set<Groupe> getGroupes() {
        return groupes;
    }

    /**
     * Définit les groupes associés à cet événement.
     *
     * @param groupes nouvel ensemble de groupes
     */
    public void setGroupes(Set<Groupe> groupes) {
        this.groupes = groupes;
    }

    public String getAvatarE() {
        if (this.avatarE == null || this.avatarE.isEmpty()) {
            return "/assets/images/avatar/placeholder.jpg"; 
        }
        return avatarE;
    }

    public void setAvatarE(String avatarE) {
        this.avatarE = avatarE;
    }

}