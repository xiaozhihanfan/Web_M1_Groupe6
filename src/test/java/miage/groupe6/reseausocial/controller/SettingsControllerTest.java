package miage.groupe6.reseausocial.controller;

import static org.hamcrest.Matchers.containsString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;

@SpringBootTest
@AutoConfigureMockMvc
class SettingsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private Utilisateur utilisateur;

    @BeforeEach
    void setUp() {
        utilisateurRepository.deleteAll(); // 清空以防数据污染
        utilisateur = new Utilisateur();
        utilisateur.setNomU("TestNom");
        utilisateur.setPrenomU("TestPrenom");
        utilisateur.setEmailU("test@email.com");
        utilisateur.setMpU("ancienmdp");
        utilisateur = utilisateurRepository.save(utilisateur);
    }

    @Test
    void testAfficherFormulaireModification() throws Exception {
        mockMvc.perform(get("/utilisateurs/" + utilisateur.getIdU() + "/modifier-info"))
                .andExpect(status().isOk())
                .andExpect(view().name("settingsInfo"))
                .andExpect(model().attributeExists("utilisateur"))
                .andExpect(model().attributeExists("passwordForm"));
    }

    @Test
    void testModifierUtilisateur_Success() throws Exception {
        mockMvc.perform(post("/utilisateurs/" + utilisateur.getIdU() + "/modifier-info")
                        .param("nom", "NouveauNom")
                        .param("prenom", "NouveauPrenom")
                        .param("email", "new@email.com")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("updateSuccess", true))
                .andExpect(redirectedUrl("/utilisateurs/" + utilisateur.getIdU() + "/modifier-info"));
    }

    @Test
    void testAfficherPageModificationMotDePasse() throws Exception {
        mockMvc.perform(get("/utilisateurs/" + utilisateur.getIdU() + "/modifier-mdp"))
                .andExpect(status().isOk())
                .andExpect(view().name("settingsMdp"))
                .andExpect(model().attributeExists("utilisateur"))
                .andExpect(model().attributeExists("passwordForm"));
    }

    @Test
    void testModifierMotDePasse_Success() throws Exception {
        // 假设 settingsService 会成功更新密码（你可以用 H2 配置一个简单密码验证逻辑）
        mockMvc.perform(post("/utilisateurs/" + utilisateur.getIdU() + "/modifier-mdp")
                        .param("currentPassword", "ancienmdp")
                        .param("newPassword", "nouveaumdp")
                        .param("confirmPassword", "nouveaumdp")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("passwordSuccess", containsString("succès")))
                .andExpect(redirectedUrl("/utilisateurs/" + utilisateur.getIdU() + "/modifier-mdp"));
    }

    @Test
    void testModifierMotDePasse_Mismatch() throws Exception {
        mockMvc.perform(post("/utilisateurs/" + utilisateur.getIdU() + "/modifier-mdp")
                        .param("currentPassword", "ancienmdp")
                        .param("newPassword", "123456")
                        .param("confirmPassword", "654321")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("passwordError", containsString("ne correspondent")))
                .andExpect(redirectedUrl("/utilisateurs/" + utilisateur.getIdU() + "/modifier"));
    }
}
