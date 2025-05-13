package miage.groupe6.reseausocial.model.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AimerId implements Serializable {

    private Long idU;
    private Long idP;

    public AimerId() {}

    public AimerId(Long idU, Long idP) {
        this.idU = idU;
        this.idP = idP;
    }

    public Long getIdU() {
        return idU;
    }

    public void setIdU(Long idU) {
        this.idU = idU;
    }

    public Long getIdP() {
        return idP;
    }

    public void setIdP(Long idP) {
        this.idP = idP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AimerId)) return false;
        AimerId that = (AimerId) o;
        return Objects.equals(idU, that.idU) && Objects.equals(idP, that.idP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idU, idP);
    }
}
