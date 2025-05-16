package miage.groupe6.reseausocial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Contrôleur pour la page d’accueil de l’application.
 * Gère la redirection vers la page de connexion si l’utilisateur
 * n’est pas authentifié, ou vers la vue index sinon.
 */
@Controller
public class IndexController {

    /**
     * Point d’entrée principal de l’application.
     * Si l’utilisateur n’est pas présent dans la session, redirige
     * vers la page de connexion. Sinon, transmet l’objet Utilisateur
     * à la vue « index ».
     *
     * @param session la session HTTP pour récupérer l’utilisateur authentifié
     * @param model   le modèle MVC pour passer les attributs à la vue
     * @return nom de la vue ou instruction de redirection
     */
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur == null) {
            return "redirct:/utilisateurs/signin";   
        }
        model.addAttribute("utilisateur", utilisateur);
        return "index";
    }

}
