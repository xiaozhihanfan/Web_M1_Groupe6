package miage.groupe6.reseausocial.model.entity;

/**
 * Représente un message envoyé soit à un groupe, soit à un autre utilisateur (message privé).
 * Le message peut être de type :
 * privé : envoyeur → recepteur
 * groupe : envoyeur → groupe
 * 
 * Auteur : Mengyi YANG
 */

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMessage;

    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    private Date temps;

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
