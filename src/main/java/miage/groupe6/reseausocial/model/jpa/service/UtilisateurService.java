package miage.groupe6.reseausocial.model.jpa.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;

/**
 * Service métier pour la gestion des utilisateurs.
 * Contient la logique liée à l'inscription et à la connexion.
 * Ce service agit comme une couche intermédiaire entre le contrôleur et la couche repository.
 * 
 * Auteur : Mengyi YANG
 */
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository ur;

    /**
     * Inscription d'un nouvel utilisateur.
     * @param utilisateur Utilisateur à enregistrer
     * @return true si inscription réussie, false si e-mail déjà utilisé
     */
    public boolean creerCompt(Utilisateur utilisateur) {
        if (ur.findByEmail(utilisateur.getEmailU()).isPresent()) {
            return false;
        }
        utilisateur.setDateInscription(new Date());
        ur.save(utilisateur);
        return true;
    }

    /**
     * Vérifie les identifiants pour la connexion.
     * @param email e-mail saisi
     * @param mp mot de passe saisi
     * @return Utilisateur si authentification réussie, sinon null
     */
    public boolean verifierConnexion(String email, String mp) {
        Optional<Utilisateur> utilisateurO = ur.findByEmail(email);

        if(utilisateurO.isPresent()){
            Utilisateur utilisateur = utilisateurO.get();
            if(utilisateur.getMpU().equals(mp)){
                return true;
            }
        }
        return false;
    }
}
