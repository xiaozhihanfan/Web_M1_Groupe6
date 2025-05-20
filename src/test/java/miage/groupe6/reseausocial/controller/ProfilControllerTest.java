package miage.groupe6.reseausocial.controller;

import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.ProfilService;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
class ProfilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfilService profilService;

    @MockBean
    private PostService postService;

    @MockBean
    private RelationAmisService relationAmisService;

    @Test
    void afficherProfil_existingUser_rendersProfilePage() throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdU(7L);
        utilisateur.setNomU("Alice");
        utilisateur.setPrenomU("Martin");
        utilisateur.setEmailU("alice.martin@example.com");
        utilisateur.setBirthday(LocalDate.of(1992, 3, 1));
        utilisateur.setTelephone("0712345678");

        when(profilService.getProfileById(7L))
            .thenReturn(utilisateur);

        mockMvc.perform(get("/utilisateurs/7/profile-about"))
               .andExpect(status().isOk())
               .andExpect(view().name("my-profile-about"))
               .andExpect(model().attribute("utilisateur", utilisateur));

        verify(profilService).getProfileById(7L);
    }

    @Test
    void afficherProfil_userNotFound_returnsServerError() throws Exception {
        when(profilService.getProfileById(99L))
            .thenThrow(new RuntimeException("Utilisateur non trouvé : 99"));

        mockMvc.perform(get("/utilisateurs/99/profile-about"))
               .andExpect(status().is5xxServerError());

        verify(profilService).getProfileById(99L);
    }

        @Test
    void afficherProfilePost_existingUser_rendersProfilePostPageWithPosts() throws Exception {
        // Préparation
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdU(1L);
        // … complétez le profil …
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date1 = sdf.parse("2025-05-01T09:00:00");
        Post p1 = new Post();
        p1.setContenuP("Premier post");
        p1.setDateP(date1);

        Date date2 = sdf.parse("2025-05-02T15:30:00");
        Post p2 = new Post();
        p2.setContenuP("Deuxième post");
        p2.setDateP(date2);

        when(profilService.getProfileById(1L)).thenReturn(utilisateur);
        when(postService.findByAuteurOrderByDateDesc(utilisateur)).thenReturn(List.of(p2, p1));

        // Exécution & Vérification
        mockMvc.perform(get("/utilisateurs/1/profile-post"))
               .andExpect(status().isOk())
               .andExpect(view().name("my-profile-post"))
               .andExpect(model().attribute("utilisateur", utilisateur))
               .andExpect(model().attribute("posts", List.of(p2, p1)));

        verify(profilService).getProfileById(1L);
        verify(postService).findByAuteurOrderByDateDesc(utilisateur);
    }

    @Test
    void afficherProfilePost_userNotFound_returnsServerError() throws Exception {
        when(profilService.getProfileById(99L))
            .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé : 99"));

        mockMvc.perform(get("/utilisateurs/99/profile-post"))
               .andExpect(status().is5xxServerError());

        verify(profilService).getProfileById(99L);
        verifyNoInteractions(postService);
    }

    /**
     * Vérifie que GET /utilisateurs/{id}/profile-connections
     * affiche correctement la page avec la liste des amis.
     */
    @Test
    void afficherProfileConnections_avecAmis_rendersPage() throws Exception {
        Utilisateur alice = new Utilisateur();
        alice.setIdU(5L);
        alice.setNomU("Alice");
        alice.setPrenomU("Dupont");

        Utilisateur ami1 = new Utilisateur();
        ami1.setIdU(6L);
        ami1.setNomU("Bob");
        ami1.setPrenomU("Martin");

        Utilisateur ami2 = new Utilisateur();
        ami2.setIdU(7L);
        ami2.setNomU("Charlie");
        ami2.setPrenomU("Durand");

        when(profilService.getProfileById(5L)).thenReturn(alice);
        when(relationAmisService.listerAmis(alice)).thenReturn(List.of(ami1, ami2));

        mockMvc.perform(get("/utilisateurs/5/profile-connections"))
               .andExpect(status().isOk())
               .andExpect(view().name("my-profile-connections"))
               .andExpect(model().attribute("utilisateur", alice))
               .andExpect(model().attribute("amis", List.of(ami1, ami2)));

        verify(profilService).getProfileById(5L);
        verify(relationAmisService).listerAmis(alice);
    }

    /**
     * Vérifie que GET /utilisateurs/{id}/profile-connections
     * renvoie une erreur 500 si le service jette une exception.
     */
    @Test
    void afficherProfileConnections_utilisateurIntrouvable_returnsServerError() throws Exception {
        when(profilService.getProfileById(99L))
            .thenThrow(new RuntimeException("Utilisateur introuvable : 99"));

        mockMvc.perform(get("/utilisateurs/99/profile-connections"))
               .andExpect(status().is5xxServerError());

        verify(profilService).getProfileById(99L);
        verifyNoInteractions(relationAmisService);
    }

    /**
     * 测试 GET /utilisateurs/{id}/profile-events
     * 应渲染 my-profile-events 视图，并且把三类列表都放到 model。
     */
    @Test
    void afficherProfileEvents_rendersEventsPage() throws Exception {
        // --- 新增：先构造一个“完整”的 Utilisateur 给模板用 ---
        Utilisateur user = new Utilisateur();
        user.setIdU(3L);
        user.setPrenomU("Test");
        user.setNomU("User");
        user.setAvatarU("assets/images/avatar/01.jpg");
        user.setUniversite("Université de Test");
        // …如果模板里还引用了别的字段（emailU、telephone…），也一并 setXXX …

        // 模拟 profilService.getProfileById(...) 
        when(profilService.getProfileById(3L)).thenReturn(user);
        // --------------------------------------------------------------

        // 三类活动列表照旧模拟
        List<Evenement> crees      = List.of();
        List<Evenement> inscrits   = List.of();
        List<Evenement> interesses = List.of();

        when(profilService.getEvenementsCrees(3L)).thenReturn(crees);
        when(profilService.getEvenementsInscrits(3L)).thenReturn(inscrits);
        when(profilService.getEvenementsInteresses(3L)).thenReturn(interesses);

        mockMvc.perform(get("/utilisateurs/{id}/profile-events", 3L)
                .sessionAttr("utilisateur", user))
            .andExpect(status().isOk())
            .andExpect(view().name("my-profile-events"))
            .andExpect(model().attribute("utilisateur", user))
            .andExpect(model().attribute("evenementsCrees", crees))
            .andExpect(model().attribute("evenementsInscrits", inscrits))
            .andExpect(model().attribute("evenementsIntereses", interesses));

        verify(profilService).getProfileById(3L);
        verify(profilService).getEvenementsCrees(3L);
        verify(profilService).getEvenementsInscrits(3L);
        verify(profilService).getEvenementsInteresses(3L);
    }

}

