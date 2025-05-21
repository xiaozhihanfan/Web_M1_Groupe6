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
import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.Message;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.GroupeService;
import miage.groupe6.reseausocial.model.jpa.service.MessageService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

/**
 * Contrôleur web pour la gestion des messages privés entre utilisateurs.
 * Permet d'afficher une discussion et d'envoyer des messages.
 */
@Controller
@RequestMapping("/messages")
public class MessageController {
    
    @Autowired
    private MessageService messageService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private RelationAmisService relationAmisService;

    @Autowired
    private GroupeService groupeService;


    // --------------- us 4.1 chat prive ----------------------------
    /**
     * Affiche la page de messagerie entre l'utilisateur connecté et le destinataire sélectionné.
     *
     * @param idRecepteur ID de l'utilisateur destinataire du message
     * @param session La session HTTP pour identifier l'utilisateur connecté
     * @param model Le modèle pour envoyer les données à la vue
     * @return Le nom de la vue "messaging" ou redirection vers l'accueil en cas d'erreur
     */
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
        List<Groupe> groupes = groupeService.getGroupesOuEtreMembre(envoyeur);

        model.addAttribute("groupes" ,groupes);
        model.addAttribute("envoyeur", envoyeur);
        model.addAttribute("recpteur", recepteur);
        model.addAttribute("messages", messages);

        model.addAttribute("utilisateur", envoyeur);

        model.addAttribute("amis", amis);
        return "messaging";
    }

    /**
     * Envoie un message texte entre deux utilisateurs.
     *
     * @param idRecpteur ID du destinataire
     * @param contenu Contenu textuel du message
     * @param session La session HTTP contenant l'utilisateur connecté
     * @return Redirection vers la page de discussion correspondante
     */
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

    // --------------- us 4.2 chat de gourpe ----------------------------

    /**
     * Affiche les messages d'un groupe.
     *
     * @param idGroupe L'identifiant du groupe
     * @param session La session utilisateur
     * @param model Le modèle pour afficher les données
     * @return La page de chat de groupe
     */
    @GetMapping("/groupe/{idGroupe}")
    public String afficherDiscussionGroupe(@PathVariable Long idGroupe,
                                            HttpSession session,
                                            Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        if(utilisateur == null) {
            return "redirect:/utilisateurs/signin";
        }

        Groupe groupe = groupeService.getGroupeById(idGroupe).orElse(null);
        if(groupe == null) {
            return "redirect:/utilisateurs/" + utilisateur.getIdU() + "/profile-groups";
        }

        List<Message> messages = messageService.getMessagesGroupe(groupe);
        List<Groupe> groupes = groupeService.getGroupesOuEtreMembre(utilisateur);

        List<Utilisateur> amis = relationAmisService.listerAmis(utilisateur);
        model.addAttribute("amis", amis);
        model.addAttribute("groupes" ,groupes);
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("envoyeur", utilisateur);
        model.addAttribute("groupe", groupe);
        model.addAttribute("messages", messages);

        return "messaging";
    }

    /**
     * Envoie un message texte dans un groupe.
     *
     * @param idGroupe ID du groupe
     * @param contenu Le message à envoyer
     * @param session La session utilisateur
     * @return Redirection vers la page de groupe
     */
    @PostMapping("/groupe/{idGroupe}/envoyer")
    public String envoyerMessageGroupe(@PathVariable Long idGroupe,
                                       @RequestParam String contenu,
                                        HttpSession session) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        Groupe groupe = groupeService.getGroupeById(idGroupe).orElse(null);

        if(utilisateur != null && groupe != null && !contenu.isBlank()) {
            messageService.envoyerMessageGroupe(contenu, utilisateur, groupe);
        }

        return "redirect:/messages/groupe/" + idGroupe;
    }
}