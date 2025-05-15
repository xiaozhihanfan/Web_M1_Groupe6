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

    /**
     * @param idGroupe the idGroupe to set
     */
    public void setIdGroupe(Long idGroupe) {
        this.idGroupe = idGroupe;
    }

    /**
     * @return String return the nomGroupe
     */
    public String getNomGroupe() {
        return nomGroupe;
    }

    /**
     * @param nomGroupe the nomGroupe to set
     */
    public void setNomGroupe(String nomGroupe) {
        this.nomGroupe = nomGroupe;
    }

    /**
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Utilisateur return the createur
     */
    public Utilisateur getCreateur() {
        return createur;
    }

    /**
     * @param createur the createur to set
     */
    public void setCreateur(Utilisateur createur) {
        this.createur = createur;
    }

    /**
     * @return Set<GroupeMembre> return the membres
     */
    public Set<GroupeMembre> getMembres() {
        return membres;
    }

    /**
     * @param membres the membres to set
     */
    public void setMembres(Set<GroupeMembre> membres) {
        this.membres = membres;
    }


    /**
     * @return Evenement return the evenement
     */
    public Evenement getEvenement() {
        return evenement;
    }

    /**
     * @param evenement the evenement to set
     */
    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

}
