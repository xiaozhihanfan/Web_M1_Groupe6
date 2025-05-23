package miage.groupe6.reseausocial.model.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Représente un message envoyé soit à un groupe, soit à un autre utilisateur (message privé).
 * Le message peut être de type :
 * privé : envoyeur → recepteur
 * groupe : envoyeur → groupe
 * 
 */

@Entity
@Table(name = "message")
public class Message {

    /**
     * Identifiant unique du message.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMessage;

    /**
     * Contenu textuel du message.
     */
    private String text;

    /**
     * Date et heure d’envoi du message.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date temps;

    /**
     * Utilisateur ayant envoyé le message.
     */
    @ManyToOne
    @JoinColumn(name = "idUtilisateurEnvoyeur", nullable = false)
    private Utilisateur envoyeur;


    /**
     * Utilisateur qui a reçu ce message si c’est un message privé (peut être null si message de groupe).
     */
    @ManyToOne
    @JoinColumn(name = "idUtilisateurRecepteur")
    private Utilisateur recepteur;

    /**
     * Groupe auquel ce message est adressé s’il s’agit d’un message de groupe (peut être null).
     */
    @ManyToOne
    @JoinColumn(name = "idGroupeRecepteur")
    private Groupe groupe;

    // ===== Constructeurs =====

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Message() {

    }

    /**
     * Constructeur complet.
     *
     * @param envoyeur utilisateur envoyeur
     * @param groupe groupe destinataire (si message de groupe)
     * @param idMessage identifiant du message
     * @param recepteur utilisateur destinataire (si message privé)
     * @param temps date d’envoi
     * @param text contenu textuel
     */
    public Message(Utilisateur envoyeur, Groupe groupe, Long idMessage, Utilisateur recepteur, Date temps, String text) {
        this.envoyeur = envoyeur;
        this.groupe = groupe;
        this.idMessage = idMessage;
        this.recepteur = recepteur;
        this.temps = temps;
        this.text = text;
    }



    /**
     * @return Long return the idMessage
     */
    public Long getIdMessage() {
        return idMessage;
    }

    /**
     * @param idMessage the idMessage to set
     */
    public void setIdMessage(Long idMessage) {
        this.idMessage = idMessage;
    }

    /**
     * @return String return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return Date return the temps
     */
    public Date getTemps() {
        return temps;
    }

    /**
     * @param temps the temps to set
     */
    public void setTemps(Date temps) {
        this.temps = temps;
    }

    /**
     * @return Utilisateur return the envoyeur
     */
    public Utilisateur getEnvoyeur() {
        return envoyeur;
    }

    /**
     * @param envoyeur the envoyeur to set
     */
    public void setEnvoyeur(Utilisateur envoyeur) {
        this.envoyeur = envoyeur;
    }

    /**
     * @return Utilisateur return the recepteur
     */
    public Utilisateur getRecepteur() {
        return recepteur;
    }

    /**
     * @param recepteur the recepteur to set
     */
    public void setRecepteur(Utilisateur recepteur) {
        this.recepteur = recepteur;
    }

    /**
     * @return Groupe return the groupe
     */
    public Groupe getGroupe() {
        return groupe;
    }

    /**
     * @param groupe the groupe to set
     */
    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    

}
