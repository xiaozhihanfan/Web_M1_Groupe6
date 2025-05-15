package miage.groupe6.reseausocial.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;


/**
 * Représente l'association entre un {@link Utilisateur} et un {@link Groupe} 
 * dans le cadre d'une relation de type "membre de groupe" dans le réseau social.
 * 
 * Cette entité utilise une clé composite {@link GroupeMembreId} qui contient 
 * les identifiants du groupe et de l'utilisateur.
 * 
 * Elle stocke également des informations contextuelles comme le rôle du membre
 * et la date d'adhésion.
 * 
 * @author Mengyi YANG
 */

@Entity
public class GroupeMembre {

    /** Clé composite représentant l'association groupe-utilisateur. */
    @EmbeddedId
    private GroupeMembreId id = new GroupeMembreId();

    @Enumerated(EnumType.STRING)
    private MembreRole role;

    private LocalDateTime dateAdhesion;

    /** Groupe auquel appartient le membre. */
    @ManyToOne
    @MapsId("idGroupe")
    @JoinColumn(name = "idGroupe")
    private Groupe groupe;

    /** Utilisateur membre du groupe. */
    @ManyToOne
    @MapsId("idUtilisateur")
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur;

    public GroupeMembreId getId() {
        return id;
    }

    public void setId(GroupeMembreId id) {
        this.id = id;
    }

    public MembreRole getRole() {
        return role;
    }

    public void setRole(MembreRole role) {
        this.role = role;
    }

    public LocalDateTime getDateAdhesion() {
        return dateAdhesion;
    }

    public void setDateAdhesion(LocalDateTime dateAdhesion) {
        this.dateAdhesion = dateAdhesion;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    


    

}
