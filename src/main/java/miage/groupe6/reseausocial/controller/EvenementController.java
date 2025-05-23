package miage.groupe6.reseausocial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.RelationAmis;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.ActionEvenementService;
import miage.groupe6.reseausocial.model.jpa.service.ActionPostService;
import miage.groupe6.reseausocial.model.jpa.service.EvenementsService;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;

/**
 * Contrôleur Spring MVC gérant les opérations relatives aux événements :
 * affichage de la page d'index, création d'un nouvel événement,
 * et enregistrement des actions des utilisateurs sur un événement.
 */
@Controller
@RequestMapping("/evenements")
public class EvenementController {

    @Autowired
    private PostService ps;

    @Autowired
    private EvenementsService es;

    @Autowired
    private ActionPostService aps;

    @Autowired
    private RelationAmisService relationAmisService;

    @Autowired
    private ActionEvenementService aes;
	
    /**
     * Affiche la page d'index des événements.  
     * <ul>
     *   <li>Si l'utilisateur n'est pas authentifié, redirige vers la page de connexion.</li>
     *   <li>Sinon, récupère tous les événements et posts, calcule les statistiques
     *       (nombre de posts, d'amitiés, d'événements), et injecte ces données dans le modèle.</li>
     * </ul>
     *
     * @param session session HTTP pour récupérer l'utilisateur courant
     * @param model   modèle Spring MVC pour passer les données à la vue
     * @return le nom de la vue Thymeleaf (« events ») ou une redirection vers « /utilisateurs/signin »
     */
	@GetMapping("/index")
	public String Index(HttpSession session, Model model) {
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		List<Evenement> evenements = es.findAll(); 
        model.addAttribute("evenements", evenements); 
        if (utilisateur == null) {
            return "redirect:/utilisateurs/signin";   

        }
        List<Post> allPosts = ps.findAllOrderedByDateDesc();
        model.addAttribute("posts", allPosts);

        List<Evenement> exploreEvents = es.findExploreEvents(utilisateur);
        model.addAttribute("exploreEvents", exploreEvents);
        
        int nbPost = ps.countPostByUtilisateur(utilisateur);
        List<RelationAmis> demandes = relationAmisService.getDemandesRecues(utilisateur);
        int nbEvenement = es.countEvenements(utilisateur);

        if (!model.containsAttribute("nbPost")) {
            model.addAttribute("nbPost", ps.countPostByUtilisateur(utilisateur));
        }
        if (!model.containsAttribute("nbAmis")) {
            model.addAttribute("nbAmis", relationAmisService.countAmis(utilisateur));
        }
        if (!model.containsAttribute("nbEvenement")) {
            model.addAttribute("nbEvenement", es.countEvenements(utilisateur));
        }

        for (int i = 0; i < allPosts.size(); i++) {
            Post post = allPosts.get(i);
            int nbLikes = aps.countLikes(post);
            post.setNombreLikes(nbLikes);
        }
        
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("demandesAmis", demandes);
        model.addAttribute("nbEvenement", nbEvenement);
        model.addAttribute("nbPost", nbPost);
        model.addAttribute("evenements",evenements);
		return "events";
	}

  
    /**
     * Point d'API REST pour créer un nouvel événement.  
     * L'objet Evenement reçu en JSON est lié à l'utilisateur courant avant d'être persisté.
     *
     * @param evenement données de l'événement reçues dans le corps de la requête (JSON)
     * @param session   session HTTP pour récupérer l'utilisateur courant
     * @return ResponseEntity contenant l'entité Evenement sauvegardée
     */
    @PostMapping
    public ResponseEntity<Evenement> createEvenement(@RequestBody Evenement evenement, HttpSession session) {

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        evenement.setUtilisateur(utilisateur);
        Evenement savedEvenement = es.save(evenement);
        return ResponseEntity.ok(savedEvenement);
    }


    /**
     * Enregistre une action de l'utilisateur sur un événement donné (participation, intérêt, etc.).  
     * Si l'utilisateur n'est pas authentifié, redirige vers la page de login.
     *
     * @param id     identifiant de l'événement ciblé
     * @param statut statut de l'action (enum StatutActionEvenement)
     * @param session session HTTP pour récupérer l'utilisateur courant
     * @return chaîne de redirection vers la page de détail de l'événement ou vers « /login »
     */
    @PostMapping("/evenements/{id}/action")
    public String enregistrerActionEvenement(
            @PathVariable Long id,
            @RequestParam StatutActionEvenement statut,
            HttpSession session) {
        Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
        if (u == null) {
            return "redirect:/login";
        }

        aes.actOnEvent(u.getIdU(), id, statut);
        return "redirect:/evenements/" + id;
    }
}
