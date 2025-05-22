package miage.groupe6.reseausocial.controller;

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
import miage.groupe6.reseausocial.model.entity.ActionEvenement;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.ActionEvenementService;
import miage.groupe6.reseausocial.model.jpa.service.EvenementsService;


@Controller
@RequestMapping("/evenements")
public class EvenementController {

    @Autowired
    private EvenementsService es;

    @Autowired
    private ActionEvenementService aes;


  
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
