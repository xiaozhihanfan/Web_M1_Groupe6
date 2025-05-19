package miage.groupe6.reseausocial.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.ProfilService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;


/**
 * Controller Spring MVC pour gérer l’affichage du profil utilisateur.
 * <p>
 * Fournit un point d’entrée HTTP GET pour récupérer et afficher les détails
 * d’un utilisateur identifié par son identifiant.
 * </p>
 */
@Controller
@RequestMapping("/utilisateurs")
public class ProfilController {

    /** Service métier pour obtenir les informations de profil d’un utilisateur. */
    @Autowired
    private ProfilService profilService;

    @Autowired
    private PostService   postService;

    @Autowired
    private RelationAmisService relationAmisService;


    /**
     * Affiche la page de profil (« About ») d’un utilisateur.
     * <p>
     * Interroge le {@link ProfilService} pour récupérer l’entité
     * {@link Utilisateur} et la place dans le modèle sous l’attribut « utilisateur ».
     * </p>
     *
     * @param id    l’identifiant unique de l’utilisateur à afficher
     * @param model le modèle Spring MVC dans lequel ajouter l’attribut « utilisateur »
     * @return le nom de la vue Thymeleaf à rendre (my-profile-about)
     * @throws ResponseStatusException avec code 500 en cas d’erreur interne
     */
    @GetMapping("/{id}/profile-about")
    public String afficherProfil(@PathVariable Long id, Model model) {
        try {
            Utilisateur utilisateur = profilService.getProfileById(id);
            model.addAttribute("utilisateur", utilisateur);
            return "my-profile-about";
        } catch (RuntimeException ex) {
            
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                ex
            );
        }
    }

    /**
     * Affiche la page de posts (« Posts ») du profil d’un utilisateur.
     * <p>
     * Récupère l’utilisateur via {@link ProfilService} et le place dans le modèle
     * sous l’attribut « utilisateur » pour que la vue Thymeleaf
     * « my-profile-post.html » puisse y accéder.
     * </p>
     *
     * @param id    l’identifiant unique de l’utilisateur dont on veut afficher les posts
     * @param model le modèle Spring MVC dans lequel ajouter l’attribut « utilisateur »
     * @return le nom de la vue Thymeleaf à rendre (my-profile-post)
     * @throws ResponseStatusException avec code 500 en cas d’erreur interne
     */
    @GetMapping("/{id}/profile-post")
    public String afficherProfilePost(@PathVariable Long id, Model model) {
        try {
            Utilisateur utilisateur = profilService.getProfileById(id);
            List<Post> posts = postService.findByAuteurOrderByDateDesc(utilisateur);
            
            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("posts", posts);

            return "my-profile-post";
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                ex
            );
        }
    }

    /**
     * Affiche la page des connexions (amis) d'un utilisateur.
     *
     * @param id    l'identifiant de l'utilisateur dont on veut voir les amis
     * @param model le modèle Thymeleaf pour transmettre les données à la vue
     * @return le nom de la vue "my-profile-connections"
     * @throws ResponseStatusException en cas d'erreur interne
     */
    @GetMapping("/{id}/profile-connections")
    public String afficherProfileConnections(@PathVariable Long id, Model model) {
        try {
            Utilisateur utilisateur = profilService.getProfileById(id);
            List<Utilisateur> amis = relationAmisService.listerAmis(utilisateur);

            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("amis", amis);
            return "my-profile-connections";
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(), ex);
        }
    }

}
