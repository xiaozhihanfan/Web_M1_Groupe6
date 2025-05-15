package miage.groupe6.reseausocial.model.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

/**
 * Clé primaire composée pour l'entité {@link GroupeMembre}.
 * 
 * Cette classe représente la combinaison de deux identifiants : 
 * l'identifiant du groupe et celui de l'utilisateur. 
 * Elle est utilisée pour modéliser la relation "membre de groupe".
 * 
 * Elle doit être marquée comme {@code Serializable} et redéfinir correctement 
 * les méthodes {@code equals} et {@code hashCode} pour fonctionner avec JPA.
 * 
 * Auteur : Mengyi YANG
 */

@Embeddable
public class GroupeMembreId implements Serializable{

    private Long idGroupe;
    private Long idUtilisateur;

    public GroupeMembreId() {}

    public GroupeMembreId(Long idGroupe, Long idUtilisateur) {
        this.idGroupe = idGroupe;
        this.idUtilisateur = idUtilisateur;
    }

    public Long getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(Long idGroupe) {
        this.idGroupe = idGroupe;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupeMembreId)) return false;
        GroupeMembreId that = (GroupeMembreId) o;
        return Objects.equals(idGroupe, that.idGroupe) && Objects.equals(idUtilisateur, that.idUtilisateur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGroupe, idUtilisateur);
    }

    

    

    
    
}
