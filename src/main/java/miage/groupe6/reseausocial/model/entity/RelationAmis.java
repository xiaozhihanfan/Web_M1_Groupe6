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
    @MapsId("idUtilisateurDemande")
    @JoinColumn(name = "id_utilisateur_demande")
    private Utilisateur idUtilisateurDemande;

    /**
     * Utilisateur qui reçoit la demande d'amitié.
     */
    @ManyToOne
    @MapsId("idUtilisateurRecu")
    @JoinColumn(name = "id_utilisateur_recu")
    private Utilisateur idUtilisateurRecu;

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
    public RelationAmis(Utilisateur idUtilisateurDemande, Utilisateur idUtilisateurRecu, Date date, StatutRelation statut) {
        this.id = new RelationAmisId(idUtilisateurDemande.getIdU(), idUtilisateurRecu.getIdU());
        this.idUtilisateurDemande = idUtilisateurDemande;
        this.idUtilisateurRecu = idUtilisateurRecu;
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
    public Utilisateur getIdUtilisateurDemande() {
        return idUtilisateurDemande;
    }

    /**
     * @param idUtilisateurDemande définit l'utilisateur qui a envoyé la demande d'amitié
     */
    public void setIdUtilisateurDemande(Utilisateur idUtilisateurDemande) {
        this.idUtilisateurDemande = idUtilisateurDemande;
    }

    /**
     * @return Utilisateur retourne l'utilisateur cible (destinataire de la demande)
     */
    public Utilisateur getUtilisateurCible() {
        return idUtilisateurRecu;
    }

    /**
     * @param idUtilisateurRecu définit l'utilisateur qui reçoit la demande d'amitié
     */
    public void setIdUtilisateurRecu(Utilisateur idUtilisateurRecu) {
        this.idUtilisateurRecu = idUtilisateurRecu;
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
