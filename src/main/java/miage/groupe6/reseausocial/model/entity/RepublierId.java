package miage.groupe6.reseausocial.model.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class RepublierId implements Serializable {

    private Long idU;
    private Long idP;


    // ==== Constructors ====
    
    public RepublierId() {}

    public RepublierId(Long idU, Long idP) {
        this.idU = idU;
        this.idP = idP;
    }



    // ==== Getter et Setter ====
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



    // hashCode and equals
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idU == null) ? 0 : idU.hashCode());
        result = prime * result + ((idP == null) ? 0 : idP.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RepublierId other = (RepublierId) obj;
        if (idU == null) {
            if (other.idU != null)
                return false;
        } else if (!idU.equals(other.idU))
            return false;
        if (idP == null) {
            if (other.idP != null)
                return false;
        } else if (!idP.equals(other.idP))
            return false;
        return true;
    }
}
