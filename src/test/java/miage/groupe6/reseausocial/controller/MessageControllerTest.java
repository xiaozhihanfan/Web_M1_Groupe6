package miage.groupe6.reseausocial.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.Message;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.MessageRepository;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;
import miage.groupe6.reseausocial.model.jpa.service.GroupeService;
import miage.groupe6.reseausocial.model.jpa.service.MessageService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

/**
 * Tests d’intégration du contrôleur {@link MessageController} avec MockMvc.
 */
@WebMvcTest(MessageController.class)
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @MockBean
    private UtilisateurService utilisateurService;

    @MockBean
    private RelationAmisService relationAmisService;

    @MockBean
    private GroupeService groupeService;

    /**
     * Vérifie l’affichage d’une discussion privée entre deux utilisateurs.
     */
    @Test
    void testAfficherDiscussionPrivee() throws Exception {
        Utilisateur u1 = new Utilisateur(); u1.setIdU(1L);
        Utilisateur u2 = new Utilisateur(); u2.setIdU(2L);

        Mockito.when(utilisateurService.getUtilisateurById(2L)).thenReturn(Optional.of(u2));
        Mockito.when(relationAmisService.listerAmis(u1)).thenReturn(List.of(u2));
        Mockito.when(messageService.getMessageEntre(u1, u2)).thenReturn(List.of());
        Mockito.when(groupeService.getGroupesOuEtreMembre(u1)).thenReturn(List.of());

        mockMvc.perform(get("/messages/2").sessionAttr("utilisateur", u1))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("messages"))
                .andExpect(view().name("messaging"));
    }

    /**
     * Vérifie l’envoi d’un message privé.
     */
    @Test
    void testEnvoyerMessagePrive() throws Exception {
        Utilisateur u1 = new Utilisateur(); u1.setIdU(1L);
        Utilisateur u2 = new Utilisateur(); u2.setIdU(2L);

        Mockito.when(utilisateurService.getUtilisateurById(2L)).thenReturn(Optional.of(u2));

        mockMvc.perform(post("/messages/envoyer")
                        .param("idRecpteur", "2")
                        .param("contenu", "Bonjour")
                        .sessionAttr("utilisateur", u1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/messages/2"));
    }

    /**
     * Vérifie l’affichage des messages d’un groupe.
     */
    @Test
    void testAfficherDiscussionGroupe() throws Exception {
        Utilisateur u1 = new Utilisateur(); u1.setIdU(1L);
        Groupe groupe = new Groupe(); groupe.setIdGroupe(100L);

        Mockito.when(groupeService.getGroupeById(100L)).thenReturn(Optional.of(groupe));
        Mockito.when(groupeService.getGroupesOuEtreMembre(u1)).thenReturn(List.of(groupe));
        Mockito.when(messageService.getMessagesGroupe(groupe)).thenReturn(List.of());
        Mockito.when(relationAmisService.listerAmis(u1)).thenReturn(List.of());

        mockMvc.perform(get("/messages/groupe/100").sessionAttr("utilisateur", u1))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("messages"))
                .andExpect(model().attribute("groupe", groupe))
                .andExpect(view().name("messaging"));
    }

    /**
     * Vérifie l’envoi d’un message à un groupe.
     */
    @Test
    void testEnvoyerMessageGroupe() throws Exception {
        Utilisateur u1 = new Utilisateur(); u1.setIdU(1L);
        Groupe groupe = new Groupe(); groupe.setIdGroupe(100L);

        Mockito.when(groupeService.getGroupeById(100L)).thenReturn(Optional.of(groupe));

        mockMvc.perform(post("/messages/groupe/100/envoyer")
                        .param("contenu", "Message pour tous")
                        .sessionAttr("utilisateur", u1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/messages/groupe/100"));
    }

    
}
