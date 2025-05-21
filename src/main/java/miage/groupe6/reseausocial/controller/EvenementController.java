package miage.groupe6.reseausocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.EvenementsService;


@RestController
@RequestMapping("/evenements")
public class EvenementController {

    @Autowired
    private EvenementsService es;


    @PostMapping("/")
    public ResponseEntity<Evenement> createEvenement(@RequestBody Evenement evenement, HttpSession session) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        evenement.setUtilisateur(utilisateur);
        Evenement savedEvenement = es.save(evenement);
        return ResponseEntity.ok(savedEvenement);
    }

  
}
