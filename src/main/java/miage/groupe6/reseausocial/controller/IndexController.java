package miage.groupe6.reseausocial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.RelationAmis;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.ActionPostService;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;

/**
 * Contrôleur pour la page d’accueil de l’application.
 * Gère la redirection vers la page de connexion si l’utilisateur
 * n’est pas authentifié, ou vers la vue index sinon.
 */
@Controller
public class IndexController {

    @Autowired
    private PostService ps;

    @Autowired
    private ActionPostService aps;

    @Autowired
    private RelationAmisService relationAmisService;

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
            return "redirect:/utilisateurs/signin";   

        }


        List<Post> allPosts = ps.findAllOrderedByDateDesc();
        model.addAttribute("posts", allPosts);

        int nbPost = ps.countPostByUtilisateur(utilisateur);
        List<RelationAmis> demandes = relationAmisService.getDemandesRecues(utilisateur);

        if (!model.containsAttribute("nbPost")) {
            model.addAttribute("nbPost", ps.countPostByUtilisateur(utilisateur));
        }
        if (!model.containsAttribute("nbAmis")) {
            model.addAttribute("nbAmis", relationAmisService.countAmis(utilisateur));
        }

        for (int i = 0; i < allPosts.size(); i++) {
            Post post = allPosts.get(i);
            int nbLikes = aps.countLikes(post);
            post.setNombreLikes(nbLikes);
        }
        
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("demandesAmis", demandes);

        model.addAttribute("nbPost", nbPost);


        return "index";
    }



}
