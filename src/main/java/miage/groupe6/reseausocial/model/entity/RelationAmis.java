package miage.groupe6.reseausocial.model.entity;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Entité représentant une relation d'amitié entre deux utilisateurs.
 * Gère l'état de la demande (acceptée ou en attente) ainsi que la date de création.
 */
@Entity
public class RelationAmis {

    /**
     * Clé composite de la relation d'amitié (utilisateur demandeur + utilisateur cible).
     */
    @EmbeddedId
    private RelationAmisId id;

    /**
     * Utilisateur qui envoie la demande d'amitié.
     */
    @ManyToOne
    @MapsId("utilisateurDemandeurId")
    @JoinColumn(name = "utilisateur_demandeur_id")
    private Utilisateur utilisateurDemandeur;

    /**
     * Utilisateur qui reçoit la demande d'amitié.
     */
    @ManyToOne
    @MapsId("utilisateurCibleId")
    @JoinColumn(name = "utilisateur_cible_id")
    private Utilisateur utilisateurCible;

    /**
     * Date de création ou de traitement de la relation d'amitié.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRelationAmis;

    /**
     * Statut actuel de la relation d'amitié (acceptee, traitee, etc.).
     */
    @Enumerated(EnumType.STRING)
    private StatutRelation statut;



    // ==== Constructors ====

    /**
     * Constructeur par défaut requis par JPA.
     */
    public RelationAmis() {}

    /**
     * Constructeur avec tous les champs.
     *
     * @param demandeur l'utilisateur qui envoie la demande
     * @param cible l'utilisateur qui reçoit la demande
     * @param date la date de la relation
     * @param statut le statut de la demande
     */
    public RelationAmis(Utilisateur demandeur, Utilisateur cible, Date date, StatutRelation statut) {
        this.id = new RelationAmisId(demandeur.getIdU(), cible.getIdU());
        this.utilisateurDemandeur = demandeur;
        this.utilisateurCible = cible;
        this.dateRelationAmis = date;
        this.statut = statut;
    }


    // ===== Getters & Setters =====

    /**
     * @return RelationAmisId retourne la clé primaire composite de la relation
     */
    public RelationAmisId getId() {
        return id;
    }

    /**
     * @param id définit la clé primaire composite de la relation
     */
    public void setId(RelationAmisId id) {
        this.id = id;
    }

    /**
     * @return Utilisateur retourne l'utilisateur qui a envoyé la demande d'amitié
     */
    public Utilisateur getUtilisateurDemandeur() {
        return utilisateurDemandeur;
    }

    /**
     * @param utilisateurDemandeur définit l'utilisateur qui a envoyé la demande d'amitié
     */
    public void setUtilisateurDemandeur(Utilisateur utilisateurDemandeur) {
        this.utilisateurDemandeur = utilisateurDemandeur;
    }

    /**
     * @return Utilisateur retourne l'utilisateur cible (destinataire de la demande)
     */
    public Utilisateur getUtilisateurCible() {
        return utilisateurCible;
    }

    /**
     * @param utilisateurCible définit l'utilisateur qui reçoit la demande d'amitié
     */
    public void setUtilisateurCible(Utilisateur utilisateurCible) {
        this.utilisateurCible = utilisateurCible;
    }

    /**
     * @return Date retourne la date à laquelle la relation d'amitié a été créée ou modifiée
     */
    public Date getDateRelationAmis() {
        return dateRelationAmis;
    }

    /**
     * @param dateRelationAmis définit la date de la relation d'amitié
     */
    public void setDateRelationAmis(Date dateRelationAmis) {
        this.dateRelationAmis = dateRelationAmis;
    }

    /**
     * @return StatutRelation retourne le statut actuel de la relation (acceptée, en attente...)
     */
    public StatutRelation getStatut() {
        return statut;
    }

    /**
     * @param statut définit le statut actuel de la relation d'amitié
     */
    public void setStatut(StatutRelation statut) {
        this.statut = statut;
    }
}
