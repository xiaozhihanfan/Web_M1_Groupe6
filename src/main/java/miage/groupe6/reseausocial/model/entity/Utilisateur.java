package miage.groupe6.reseausocial.model.entity;



import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Représente un utilisateur du réseau social.
 * 
 * Un utilisateur possède des informations personnelles telles que son nom, prénom, email, mot de passe, description, avatar, 
 * et la date de son inscription. Il peut aimer des publications, envoyer et recevoir des demandes d’amis.
 * 
 */

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    /**
     * Identifiant unique du utilisateur.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idU;

    private String nomU;
    private String prenomU;
    private String emailU;
    private String mpU;
    private String descriptionU;
    private String avatarU;
    private String dateInscription;
    private LocalDate birthday;
    private String telephone;
    private String userName;

    /** Publications créées par l'utilisateur. */
    @OneToMany(mappedBy = "idP", cascade = CascadeType.ALL)
    private Set<Post> posts = new HashSet<>();

    /** Ensemble des publications aimées par l'utilisateur. */
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private Set<ActionPost> actionPosts = new HashSet<>();

    /** Commentaires écrits par l'utilisateur. */
    @OneToMany(mappedBy = "idC", cascade = CascadeType.ALL)
    private Set<Commentaire> commentaires = new HashSet<>();

    /** Actions réalisées sur les événements (s'inscrire, s'intéresser). */
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private Set<ActionEvenement> actionEvenements = new HashSet<>();

    /** Événements créés par l'utilisateur. */
    @OneToMany(mappedBy = "idE", cascade = CascadeType.ALL)
    private Set<Evenement> evenements = new HashSet<>();

    /** Groupes auxquels l'utilisateur appartient. */
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private Set<GroupeMembre> groupeMembres = new HashSet<>();

    /** Groupes créés par l'utilisateur. */
    @OneToMany(mappedBy = "idGroupe", cascade = CascadeType.ALL)
    private Set<Groupe> groupes = new HashSet<>();

    /** Liste des messages envoyés par cet utilisateur, que ce soit en privé ou dans un groupe. */
    @OneToMany(mappedBy = "envoyeur", cascade = CascadeType.ALL)
    private Set<Message> messagesEnvoyes = new HashSet<>();

    /** Liste des messages privés reçus par cet utilisateur. */
    @OneToMany(mappedBy = "recepteur", cascade = CascadeType.ALL)
    private Set<Message> messagesRecus = new HashSet<>();

    /** Demandes d’amis envoyées par l'utilisateur. */
    @OneToMany(mappedBy = "utilisateurDemande", cascade = CascadeType.ALL)
    private Set<RelationAmis> amisDemandes = new HashSet<>();

    /** Demandes d’amis reçues par l'utilisateur. */
    @OneToMany(mappedBy = "utilisateurRecu", cascade = CascadeType.ALL)
    private Set<RelationAmis> amisRecus = new HashSet<>();


    /**
     * Constructeur par défaut.
     */
    public Utilisateur() {}

    /**
     * Constructeur avec tous les champs principaux.
     * 
     * @param avatarU avatar de l'utilisateur
     * @param dateInscription date d'inscription
     * @param descriptionU description de l'utilisateur
     * @param emailU email
     * @param idU identifiant unique
     * @param mpU mot de passe
     * @param nomU nom
     * @param prenomU prénom
     * @param birthday date de naissance
     * @param telephone numéro tel
     * @param userName nom de Reseau Social
     */
    public Utilisateur(String avatarU, String dateInscription, String descriptionU, String emailU, Long idU, String mpU, String nomU, String prenomU, LocalDate birthday, String telephone, String userName) {
        this.avatarU = avatarU;
        this.dateInscription = dateInscription;
        this.descriptionU = descriptionU;
        this.emailU = emailU;
        this.idU = idU;
        this.mpU = mpU;
        this.nomU = nomU;
        this.prenomU = prenomU;
        this.birthday = birthday;
        this.telephone = telephone;
        this.userName = userName;
    }

    // ==== Getters & Setters ====

    /**
     * @return l'identifiant unique de l'utilisateur
     */
    public Long getIdU() {
        return idU;
    }

    /**
     * Définit l'identifiant unique de l'utilisateur.
     * @param idU identifiant
     */
    public void setIdU(Long idU) {
        this.idU = idU;
    }

    /**
     * @return le nom de l'utilisateur
     */
    public String getNomU() {
        return nomU;
    }

    /**
     * Définit le nom de l'utilisateur.
     * @param nomU nom
     */
    public void setNomU(String nomU) {
        this.nomU = nomU;
    }

    /**
     * @return le prénom de l'utilisateur
     */
    public String getPrenomU() {
        return prenomU;
    }

    /**
     * Définit le prénom de l'utilisateur.
     * @param prenomU prénom
     */
    public void setPrenomU(String prenomU) {
        this.prenomU = prenomU;
    }

    /**
     * @return l'adresse e-mail de l'utilisateur
     */
    public String getEmailU() {
        return emailU;
    }

    /**
     * Définit l'adresse e-mail de l'utilisateur.
     * @param emailU adresse e-mail
     */
    public void setEmailU(String emailU) {
        this.emailU = emailU;
    }

    /**
     * @return le mot de passe de l'utilisateur
     */
    public String getMpU() {
        return mpU;
    }

    /**
     * Définit le mot de passe de l'utilisateur.
     * @param mpU mot de passe
     */
    public void setMpU(String mpU) {
        this.mpU = mpU;
    }

    /**
     * @return la description de l'utilisateur
     */
    public String getDescriptionU() {
        return descriptionU;
    }

    /**
     * Définit la description de l'utilisateur.
     * @param descriptionU description
     */
    public void setDescriptionU(String descriptionU) {
        this.descriptionU = descriptionU;
    }

    /**
     * @return l'URL ou le chemin de l'avatar de l'utilisateur
     */
    public String getAvatarU() {
        return avatarU;
    }

    /**
     * Définit l'avatar de l'utilisateur.
     * @param avatarU chemin ou URL de l'avatar
     */
    public void setAvatarU(String avatarU) {
        this.avatarU = avatarU;
    }

    /**
     * @return la date d'inscription de l'utilisateur
     */
    public String getDateInscription() {
        return dateInscription;
    }

    /**
     * Définit la date d'inscription de l'utilisateur.
     * @param dateInscription date
     */
    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }

    /**
     * @return les posts créés par l'utilisateur
     */
    public Set<Post> getPosts() {
        return posts;
    }

    /**
     * Définit les posts créés par l'utilisateur.
     * @param posts ensemble des posts
     */
    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    /**
     * @return les actions réalisées par l'utilisateur sur les posts
     */
    public Set<ActionPost> getActionPosts() {
        return actionPosts;
    }

    /**
     * Définit les actions réalisées par l'utilisateur sur les posts.
     * @param actionPosts ensemble des actions
     */
    public void setActionPosts(Set<ActionPost> actionPosts) {
        this.actionPosts = actionPosts;
    }

    /**
     * @return les commentaires réalisés par l'utilisateur
     */
    public Set<Commentaire> getCommentaires() {
        return commentaires;
    }

    /**
     * Définit les commentaires réalisés par l'utilisateur.
     * @param commentaires ensemble des commentaires
     */
    public void setCommentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    /**
     * @return les actions réalisées sur les événements par l'utilisateur
     */
    public Set<ActionEvenement> getActionEvenements() {
        return actionEvenements;
    }

    /**
     * Définit les actions réalisées sur les événements par l'utilisateur.
     * @param actionEvenements ensemble des actions
     */
    public void setActionEvenements(Set<ActionEvenement> actionEvenements) {
        this.actionEvenements = actionEvenements;
    }

    /**
     * @return les événements créés par l'utilisateur
     */
    public Set<Evenement> getEvenements() {
        return evenements;
    }

    /**
     * Définit les événements créés par l'utilisateur.
     * @param evenements ensemble des événements
     */
    public void setEvenements(Set<Evenement> evenements) {
        this.evenements = evenements;
    }

    /**
     * @return les groupes auxquels l'utilisateur appartient
     */
    public Set<GroupeMembre> getGroupeMembres() {
        return groupeMembres;
    }

    /**
     * Définit les groupes auxquels l'utilisateur appartient.
     * @param groupeMembres ensemble des appartenances aux groupes
     */
    public void setGroupeMembres(Set<GroupeMembre> groupeMembres) {
        this.groupeMembres = groupeMembres;
    }

    /**
     * @return les groupes créés par l'utilisateur
     */
    public Set<Groupe> getGroupes() {
        return groupes;
    }

    /**
     * Définit les groupes créés par l'utilisateur.
     * @param groupes ensemble des groupes
     */
    public void setGroupes(Set<Groupe> groupes) {
        this.groupes = groupes;
    }

    /**
     * @return les messages envoyés par l'utilisateur
     */
    public Set<Message> getMessagesEnvoyes() {
        return messagesEnvoyes;
    }

    /**
     * Définit les messages envoyés par l'utilisateur.
     * @param messagesEnvoyes ensemble des messages
     */
    public void setMessagesEnvoyes(Set<Message> messagesEnvoyes) {
        this.messagesEnvoyes = messagesEnvoyes;
    }

    /**
     * @return les messages reçus par l'utilisateur
     */
    public Set<Message> getMessagesRecus() {
        return messagesRecus;
    }

    /**
     * Définit les messages reçus par l'utilisateur.
     * @param messagesRecus ensemble des messages
     */
    public void setMessagesRecus(Set<Message> messagesRecus) {
        this.messagesRecus = messagesRecus;
    }

    /**
     * @return les demandes d'amis envoyées par l'utilisateur
     */
    public Set<RelationAmis> getAmisDemandes() {
        return amisDemandes;
    }

    /**
     * Définit les demandes d'amis envoyées par l'utilisateur.
     * @param amisDemandes ensemble des relations d'amis
     */
    public void setAmisDemandes(Set<RelationAmis> amisDemandes) {
        this.amisDemandes = amisDemandes;
    }

    /**
     * @return les demandes d'amis reçues par l'utilisateur
     */
    public Set<RelationAmis> getAmisRecus() {
        return amisRecus;
    }

    /**
     * Définit les demandes d'amis reçues par l'utilisateur.
     * @param amisRecus ensemble des relations d'amis
     */
    public void setAmisRecus(Set<RelationAmis> amisRecus) {
        this.amisRecus = amisRecus;
    }


    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}

