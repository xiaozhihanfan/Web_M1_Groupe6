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

    /** 
     * Utilisateur ayant créé le groupe.
     * Un utilisateur peut créer plusieurs groupes.
     */
    @ManyToOne
    @JoinColumn(name = "idUtilisateurCreateur")
    private Utilisateur createur;
    
    /**
     * Ensemble des membres du groupe.
     * Un groupe peut avoir plusieurs membres.
     */
    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GroupeMembre> membres = new HashSet<>();

    /**
     * Messages envoyés dans ce groupe par les utilisateurs.
     * Ne contient que les messages de groupe.
     */
    @OneToMany(mappedBy = "groupe")
    private Set<Message> messagesGroupe = new HashSet<>();


    /**
     * @return Long return the idGroupe
     */
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
     * @return Set<Message> return the messagesGroupe
     */
    public Set<Message> getMessagesGroupe() {
        return messagesGroupe;
    }

    /**
     * @param messagesGroupe the messagesGroupe to set
     */
    public void setMessagesGroupe(Set<Message> messagesGroupe) {
        this.messagesGroupe = messagesGroupe;
    }

}
