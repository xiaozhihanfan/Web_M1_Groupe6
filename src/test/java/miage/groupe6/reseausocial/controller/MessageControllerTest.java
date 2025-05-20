package miage.groupe6.reseausocial.controller;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import miage.groupe6.reseausocial.model.entity.Message;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.MessageRepository;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;
import miage.groupe6.reseausocial.model.jpa.service.MessageService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RelationAmisService relationAmisService;

    private Utilisateur u1;
    private Utilisateur u2;

    @BeforeEach
    void setUp() {
        
        messageRepository.deleteAll();
        utilisateurRepository.deleteAll();

        
        u1 = new Utilisateur();
        u1.setNomU("Alice");
        u1.setPrenomU("Test");
        u1.setEmailU("alice@test.com");
        u1.setMpU("1234");
        u1 = utilisateurRepository.save(u1);

        u2 = new Utilisateur();
        u2.setNomU("Bob");
        u2.setPrenomU("Test");
        u2.setEmailU("bob@test.com");
        u2.setMpU("5678");
        u2 = utilisateurRepository.save(u2);

        
        Message msg = new Message();
        msg.setEnvoyeur(u1);
        msg.setRecepteur(u2);
        msg.setText("Hello Bob!");
        msg.setTemps(new Date());
        messageRepository.save(msg);
    }

    @Test
    void testAfficherDiscussion() throws Exception {
        mockMvc.perform(get("/messages/" + u2.getIdU())
                .sessionAttr("utilisateur", u1))
                .andExpect(status().isOk())
                .andExpect(view().name("messaging"))
                .andExpect(model().attributeExists("messages"))
                .andExpect(model().attributeExists("amis"))
                .andExpect(model().attributeExists("recpteur"));
    }

    @Test
    void testEnvoyerMessage() throws Exception {
        mockMvc.perform(post("/messages/envoyer")
                .param("idRecpteur", String.valueOf(u2.getIdU()))
                .param("contenu", "Salut Bob !")
                .sessionAttr("utilisateur", u1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/messages/" + u2.getIdU()));
    }

    @Test
    void testEnvoyerMessageSansSession() throws Exception {
        mockMvc.perform(post("/messages/envoyer")
                .param("idRecpteur", String.valueOf(u2.getIdU()))
                .param("contenu", "Message sans session"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/messages/" + u2.getIdU()));
    }
}
