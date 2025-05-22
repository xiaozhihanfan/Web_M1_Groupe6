package miage.groupe6.reseausocial.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.ActionEvenement;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.ActionEvenementService;
import miage.groupe6.reseausocial.model.jpa.service.EvenementsService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;


@RestController
@RequestMapping("/evenements")
public class EvenementRestController {

    @Autowired
    private EvenementsService es;

    @Autowired
    private ActionEvenementService aes;



    @PostMapping("/Creer")
    public ResponseEntity<Evenement> createEvenement(@RequestBody Evenement evenement, HttpSession session) {

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        evenement.setUtilisateur(utilisateur);
        Evenement savedEvenement = es.save(evenement);
        return ResponseEntity.ok(savedEvenement);
    }

    /**
     * POST /evenements/{id}/action/{statut}
     * Permet à l’utilisateur connecté de s’inscrire ou de marquer son intérêt.
     */
    @PostMapping("/{id}/action/{statut}")
    public ResponseEntity<ActionEvenement> actOnEvent(
            @PathVariable Long id,
            @PathVariable StatutActionEvenement statut,
            HttpSession session) {

        Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
        if (u == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ActionEvenement ae = aes.actOnEvent(u.getIdU(), id, statut);
        return ResponseEntity.ok(ae);
    }


  
}
