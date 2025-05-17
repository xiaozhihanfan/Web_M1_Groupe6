package miage.groupe6.reseausocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.ProfilService;


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
    private final ProfilService profilService;

    /**
     * Constructeur par injection du {@link ProfilService}.
     *
     * @param profilService le service à utiliser pour récupérer les données de profil
     */
    @Autowired
    public ProfilController(ProfilService profilService) {
        this.profilService = profilService;
    }

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
}
