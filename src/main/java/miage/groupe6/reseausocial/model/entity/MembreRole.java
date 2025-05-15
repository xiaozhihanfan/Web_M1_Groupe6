package miage.groupe6.reseausocial.model.entity;

/**
 * Représente le rôle qu’un utilisateur peut avoir au sein d’un groupe.
 * 
 * Ce rôle permet de définir les droits ou responsabilités dans le groupe.
 * 
 * {@code MEMBRE} : utilisateur standard du groupe, avec des droits limités.
 * {@code ADMIN} : utilisateur ayant des privilèges d’administration (par exemple : gérer les membres, modifier les paramètres du groupe).
 * 
 * Auteur : Mengyi YANG
 */

public enum MembreRole {

    /**
     * Membre standard du groupe.
     */
    MEMBRE, 
    
    /**
     * Administrateur du groupe.
     */
    ADMIN
    
}
