package miage.groupe6.reseausocial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.RelationAmis;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;

@ControllerAdvice
public class GlobalModelAttribute {
    @Autowired
    private RelationAmisService relationAmisService;

    // Les demandes de messages d'amis peuvent être affichées sur toutes les pages.
    @ModelAttribute("demandesAmis")
    public List<RelationAmis> populateDemandesAmis(HttpSession session) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur != null) {
            return relationAmisService.getDemandesRecues(utilisateur);
        }
        return List.of(); 
    }

    /**
     * Fournit à toutes les vues Thymeleaf l'utilisateur connecté en session,
     * sous l'attribut "sessionUser".
     */
    @ModelAttribute("sessionUser")
    public Utilisateur sessionUser(HttpSession session) {
        return (Utilisateur) session.getAttribute("utilisateur");
    }
}
