package miage.groupe6.reseausocial.model.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

/**
 * Classe représentant une clé primaire composite pour l'entité {@code ActionPost},
 * composée des identifiants de l'utilisateur et de la publication liked/unliked/republié.
 * 
 * Utilisée avec {@code @EmbeddedId} dans JPA.
 */
@Embeddable
public class ActionPostId implements Serializable {

    private Long idU;
    private Long idP;




    // === Constructors ===

    /**
     * Constructeur sans argument requis par JPA.
     */
    public ActionPostId() {}

    /**
     * Constructeur permettant de définir la clé composite avec l'ID utilisateur et l'ID publication.
     *
     * @param idU l'identifiant de l'utilisateur
     * @param idP l'identifiant de la publication
     */
    public ActionPostId(Long idU, Long idP) {
        this.idU = idU;
        this.idP = idP;
    }



    
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
     * Calcule le code de hachage pour cette clé composite.
     * Utilisé par les collections (ex: HashMap, HashSet).
     *
     * @return un entier représentant le hash code de l'objet
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idU == null) ? 0 : idU.hashCode());
        result = prime * result + ((idP == null) ? 0 : idP.hashCode());
        return result;
    }

    /**
     * Vérifie si deux objets {@code ActionPostId} sont équivalents sur le plan logique.
     * Cela repose sur l'égalité des deux champs : {@code idU} et {@code idP}.
     *
     * @param obj l'objet à comparer avec {@code this}
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
        ActionPostId other = (ActionPostId) obj;
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
