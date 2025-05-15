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

    /**
     * Identifiant du groupe.
     * Identifiant de l'utilisateur.
     */
    private Long idGroupe;
    private Long idUtilisateur;

    /**
     * Constructeur par défaut requis par JPA.
     */
    public GroupeMembreId() {}

    /**
     * Constructeur complet.
     *
     * @param idGroupe identifiant du groupe
     * @param idUtilisateur identifiant de l'utilisateur
     */
    public GroupeMembreId(Long idGroupe, Long idUtilisateur) {
        this.idGroupe = idGroupe;
        this.idUtilisateur = idUtilisateur;
    }

    /**
     * Retourne l’identifiant du groupe.
     *
     * @return idGroupe
     */
    public Long getIdGroupe() {
        return idGroupe;
    }

    /**
     * Définit l’identifiant du groupe.
     *
     * @param idGroupe nouvel identifiant du groupe
     */
    public void setIdGroupe(Long idGroupe) {
        this.idGroupe = idGroupe;
    }

    /**
     * Retourne l’identifiant de l’utilisateur.
     *
     * @return idUtilisateur
     */
    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    /**
     * Définit l’identifiant de l’utilisateur.
     *
     * @param idUtilisateur nouvel identifiant utilisateur
     */
    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    /**
     * Vérifie l’égalité logique entre deux objets {@code GroupeMembreId}.
     *
     * @param o autre objet à comparer
     * @return {@code true} si les objets sont égaux, sinon {@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupeMembreId)) return false;
        GroupeMembreId that = (GroupeMembreId) o;
        return Objects.equals(idGroupe, that.idGroupe) && Objects.equals(idUtilisateur, that.idUtilisateur);
    }

    /**
     * Calcule le code de hachage pour cette clé composite.
     *
     * @return code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(idGroupe, idUtilisateur);
    }

    

    

    
    
}
