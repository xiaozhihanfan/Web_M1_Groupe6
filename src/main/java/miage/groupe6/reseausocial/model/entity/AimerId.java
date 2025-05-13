package miage.groupe6.reseausocial.model.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AimerId implements Serializable {

    private Long idU;
    private Long idP;

   // === Getters et Setters ===

    /**
     * @return l'identifiant de l'utilisateur
     */
    public Long getIdU() {
        return idU;
    }

    /**
     * @param idU l'identifiant de l'utilisateur à définir
     */
    public void setIdU(Long idU) {
        this.idU = idU;
    }

    /**
     * @return l'identifiant de la publication
     */
    public Long getIdP() {
        return idP;
    }

    /**
     * @param idP l'identifiant de la publication à définir
     */
    public void setIdP(Long idP) {
        this.idP = idP;
    }

    /**
     * Vérifie l'égalité logique entre deux objets {@code AimerId}.
     *
     * @param o l'objet à comparer
     * @return {@code true} si les deux identifiants sont égaux
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AimerId)) return false;
        AimerId that = (AimerId) o;
        return Objects.equals(idU, that.idU) && Objects.equals(idP, that.idP);
    }

    /**
     * Génère un code de hachage basé sur les deux identifiants.
     *
     * @return un hashCode cohérent avec {@code equals()}
     */
    @Override
    public int hashCode() {
        return Objects.hash(idU, idP);
    }
}
