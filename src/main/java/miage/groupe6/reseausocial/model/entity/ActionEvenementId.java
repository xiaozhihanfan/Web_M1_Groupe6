package miage.groupe6.reseausocial.model.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

/**
 * Représente la clé primaire composite pour l'entité ActionEvenement.
 * Composée des identifiants de l'utilisateur (idU) et de l'événement (idE).
 */
@Embeddable
public class ActionEvenementId implements Serializable {
    
    /**
     * Identifiant de l'utilisateur.
     * Identifiant de l'événement.
     */
    private Long idU;
    private Long idE;

    /**
     * Constructeur par défaut.
     */
    public ActionEvenementId() {
    }

    /**
     * Constructeur avec paramètres.
     *
     * @param idE identifiant de l'événement
     * @param idU identifiant de l'utilisateur
     */
    public ActionEvenementId(Long idE, Long idU) {
        this.idE = idE;
        this.idU = idU;
    }

    /**
     * Retourne l'identifiant de l'utilisateur.
     * @return idU
     */
    public Long getIdU() {
        return idU;
    }

    /**
     * Définit l'identifiant de l'utilisateur.
     * @param idU identifiant utilisateur
     */
    public void setIdU(Long idU) {
        this.idU = idU;
    }

    /**
     * Retourne l'identifiant de l'événement.
     * @return idE
     */
    public Long getIdE() {
        return idE;
    }

    /**
     * Définit l'identifiant de l'événement.
     * @param idE identifiant événement
     */
    public void setIdE(Long idE) {
        this.idE = idE;
    }

    /**
     * Calcule le hash code basé sur les identifiants utilisateur et événement.
     * @return hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idU);
        hash = 29 * hash + Objects.hashCode(this.idE);
        return hash;
    }

    /**
     * Vérifie l'égalité entre deux objets ActionEvenementId.
     * @param obj autre objet à comparer
     * @return true si égal, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ActionEvenementId other = (ActionEvenementId) obj;
        if (!Objects.equals(this.idU, other.idU)) {
            return false;
        }
        return Objects.equals(this.idE, other.idE);
    }



}
