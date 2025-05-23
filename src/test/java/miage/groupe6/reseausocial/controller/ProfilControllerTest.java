package miage.groupe6.reseausocial.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.ProfilService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;

/**
 * Tests unitaires pour {@link ProfilController}.
 */
@WebMvcTest(ProfilController.class)
class ProfilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfilService profilService;
    @MockBean
    private PostService postService;
    @MockBean
    private RelationAmisService relationAmisService;

    private MockHttpSession sessionWithUser(long userId) {
        Utilisateur user = new Utilisateur();
        user.setIdU(userId);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("utilisateur", user);
        return session;
    }

    @Test
    @DisplayName("afficherProfil: succès et propriétaire détecté")
    void testAfficherProfilOwner() throws Exception {
        long id = 99L;
        Utilisateur profilUser = new Utilisateur();
        profilUser.setIdU(id);

        when(profilService.getProfileById(id)).thenReturn(profilUser);

        mockMvc.perform(get("/utilisateurs/{id}/profile-about", id)
                .session(sessionWithUser(id)))
            .andExpect(status().isOk())
            .andExpect(view().name("my-profile-about"))
            .andExpect(model().attribute("estProprietaire", true))
            .andExpect(model().attribute("profilUtilisateur", profilUser));
    }

    @Test
    @DisplayName("afficherProfil: erreur interne renvoie 500")
    void testAfficherProfilError() throws Exception {
        long id = 5L;
        when(profilService.getProfileById(id)).thenThrow(new RuntimeException("erreur"));

        mockMvc.perform(get("/utilisateurs/{id}/profile-about", id)
                .session(new MockHttpSession()))
            .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("afficherProfilePost: affichage correct avec posts")
    void testAfficherProfilePost() throws Exception {
        long id = 7L;
        Utilisateur profilUser = new Utilisateur();
        profilUser.setIdU(id);
        List<Post> posts = Arrays.asList(new Post(), new Post());

        when(profilService.getProfileById(id)).thenReturn(profilUser);
        when(postService.findByAuteurOrderByDateDesc(profilUser)).thenReturn(posts);

        mockMvc.perform(get("/utilisateurs/{id}/profile-post", id)
                .session(sessionWithUser(0L)))  // session user is 0, so not owner
            .andExpect(status().isOk())
            .andExpect(view().name("my-profile-post"))
            .andExpect(model().attribute("estProprietaire", false))
            .andExpect(model().attribute("profilUtilisateur", profilUser))
            .andExpect(model().attribute("posts", posts));
    }

    @Test
    @DisplayName("afficherProfileConnections: affichage correct avec amis")
    void testAfficherProfileConnections() throws Exception {
        long id = 11L;
        Utilisateur profilUser = new Utilisateur();
        profilUser.setIdU(id);
        List<Utilisateur> amis = Collections.singletonList(new Utilisateur());

        when(profilService.getProfileById(id)).thenReturn(profilUser);
        when(relationAmisService.listerAmis(profilUser)).thenReturn(amis);

        mockMvc.perform(get("/utilisateurs/{id}/profile-connections", id)
                .session(sessionWithUser(id)))
            .andExpect(status().isOk())
            .andExpect(view().name("my-profile-connections"))
            .andExpect(model().attribute("estProprietaire", true))
            .andExpect(model().attribute("profilUtilisateur", profilUser))
            .andExpect(model().attribute("amis", amis));
    }

    @Test
    @DisplayName("afficherProfileEvents: affichage correct des listes d'événements")
    void testAfficherProfileEvents() throws Exception {
        long id = 21L;
        Utilisateur profilUser = new Utilisateur();
        profilUser.setIdU(id);
        List<Evenement> crees = Collections.singletonList(new Evenement());
        List<Evenement> inscrits = Collections.emptyList();
        List<Evenement> interesses = Arrays.asList(new Evenement(), new Evenement());

        when(profilService.getProfileById(id)).thenReturn(profilUser);
        when(profilService.getEvenementsCrees(id)).thenReturn(crees);
        when(profilService.getEvenementsInscrits(id)).thenReturn(inscrits);
        when(profilService.getEvenementsInteresses(id)).thenReturn(interesses);

        mockMvc.perform(get("/utilisateurs/{id}/profile-events", id)
                .session(sessionWithUser(999L)))  // not owner
            .andExpect(status().isOk())
            .andExpect(view().name("my-profile-events"))
            .andExpect(model().attribute("estProprietaire", false))
            .andExpect(model().attribute("profilUtilisateur", profilUser))
            .andExpect(model().attribute("evenementsCrees", crees))
            .andExpect(model().attribute("evenementsInscrits", inscrits))
            .andExpect(model().attribute("evenementsIntereses", interesses));
    }

    @Test
    @DisplayName("afficherProfileGroups: affichage correct des groupes et nouvel objet Groupe")
    void testAfficherProfileGroups() throws Exception {
        long id = 33L;
        Utilisateur profilUser = new Utilisateur();
        profilUser.setIdU(id);
        List<Groupe> admin = Collections.singletonList(new Groupe());
        List<Groupe> membre = Collections.singletonList(new Groupe());

        when(profilService.getProfileById(id)).thenReturn(profilUser);
        when(profilService.getGroupesAdmin(id)).thenReturn(admin);
        when(profilService.getGroupesMembre(id)).thenReturn(membre);

        mockMvc.perform(get("/utilisateurs/{id}/profile-groups", id)
                .session(sessionWithUser(id)))
            .andExpect(status().isOk())
            .andExpect(view().name("my-profile-groups"))
            .andExpect(model().attribute("estProprietaire", true))
            .andExpect(model().attribute("profilUtilisateur", profilUser))
            .andExpect(model().attribute("groupesAdmin", admin))
            .andExpect(model().attribute("groupesMembre", membre))
            .andExpect(model().attributeExists("groupe"))
            .andExpect(model().attribute("groupe", instanceOf(Groupe.class)));
    }

    @Test
    @DisplayName("afficherProfilePost: erreur interne quand postService échoue")
    void testAfficherProfilePostErrorOnPostsService() throws Exception {
        long id = 123L;
        Utilisateur profilUser = new Utilisateur();
        profilUser.setIdU(id);

        // profilService retourne un utilisateur valide
        when(profilService.getProfileById(id)).thenReturn(profilUser);
        // postService lève une RuntimeException
        when(postService.findByAuteurOrderByDateDesc(profilUser))
            .thenThrow(new RuntimeException("service down"));

        mockMvc.perform(get("/utilisateurs/{id}/profile-post", id)
                .session(sessionWithUser(id)))
            .andExpect(status().isInternalServerError());
    }
}

