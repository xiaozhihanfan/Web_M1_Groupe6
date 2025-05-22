package miage.groupe6.reseausocial.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.ActionEvenement;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.ActionEvenementService;
import miage.groupe6.reseausocial.model.jpa.service.EvenementsService;

/**
 * MVC Controller responsible for handling requests to display details of an event.
 * <p>
 * Provides a route GET /evenements/{id} which renders the "events-details" Thymeleaf template
 * populated with the specified Evenement entity.
 */
@Controller
@RequestMapping("/evenements")
public class EvenementDetailController {

    private final EvenementsService evenementService;

    private final ActionEvenementService aes;

    /**
     * Constructs the controller with the required EvenementsService.
     *
     * @param evenementService the service used to retrieve event data
     */
    @Autowired
    public EvenementDetailController(EvenementsService evenementService, ActionEvenementService aes) {
        this.evenementService = evenementService;
        this.aes = aes;
    }

    /**
     * Handle GET requests for the event detail page.
     * <p>
     * Retrieves the Evenement with associated details (participants, comments, etc.)
     * via the service and adds it as a model attribute named "evenement".
     * Returns the logical view name "events-details" which corresponds to
     * src/main/resources/templates/events-details.html.
     *
     * @param id    the unique identifier of the event to display
     * @param model the Spring MVC model used to pass attributes to the view
     * @return the name of the Thymeleaf template for event details
     * @throws EntityNotFoundException if no event exists with the given id
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model m, HttpSession session) {
        Evenement evenement = evenementService.getEvenementAvecDetails(id);

        Utilisateur current = (Utilisateur) session.getAttribute("utilisateur");

        Optional<ActionEvenement> actionOpt = aes.findByUserAndEvent(current.getIdU(), id);
        StatutActionEvenement statut = actionOpt.map(ActionEvenement::getStatut).orElse(null);


        // Créateur ou déjà INSCRIRE → on cache tout
        boolean hideAll = evenement.getUtilisateur().getIdU().equals(current.getIdU()) || StatutActionEvenement.INSCRIRE.equals(statut); 

        // S’inscrire :  
        // si on ne cache pas tout, ET (aucune action OU on est seulement INTERESSER)
        boolean showInscrire = !hideAll && (statut == null || StatutActionEvenement.INTERESSER.equals(statut));

        // Intéresser :
        // si on ne cache pas tout, ET aucune action n’a encore eu lieu
        boolean showInteress = !hideAll && statut == null;

        m.addAttribute("hideAll", hideAll);
        m.addAttribute("showInscrire", showInscrire);
        m.addAttribute("showInteress", showInteress);

        m.addAttribute("evenement", evenement);
        m.addAttribute("utilisateur", session.getAttribute("utilisateur"));
        m.addAttribute("nbInscriptions", aes.countInscriptions(id));
        m.addAttribute("nbInteresses", aes.countInteresses(id));
        return "event-details"; 
    }


}