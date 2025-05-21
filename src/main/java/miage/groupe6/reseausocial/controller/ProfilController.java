package miage.groupe6.reseausocial.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Groupe;
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
     * @param session la session HTTP pour identifier l’utilisateur connecté
     * @param model le modèle Spring MVC dans lequel ajouter l’attribut « utilisateur »
     * @return le nom de la vue Thymeleaf à rendre (my-profile-about)
     * @throws ResponseStatusException avec code 500 en cas d’erreur interne
     */
    @GetMapping("/{id}/profile-about")
    public String afficherProfil(@PathVariable Long id,
                                HttpSession session,
                                Model model) {
        try {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            boolean estProprietaire = utilisateur != null && utilisateur.getIdU().equals(id);
            model.addAttribute("estProprietaire", estProprietaire);

            Utilisateur profilUtilisateur = profilService.getProfileById(id);
            model.addAttribute("profilUtilisateur", profilUtilisateur);

            return "my-profile-about";
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
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
     * @param session la session HTTP pour identifier l’utilisateur connecté
     * @param model le modèle Spring MVC dans lequel ajouter l’attribut « utilisateur »
     * @return le nom de la vue Thymeleaf à rendre (my-profile-post)
     * @throws ResponseStatusException avec code 500 en cas d’erreur interne
     */
    @GetMapping("/{id}/profile-post")
    public String afficherProfilePost(@PathVariable Long id,
                                    HttpSession session,
                                    Model model) {
        try {
            // --- NOUVEAU : déterminer si on est propriétaire du profil ---
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            boolean estProprietaire = utilisateur != null && utilisateur.getIdU().equals(id);
            model.addAttribute("estProprietaire", estProprietaire);
            // ---------------------------------------------------------------

            Utilisateur profilUtilisateur = profilService.getProfileById(id);
            List<Post> posts = postService.findByAuteurOrderByDateDesc(utilisateur);

            model.addAttribute("profilUtilisateur", profilUtilisateur);
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
     * Ne propose l'édition que si c'est son propre profil.
     */
    @GetMapping("/{idU}/profile-connections")
    public String afficherProfileConnections(
            @PathVariable("idU") Long idU,
            HttpSession session,
            Model model) {
        try {

            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

            boolean estProprietaire = false;
            if (utilisateur != null && utilisateur.getIdU().equals(idU)) {
                estProprietaire = true;
            }
            model.addAttribute("estProprietaire", estProprietaire);

            Utilisateur profilUtilisateur = profilService.getProfileById(idU);
            List<Utilisateur> amis = relationAmisService.listerAmis(utilisateur);

            model.addAttribute("profilUtilisateur", profilUtilisateur);
            model.addAttribute("amis", amis);

            return "my-profile-connections";
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(), ex);
        }
    }


   /**
     * Affiche la page des événements classés par type (créés, inscrits, intéressés).
     *
     * @param id      identifiant de l’utilisateur
     * @param session HTTP session pour savoir si c’est son propre profil
     * @param model   modèle Thymeleaf
     * @return vue "my-profile-events"
     */
    @GetMapping("/{id}/profile-events")
    public String afficherProfileEvents(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        Utilisateur utilisatuer = (Utilisateur) session.getAttribute("utilisateur");
        boolean estProprietaire = utilisatuer != null && utilisatuer.getIdU().equals(id);
        model.addAttribute("estProprietaire", estProprietaire);

        Utilisateur profilUtilisateur = profilService.getProfileById(id);
        model.addAttribute("profilUtilisateur", profilUtilisateur);

        List<Evenement> crees = profilService.getEvenementsCrees(id);
        List<Evenement> inscrits = profilService.getEvenementsInscrits(id);
        List<Evenement> interesses = profilService.getEvenementsInteresses(id);

        model.addAttribute("evenementsCrees",    crees);
        model.addAttribute("evenementsInscrits", inscrits);
        model.addAttribute("evenementsIntereses", interesses);

        return "my-profile-events";
    }

    /**
     * Affiche la page des groupes d’un utilisateur,
     * séparés selon qu’il en est admin ou simple membre.
     *
     * @param id      identifiant de l’utilisateur
     * @param session session HTTP pour vérifier le propriétaire
     * @param model   modèle Thymeleaf
     * @return vue "my-profile-groups"
     */
    @GetMapping("/{id}/profile-groups")
    public String afficherProfileGroups(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        boolean estProprietaire = utilisateur != null && utilisateur.getIdU().equals(id);
        model.addAttribute("estProprietaire", estProprietaire);

        Utilisateur profilUtilisateur = profilService.getProfileById(id);
        model.addAttribute("profilUtilisateur", profilUtilisateur);

        List<Groupe> groupesAdmin = profilService.getGroupesAdmin(id);
        List<Groupe> groupesMembre = profilService.getGroupesMembre(id);

        model.addAttribute("groupesAdmin", groupesAdmin);
        model.addAttribute("groupesMembre", groupesMembre);

        model.addAttribute("groupe", new Groupe());
        return "my-profile-groups";
    }

}
