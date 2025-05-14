package miage.groupe6.reseausocial.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class GroupeMembre {

    @EmbeddedId
    private GroupeMembreId id = new GroupeMembreId();

    @Enumerated(EnumType.STRING)
    private MembreRole role;

    private LocalDateTime dateAdhesion;

    @ManyToOne
    @MapsId("idGroupe")
    @JoinColumn(name = "idGroupe")
    private Groupe groupe;

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
