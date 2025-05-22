package miage.groupe6.reseausocial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.RelationAmis;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.ActionEvenementService;
import miage.groupe6.reseausocial.model.jpa.service.ActionPostService;
import miage.groupe6.reseausocial.model.jpa.service.EvenementsService;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;

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
	
	@GetMapping("/index")
	public String Index(HttpSession session,Model model) {
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
	
	
	
	
}
