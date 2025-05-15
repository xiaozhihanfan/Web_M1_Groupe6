package miage.groupe6.reseausocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

/**
 * Contrôleur Spring pour la gestion des utilisateurs.
 * Il gère l'inscription, la connexion et l'affichage des pages correspondantes.
 * 
 * Auteur : Mengyi YANG
 */
@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService us;

    /**
     * Affiche le formulaire d'inscription.
     */
    @GetMapping("/login")
	public String login(Model model) {
		// model.addAttribute("utilisateur", new Utilisateur());
		return "sign-in";
	}

    /**
     * Traite la soumission du formulaire d'inscription.
     * Si l'email existe déjà, renvoie une erreur.
     */
    @PostMapping("/insert")
	public String insertUtilisateur(@ModelAttribute Utilisateur utilisateur, Model model) {	
		if(!us.creerCompt(utilisateur)) {
			String erreur = "Cette email existe déjà ! ";
			model.addAttribute("erreur", erreur);
            return "formCreerCompt";
		}
		else {
			return "CreerComptConfirmation";
		}
    }

    /**
     * Affiche le formulaire de connexion.
     */
    // @GetMapping("/login")
    // public String pageLogin(Model model) {
    //     model.addAttribute("utilisateur", new Utilisateur());
    //     return "login";
    // }

    @PostMapping("/entrer")
    public String entrerAccueil(@ModelAttribute Utilisateur utilisateur, Model model){
        String email = utilisateur.getEmailU();
        String mp = utilisateur.getMpU();
        if(us.verifierConnexion(email, mp)){
            return "accueil";            
        }
        else{
            String erreur = "L'adresse e-mail ou le mot de passe est incorrect ! ";
			model.addAttribute("erreur", erreur);
            return "login";
        }
        

    }


}
