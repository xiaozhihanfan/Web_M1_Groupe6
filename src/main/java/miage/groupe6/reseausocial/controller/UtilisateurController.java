package miage.groupe6.reseausocial.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Contrôleur Spring pour la gestion des utilisateurs.
 * Il gère l'inscription, la connexion et l'affichage des pages correspondantes.
 * 
 */
@Controller
@RequestMapping("/utilisateurs")
@SessionAttributes("utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService us;

    @Autowired
    private UtilisateurRepository ur;

    /**
     * Affiche le formulaire d'inscription.
     */
    @GetMapping("/signin")
	public String signIn() {
		return "sign-in";
	}

 
    @PostMapping("/verifierSignIn")
    public String verifierSignIn(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        Utilisateur utilisateur = us.verifierSignIn(email, password);
        if (utilisateur != null) {
            model.addAttribute("utilisateur", utilisateur);
            return "redirect:/";
        }

        String erreur = "L'adresse e-mail ou le mot de passe est incorrect ! ";
		model.addAttribute("erreur", erreur);
		return "sign-in";
    }
    

    /**
     * Affiche la page d'inscription.
     *
     * @param model le modèle utilisé pour passer des attributs à la vue
     * @return le nom de la vue "sign-up" à afficher
     */
    @GetMapping("/signup")
	public String signUp(Model model) {
        return "sign-up";
    }
    

    /**
     * Traite la soumission du formulaire d'inscription.
     *
     * @param email            l'adresse email saisie par l'utilisateur
     * @param password         le mot de passe saisi
     * @param confirmPassword  la confirmation du mot de passe
     * @param model            le modèle pour ajouter des attributs à la vue
     * @return la vue "sign-in" si l'inscription a réussi, sinon "sign-up" avec un message d'erreur
     */
    @PostMapping("/verifierSignUp")
    public String verifierSignUp(@RequestParam String email, @RequestParam String password, @RequestParam String confirmPassword, Model model) {
        String res = us.verifierSignUp(email, password, confirmPassword);
        if (res.equals("succès")) {
            return "sign-in";
        }
        model.addAttribute("erreur", res);
        return "sign-up";  
    }

    


}
