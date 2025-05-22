package miage.groupe6.reseausocial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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

  
      @PostMapping
    public ResponseEntity<Evenement> createEvenement(@RequestBody Evenement evenement, HttpSession session) {

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        evenement.setUtilisateur(utilisateur);
        Evenement savedEvenement = es.save(evenement);
        return ResponseEntity.ok(savedEvenement);
    }



    @PostMapping("/{id}/action/{statut}")
    public Object actOnEvent(
            @PathVariable Long id,
            @PathVariable StatutActionEvenement statut,
            HttpSession session,
            @RequestHeader(name = "Accept", required = false) String acceptHeader) {

        Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
        if (u == null) {
            if (acceptHeader != null && acceptHeader.contains("application/json")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return "redirect:/utilisateurs/signin";
        }

        ActionEvenement ae = aes.actOnEvent(u.getIdU(), id, statut);

        if (acceptHeader != null && acceptHeader.contains("application/json")) {
            return ResponseEntity.ok(ae);
        } else {
            return "redirect:/evenements/" + id;
        }
    }

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
