package miage.groupe6.reseausocial.model.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

/**
 * Représente un événement créé par un utilisateur.
 * Un événement contient un titre, un lieu, une description,
 * une date de début et de fin, ainsi qu'un utilisateur créateur.
 */
@Entity
@Table(name = "evenement")
public class Evenement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idE;

    private String titre;
    private String lieu;

    @Column(length = 500)
    private String descriptionE;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebut;

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

    @OneToMany(mappedBy = "idGroupe", cascade = CascadeType.ALL)
    private Set<Groupe> groupes = new HashSet<>();
    



    
    /**
     * Constructeur par défaut.
     */
    public Evenement() {}

    public Evenement(Long idE, String titre, String lieu, String descriptionE, Date dateDebut, Date dateFin,
            Utilisateur utilisateur, Set<ActionEvenement> actionEvenement, Set<Groupe> groupes) {
        this.idE = idE;
        this.titre = titre;
        this.lieu = lieu;
        this.descriptionE = descriptionE;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.utilisateur = utilisateur;
        this.actionEvenement = actionEvenement;
        this.groupes = groupes;
    }



    

    public Long getIdE() {
        return idE;
    }

    public void setIdE(Long idE) {
        this.idE = idE;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescriptionE() {
        return descriptionE;
    }

    public void setDescriptionE(String descriptionE) {
        this.descriptionE = descriptionE;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Set<ActionEvenement> getActionEvenement() {
        return actionEvenement;
    }

    public void setActionEvenement(Set<ActionEvenement> actionEvenement) {
        this.actionEvenement = actionEvenement;
    }

    public Set<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(Set<Groupe> groupes) {
        this.groupes = groupes;
    }


}