package miage.groupe6.reseausocial.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.CommentaireService;
import miage.groupe6.reseausocial.model.jpa.service.PostService;

/**
 * Contrôleur pour la gestion des commentaires des publications.
 */
@Controller
@RequestMapping("/commentaires")
public class CommentaireController {

    @Autowired
    private CommentaireService cs;

    @Autowired
    private PostService ps;

    /**
     * Gère l'ajout d'un commentaire à un post donné.
     * @param postId l'identifiant du post
     * @param contenu le contenu du commentaire
     * @param session session HTTP pour récupérer l'utilisateur connecté
     * @return redirection vers l'accueil ou la page du post
     */
    @PostMapping("/creer/{postId}")
    public String ajouterCommentaire(@PathVariable("postId") Long postId, @RequestParam("contenu") String contenu, HttpSession session, RedirectAttributes redirectAttributes) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur == null) {
            return "redirect:/utilisateurs/signin";
        }

        Optional<Post> optPost = ps.findById(postId);
        if (optPost.isEmpty()) {
            redirectAttributes.addFlashAttribute("erreur", "Le post est introuvable.");
            return "redirect:/";
        }

        cs.ajouterCommentaire(utilisateur, optPost.get(), contenu);

        return "redirect:/";
    }

    /**
     * Gère la requête HTTP GET pour la page d'accueil.
     * 
     * Ce contrôleur récupère la liste de tous les posts avec leurs commentaires associés 
     * en utilisant le service PostService, les ajoute au modèle, et renvoie la vue "index".
     *
     * @param model le modèle Spring MVC utilisé pour passer des attributs à la vue
     * @return le nom de la vue à afficher, ici "index"
     */
    @GetMapping("/")
    public String afficherAccueil(Model model) {
        List<Post> posts = ps.findAllPostsWithCommentaires();
        model.addAttribute("posts", posts);
        return "index";

    }
}
