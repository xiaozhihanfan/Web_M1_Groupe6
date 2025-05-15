package miage.groupe6.reseausocial.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


/**
 * Clé primaire composite pour l'entité {@link RelationAmis}.
 * Elle est composée de deux identifiants utilisateurs représentant
 * la relation entre le demandeur et le destinataire de la demande d'amitié.
 */
@Embeddable

public class RelationAmisId implements Serializable {

    /**
     * Identifiant de l'utilisateur qui a envoyé la demande d'amitié.
     */
    @Column(name = "id_utilisateur_demande")
    private Long idUtilisateurDemande;

    /**
     * Identifiant de l'utilisateur qui a reçu la demande d'amitié.
     */
    @Column(name = "id_utilisateur_recu")
    private Long idUtilisateurRecu;




    // ==== Constructors ====
    /**
     * Constructeur par défaut requis par JPA.
     */
    public RelationAmisId() {}

    /**
     * Constructeur avec paramètres.
     *
     * @param idUtilisateurDemande identifiant de l'utilisateur demandeur
     * @param idUtilisateurRecu identifiant de l'utilisateur receveur
     */
    public RelationAmisId(Long idUtilisateurDemande, Long idUtilisateurRecu) {
        this.idUtilisateurDemande = idUtilisateurDemande;
        this.idUtilisateurRecu = idUtilisateurRecu;
    }



    // ==== Getter and setter

    /**
     * @return Long retourne l'identifiant de l'utilisateur qui a envoyé la demande d'amitié
     */
    public Long getIdUtilisateurDemande() {
        return idUtilisateurDemande;
    }

    /**
     * @param utilisateurDemandeurId définit l'identifiant de l'utilisateur demandeur dans la relation
     */
    public void setIdUtilisateurDemande(Long idUtilisateurDemande) {
        this.idUtilisateurDemande = idUtilisateurDemande;
    }

    /**
     * @return Long retourne l'identifiant de l'utilisateur qui a reçu la demande d'amitié
     */
    public Long getIdUtilisateurRecu() {
        return idUtilisateurRecu;
    }

    /**
     * @param idUtilisateurRecu définit l'identifiant de l'utilisateur cible dans la relation
     */
    public void setIdUtilisateurRecu(Long idUtilisateurRecu) {
        this.idUtilisateurRecu = idUtilisateurRecu;
    }




    /**
     * Calcule un code de hachage pour cette clé composite.
     * Ce code est utilisé par les structures de données basées sur le hachage,
     * telles que {@link java.util.HashMap} ou {@link java.util.HashSet}.
     *
     * @return un entier représentant le hash code de l'objet
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idUtilisateurDemande == null) ? 0 : idUtilisateurDemande.hashCode());
        result = prime * result + ((idUtilisateurRecu == null) ? 0 : idUtilisateurRecu.hashCode());
        return result;
    }

    /**
     * Vérifie si deux objets {@code RelationAmisId} sont logiquement équivalents.
     * Deux objets sont considérés égaux s'ils contiennent les mêmes identifiants utilisateur.
     *
     * @param obj l'objet à comparer avec l'instance courante
     * @return {@code true} si les objets sont égaux, sinon {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RelationAmisId other = (RelationAmisId) obj;
        if (idUtilisateurDemande == null) {
            if (other.idUtilisateurDemande != null)
                return false;
        } else if (!idUtilisateurDemande.equals(other.idUtilisateurDemande))
            return false;
        if (idUtilisateurRecu == null) {
            if (other.idUtilisateurRecu != null)
                return false;
        } else if (!idUtilisateurRecu.equals(other.idUtilisateurRecu))
            return false;
        return true;
    }
}
