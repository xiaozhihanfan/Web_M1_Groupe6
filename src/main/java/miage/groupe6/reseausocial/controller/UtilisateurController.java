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
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;


/**
 * Contrôleur Spring pour la gestion des utilisateurs.
 * Il gère l'inscription, la connexion et l'affichage des pages correspondantes.
 * 
 */
@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService us;

    @Autowired
    private PostService ps;

    @Autowired
    private RelationAmisService ras;



    /**
     * Affiche le formulaire d'inscription.
     */
    @GetMapping("/signin")
	public String signIn() {
		return "sign-in";
	}

 
    /**
     * Gère la soumission du formulaire de connexion et vérifie les identifiants de l’utilisateur.
     * Appelle le service utilisateur pour valider l’email et le mot de passe :
     * Si les identifiants sont valides, stocke l’entité Utilisateur dans la session,
     * Récupère et transmet via RedirectAttributes les statistiques de l’utilisateur
     * (nombre de posts, de followings et de followers),
     * Redirige vers la page d’accueil.
     * En cas d’échec, ajoute un message d’erreur au modèle et renvoie la vue de connexion.
     *
     * @param email               l’adresse e-mail saisie dans le formulaire de connexion
     * @param password            le mot de passe saisi dans le formulaire de connexion
     * @param redirectAttributes  container pour les attributs flash lors de la redirection
     * @param session             la session HTTP utilisée pour stocker l’utilisateur connecté
     * @param model               le modèle MVC pour transmettre les messages d’erreur à la vue
     * @return                     « redirect:/ » en cas de succès, sinon « sign-in » pour réafficher la page de connexion
     */
    @PostMapping("/verifierSignIn")
    public String verifierSignIn(@RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes, HttpSession session, Model model) {
        Utilisateur utilisateur = us.verifierSignIn(email, password);
        if (utilisateur != null) {
            session.setAttribute("utilisateur", utilisateur);
            int nbPost = ps.countPostByUtilisateur(utilisateur);
            int nbFollowing = ras.countFollowingAccepte(utilisateur);
            int nbFollowers = ras.countFollowersAccepte(utilisateur);
            redirectAttributes.addFlashAttribute("nbPost", nbPost);
            redirectAttributes.addFlashAttribute("nbFollowing", nbFollowing);
            redirectAttributes.addFlashAttribute("nbFollowers", nbFollowers);

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

    // ========================= US1.3: Recherche ========================= //

    @GetMapping("/rechercher")
    public String afficherFormulaireRechercher(Model model, HttpSession session) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur == null) return "redirect:/utilisateurs/signin";
        model.addAttribute("utilisateur", utilisateur);
        return "rechercherUtilisateurs";
    }

    @GetMapping("/resultats")
    public String rechercherUtilisateurs(@RequestParam("query") String query, Model model, HttpSession session) {
        Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateurSession == null) return "redirect:/utilisateurs/signin";

        List<Utilisateur> utilisateurs;

        if (query.contains("@")) {
            Utilisateur u = us.rechercherParEmail(query);
            utilisateurs = u != null ? new java.util.ArrayList<>(List.of(u)) : new java.util.ArrayList<>();
        } else {
            utilisateurs = us.rechercherParNomOuPrenom(query);
        }

        
        utilisateurs.removeIf(u -> u.getIdU().equals(utilisateurSession.getIdU()));

        model.addAttribute("utilisateur", utilisateurSession);
        model.addAttribute("query", query);
        model.addAttribute("utilisateurs", utilisateurs);
        return "resultatsRechercherUtilisateurs";
    }

    // ========================= regarder les utilisatuers rechercheés ========================= //

    @GetMapping("/{id}")
    public String afficherProfilUtilisateur(@PathVariable Long id, Model model, HttpSession session) {
        Optional<Utilisateur> utilisateur = us.getUtilisateurById(id);
        Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("utilisateur");

        if (utilisateur.isPresent()) {
            model.addAttribute("utilisateur", utilisateurSession);
            model.addAttribute("autre", utilisateur.get());
            return "profilUtilisateur";
        } else {
            model.addAttribute("erreur", "Utilisateur introuvable.");
            return "erreur-404";
        }
    }


}
