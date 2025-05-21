package miage.groupe6.reseausocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
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
        Evenement e = evenementService.getEvenementAvecDetails(id);
        m.addAttribute("evenement", e);
        
        m.addAttribute("nbInscriptions", aes.countInscriptions(id));
        m.addAttribute("nbInteresses", aes.countInteresses(id));

        m.addAttribute("utilisateur", session.getAttribute("utilisateur"));
        return "event-details"; 
    }
}