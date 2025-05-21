package miage.groupe6.reseausocial.controller;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;
import miage.groupe6.reseausocial.model.jpa.service.EvenementsService;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;


/**
 * Contrôleur Spring MVC pour la gestion des utilisateurs.
 * <p>
 * Prend en charge :
 * <ul>
 *   <li>l'affichage et la soumission des formulaires de connexion (sign-in) et d'inscription (sign-up),</li>
 *   <li>la recherche d'utilisateurs par nom, prénom ou email,</li>
 *   <li>l'affichage du profil d'un autre utilisateur.</li>
 * </ul>
 * Utilise {@link UtilisateurService} pour la logique métier et
 * {@link UtilisateurRepository} pour les opérations de persistence.
 * </p>
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

    @Autowired
    private EvenementsService evenementsService;



    /**
     * Affiche le formulaire de connexion.
     *
     * @return le nom de la vue "sign-in"
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
            redirectAttributes.addFlashAttribute("nbPost", nbPost);


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
     * @param email            l'adresse email
     * @param password         le mot de passe
     * @param confirmPassword  la confirmation du mot de passe
     * @param nomU             le nom de famille
     * @param prenomU          le prénom
     * @param universite       l'université
     * @param ville            la ville
     * @param birthday         la date de naissance au format ISO (yyyy-MM-dd)
     * @param ine              le code INE
     * @param model            le modèle pour afficher les erreurs
     * @return "sign-in" si succès, sinon "sign-up"
     */
    @PostMapping("/verifierSignUp")
    public String verifierSignUp(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String nomU,
            @RequestParam String prenomU,
            @RequestParam String universite,
            @RequestParam String ville,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                         LocalDate birthday,
            @RequestParam String ine,
            Model model) {

        String res = us.verifierSignUp(
            email, password, confirmPassword,
            nomU, prenomU, universite, ville,
            birthday, ine
        );
        if ("succès".equals(res)) {
            return "sign-in";
        }
        // en cas d'erreur, renvoyer les valeurs remplies pour ne pas tout retaper
        model.addAttribute("erreur", res);
        model.addAttribute("email", email);
        model.addAttribute("nomU", nomU);
        model.addAttribute("prenomU", prenomU);
        model.addAttribute("universite", universite);
        model.addAttribute("ville", ville);
        model.addAttribute("birthday", birthday);
        model.addAttribute("ine", ine);
        return "sign-up";
    }

    

    // ========================= US1.3: Recherche ========================= //

    /**
     * Exécute la recherche d'utilisateurs par nom, prénom ou email.
     * <p>
     * Exclut l'utilisateur en session des résultats.
     * </p>
     *
     * @param query   la chaîne de recherche (email si contient '@', sinon nom/prénom)
     * @param model   le modèle pour passer des attributs à la vue
     * @param session la session HTTP pour obtenir l'utilisateur connecté
     * @return la vue "resultatsRechercherUtilisateurs" ou redirection vers sign-in
     */
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

        Map<Long, Integer> nbPostsParUtilisateur = new HashMap<>();
        for (Utilisateur u : utilisateurs) {
            int nbPost = ps.countPostByUtilisateur(u);
            nbPostsParUtilisateur.put(u.getIdU(), nbPost);
        }

        Map<Long, Integer> nbEvenementsParUtilisateur = new HashMap<>();
        for (Utilisateur u : utilisateurs) {
            int nbEvenement = evenementsService.countEvenements(u);
            nbEvenementsParUtilisateur.put(u.getIdU(), nbEvenement);
        }

        int nbPost = ps.countPostByUtilisateur(utilisateurSession);
        int nbAmis = ras.countAmis(utilisateurSession);
        int nbEvenement = evenementsService.countEvenements(utilisateurSession);
        

        model.addAttribute("utilisateur", utilisateurSession);
        model.addAttribute("nbPost", nbPost);
        model.addAttribute("nbAmis",nbAmis);
        model.addAttribute("nbEvenement", nbEvenement);
        model.addAttribute("query", query);
        model.addAttribute("utilisateurs", utilisateurs);
        model.addAttribute("nbPostsParUtilisateur", nbPostsParUtilisateur);
        model.addAttribute("nbEvenementsParUtilisateur", nbEvenementsParUtilisateur);
        
        return "resultatsRechercherUtilisateurs";
    }



}
