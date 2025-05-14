package miage.groupe6.reseausocial.model.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Clé primaire composite pour l'entité {@link RelationAmis}.
 * Elle est composée de deux identifiants utilisateurs représentant
 * la relation entre le demandeur et le destinataire de la demande d'amitié.
 */
@Embeddable
public class RelationAmisId implements Serializable {

    /**
     * ID de l'utilisateur qui envoie la demande d'amitié.
     */
    private Long utilisateurDemandeurId;

    /**
     * ID de l'utilisateur qui reçoit la demande d'amitié.
     */
    private Long utilisateurCibleId;




    // ==== Constructors ====
    public RelationAmisId() {}

    public RelationAmisId(Long utilisateurDemandeurId, Long utilisateurCibleId) {
        this.utilisateurDemandeurId = utilisateurDemandeurId;
        this.utilisateurCibleId = utilisateurCibleId;
    }



    // ==== Getter and setter

    /**
     * @return Long retourne l'identifiant de l'utilisateur qui a envoyé la demande d'amitié
     */
    public Long getUtilisateurDemandeurId() {
        return utilisateurDemandeurId;
    }

    /**
     * @param utilisateurDemandeurId définit l'identifiant de l'utilisateur demandeur dans la relation
     */
    public void setUtilisateurDemandeurId(Long utilisateurDemandeurId) {
        this.utilisateurDemandeurId = utilisateurDemandeurId;
    }

    /**
     * @return Long retourne l'identifiant de l'utilisateur qui a reçu la demande d'amitié
     */
    public Long getUtilisateurCibleId() {
        return utilisateurCibleId;
    }

    /**
     * @param utilisateurCibleId définit l'identifiant de l'utilisateur cible dans la relation
     */
    public void setUtilisateurCibleId(Long utilisateurCibleId) {
        this.utilisateurCibleId = utilisateurCibleId;
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
        result = prime * result + ((utilisateurDemandeurId == null) ? 0 : utilisateurDemandeurId.hashCode());
        result = prime * result + ((utilisateurCibleId == null) ? 0 : utilisateurCibleId.hashCode());
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
        if (utilisateurDemandeurId == null) {
            if (other.utilisateurDemandeurId != null)
                return false;
        } else if (!utilisateurDemandeurId.equals(other.utilisateurDemandeurId))
            return false;
        if (utilisateurCibleId == null) {
            if (other.utilisateurCibleId != null)
                return false;
        } else if (!utilisateurCibleId.equals(other.utilisateurCibleId))
            return false;
        return true;
    }
}
