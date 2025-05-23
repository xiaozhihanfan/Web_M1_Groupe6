package miage.groupe6.reseausocial.controller;

import miage.groupe6.reseausocial.model.entity.RelationAmis;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.EvenementsService;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

/**
 * Tests du contrôleur RelationAmisController.
 * Vérifie les cas d'envoi, d'acceptation et de suppression des relations d'amitié.
 */
@WebMvcTest(RelationAmisController.class)
public class RelationAmisControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RelationAmisService relationAmisService;

    @MockBean
    private UtilisateurService utilisateurService;

    @MockBean
    private PostService postService;

    @MockBean
    private EvenementsService evenementsService;

    /**
     * Vérifie l'envoi d'une demande d'amitié réussie.
     */
    @Test
    void testEnvoyerDemandeAmi_succes() throws Exception {
        Utilisateur demandeur = new Utilisateur();
        demandeur.setIdU(1L);
        demandeur.setEmailU("demandeur@test.com");

        Utilisateur cible = new Utilisateur();
        cible.setIdU(2L);
        cible.setEmailU("cible@test.com");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("utilisateur", demandeur);

        when(utilisateurService.getUtilisateurById(2L)).thenReturn(Optional.of(cible));
        when(relationAmisService.demandeExisteDeja(1L, 2L)).thenReturn(false);

        mockMvc.perform(post("/relationamis/envoyer/2").session(session))
                .andExpect(redirectedUrl("/utilisateurs/resultats?query=cible@test.com"))
                .andExpect(flash().attribute("message", "Demande d'amitié envoyée avec succès."));

        verify(relationAmisService).envoyerDemandeAmi(demandeur, cible);
    }

    /**
     * Vérifie le comportement lorsqu'un utilisateur non connecté essaie d'envoyer une demande.
     */
    @Test
    void testEnvoyerDemandeAmi_sansConnexion() throws Exception {
        mockMvc.perform(post("/relationamis/envoyer/2"))
                .andExpect(redirectedUrl("/utilisateurs/signin"));
    }

    /**
     * Vérifie le comportement si l'utilisateur cible est invalide.
     */
    @Test
    void testEnvoyerDemandeAmi_utilisateurInvalide() throws Exception {
        Utilisateur demandeur = new Utilisateur();
        demandeur.setIdU(1L);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("utilisateur", demandeur);

        when(utilisateurService.getUtilisateurById(1L)).thenReturn(Optional.of(demandeur));

        mockMvc.perform(post("/relationamis/envoyer/1").session(session))
                .andExpect(redirectedUrl("/utilisateurs/rechercher"))
                .andExpect(flash().attribute("erreur", "Utilisateur cible invalide."));
    }

    /**
     * Vérifie l'acceptation d'une demande d'ami.
     */
    @Test
    void testAccepterDemandeAmi() throws Exception {
        Utilisateur receveur = new Utilisateur();
        receveur.setIdU(10L);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("utilisateur", receveur);

        when(relationAmisService.accepterDemandeAmis(5L, 10L)).thenReturn(true);
        when(postService.countPostByUtilisateur(receveur)).thenReturn(3);
        when(relationAmisService.countAmis(receveur)).thenReturn(2);
        when(evenementsService.countEvenements(receveur)).thenReturn(1);

        mockMvc.perform(post("/relationamis/accepter/5").session(session))
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("message", "Demande acceptée !"));
    }

    /**
     * Vérifie le refus d'une demande d'amitié.
     */
    @Test
    void testRefuserDemandeAmi() throws Exception {
        Utilisateur receveur = new Utilisateur();
        receveur.setIdU(20L);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("utilisateur", receveur);

        when(relationAmisService.refuserDemandeAmis(7L, 20L)).thenReturn(true);

        mockMvc.perform(post("/relationamis/refuser/7").session(session))
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("message", "Demande refusée !"));
    }

    /**
     * Vérifie la suppression d'un ami existant.
     */
    @Test
    void testRemoveAmi() throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdU(30L);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("utilisateur", utilisateur);

        RelationAmis relation = new RelationAmis(); 
        when(relationAmisService.findRelationByIds(40L, 30L))
        .thenReturn(List.of(new RelationAmis()));

        mockMvc.perform(get("/relationamis/remove/40").session(session))
                .andExpect(redirectedUrl("/utilisateurs/30/profile-connections"));

        verify(relationAmisService).deleteRelationByIds(40L, 30L);
    }

    /**
     * Vérifie que rien ne se passe si la relation n'existe pas.
     */
    @Test
    void testRemoveAmi_relationInexistante() throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdU(30L);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("utilisateur", utilisateur);

        when(relationAmisService.findRelationByIds(50L, 30L)).thenReturn(null);

        mockMvc.perform(get("/relationamis/remove/50").session(session))
                .andExpect(redirectedUrl("/utilisateurs/30/profile-connections"));

        verify(relationAmisService, never()).deleteRelationByIds(any(), any());
    }
}
