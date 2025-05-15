package miage.groupe6.reseausocial.model.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Représente un groupe dans le réseau social.
 * 
 * Chaque groupe est créé par un utilisateur et peut contenir plusieurs membres.
 * Il contient des informations de base telles que le nom, la description et le créateur du groupe.
 * 
 * Auteur : Mengyi YANG
 */

@Entity
@Table(name = "groupe")
public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGroupe;

    private String nomGroupe;

    private String description;


    @ManyToOne
    @JoinColumn(name = "idUtilisateurCreateur")
    private Utilisateur createur;

    @ManyToOne
    @JoinColumn(name = "groupeEvenement")
    private Evenement evenement;
    
    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GroupeMembre> membres = new HashSet<>();



    public Groupe() {}
    
    public Groupe(Long idGroupe, String nomGroupe, String description, Utilisateur createur, Evenement evenement,
            Set<GroupeMembre> membres) {
        this.idGroupe = idGroupe;
        this.nomGroupe = nomGroupe;
        this.description = description;
        this.createur = createur;
        this.evenement = evenement;
        this.membres = membres;
    }



    
    public Long getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(Long idGroupe) {
        this.idGroupe = idGroupe;
    }

    public String getNomGroupe() {
        return nomGroupe;
    }

    public void setNomGroupe(String nomGroupe) {
        this.nomGroupe = nomGroupe;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Utilisateur getCreateur() {
        return createur;
    }

    public void setCreateur(Utilisateur createur) {
        this.createur = createur;
    }

    public Set<GroupeMembre> getMembres() {
        return membres;
    }

    public void setMembres(Set<GroupeMembre> membres) {
        this.membres = membres;
    }

    


}
