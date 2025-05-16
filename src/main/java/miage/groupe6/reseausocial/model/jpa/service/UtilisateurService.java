package miage.groupe6.reseausocial.model.jpa.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;

/**
 * Service métier pour la gestion des utilisateurs.
 * Contient la logique liée à l'inscription et à la connexion.
 * Ce service agit comme une couche intermédiaire entre le contrôleur et la couche repository.
 * 
 * Auteur : Mengyi YANG, Zheng Shiwei, Xue Kunying
 */

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository ur;

    @Autowired
    private PostService ps;

    /**
     * Gère l'inscription d'un utilisateur.
     * 
     * @param email
     * @param password
     * @param confirmPassword
     * @return un message indiquant le résultat de l'inscription
     * 
     */
    public String verifierSignUp(String email, String password, String confirmPassword) {
        Optional<Utilisateur> utilisateurOpt =  ur.findByEmailU(email);
        if (utilisateurOpt.isPresent()) {
            return "Cet email existe déjà ! ";
        } 

        if (!password.equals(confirmPassword)) {
            return "Les deux mots de passe ne sont pas identiques !";
        }

        Utilisateur utilisateur = new Utilisateur();
        String dateInscription = LocalDate.now().toString();
        utilisateur.setEmailU(email);
        utilisateur.setMpU(password);
        utilisateur.setDateInscription(dateInscription);
        ur.save(utilisateur);

        return "succès";
    }

    /**
     * Vérifie les identifiants pour la connexion.
     * @param email e-mail saisi
     * @param mp mot de passe saisi
     * @return Utilisateur si authentification réussie, sinon null
     */
    public Utilisateur verifierSignIn(String email, String mp) {
        Optional<Utilisateur> utilisateurOpt = ur.findByEmailU(email);
        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            if (utilisateur.getMpU().equals(mp)) {
                if (utilisateur.getNomU() == null && utilisateur.getPrenomU() == null) {
                    utilisateur.setPrenomU("modier votre");
                    utilisateur.setNomU("profil");
                }
                if(utilisateur.getDescriptionU() == null || utilisateur.getDescriptionU().isEmpty()){
                    utilisateur.setDescriptionU("Voici la description par défaut...");
                }
                if (utilisateur.getAvatarU() == null || utilisateur.getAvatarU().isEmpty()) {
                    utilisateur.setAvatarU("/assets/images/avatar/placeholder.jpg");
                }
                return utilisateur;
            }
        }
        return null;
    }

    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return ur.findById(id);
    }

    public List<Utilisateur> rechercherParNomOuPrenom(String query) {
        return ur.findByNomUContainingIgnoreCaseOrPrenomUContainingIgnoreCase(query, query);
    }

    public Utilisateur rechercherParEmail(String emailU) {
        return ur.findByEmailU(emailU).orElse(null);
    }
}
