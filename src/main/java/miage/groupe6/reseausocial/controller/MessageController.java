package miage.groupe6.reseausocial.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Message;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.MessageService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

@Controller
@RequestMapping("/messages")
public class MessageController {
    
    @Autowired
    private MessageService messageService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private RelationAmisService relationAmisService;

    @GetMapping("/{idRecepteur}")
    public String afficherDiscussion(@PathVariable Long idRecepteur, 
                                     HttpSession session, Model model) {
        Utilisateur envoyeur = (Utilisateur) session.getAttribute("utilisateur");
        Utilisateur recepteur = utilisateurService.getUtilisateurById(idRecepteur).orElse(null);

        if(recepteur == null || envoyeur == null) {
            return "redirect:/";
        }

        List<Message> messages = messageService.getMessageEntre(envoyeur, recepteur);
        List<Utilisateur> amis = relationAmisService.listerAmis(envoyeur);

        model.addAttribute("envoyeur", envoyeur);
        model.addAttribute("recpteur", recepteur);
        model.addAttribute("messages", messages);

        model.addAttribute("utilisateur", envoyeur);

        model.addAttribute("amis", amis);
        return "messaging";
    }

    @PostMapping("/envoyer")
    public String envoyerMessage(@RequestParam Long idRecpteur,
                                 @RequestParam String contenu,
                                 HttpSession session) {
        Utilisateur envoyeur = (Utilisateur) session.getAttribute("utilisateur");
        Utilisateur recepteur = utilisateurService.getUtilisateurById(idRecpteur).orElse(null);
                                    
        if(envoyeur != null && recepteur != null && !contenu.isBlank()) {
            messageService.envoyerMessage(envoyeur, recepteur, contenu);
        }

        return "redirect:/messages/" + idRecpteur;
    }
}