package miage.groupe6.reseausocial;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;
import miage.groupe6.reseausocial.model.jpa.service.SettingsService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SettingsServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private SettingsService settingsService;

    private Utilisateur utilisateur;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        utilisateur = new Utilisateur();
        utilisateur.setIdU(1L);
        utilisateur.setNomU("John");
        utilisateur.setPrenomU("Doe");
        utilisateur.setEmailU("john.doe@example.com");
        utilisateur.setMpU("oldpass");
        utilisateur.setBirthday(LocalDate.of(1990, 1, 1));
        utilisateur.setTelephone("1234567890");
        utilisateur.setUserName("johndoe");
    }

    @Test
    void testUpdateUtilisateur_success() {
        Utilisateur newData = new Utilisateur();
        newData.setNomU("Jane");
        newData.setPrenomU("Smith");
        newData.setEmailU("jane.smith@example.com");
        newData.setAvatarU("avatar.png");
        newData.setDescriptionU("Nouvelle description");
        newData.setBirthday(LocalDate.of(1995, 5, 5));
        newData.setTelephone("0987654321");
        newData.setUserName("janesmith");

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);

        Utilisateur updated = settingsService.updateUtilisateur(1L, newData);

        assertEquals("Jane", updated.getNomU());
        assertEquals("Smith", updated.getPrenomU());
        assertEquals("janesmith", updated.getUserName());
    }

    @Test
    void testUpdateUtilisateur_userNotFound() {
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> settingsService.updateUtilisateur(1L, utilisateur));
    }

    @Test
    void testUpdatePassword_success() {
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));

        settingsService.updatePassword(1L, "oldpass", "newpass123");

        verify(utilisateurRepository).save(argThat(u -> u.getMpU().equals("newpass123")));
    }

    @Test
    void testUpdatePassword_wrongCurrentPassword() {
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));
        assertThrows(RuntimeException.class, () -> settingsService.updatePassword(1L, "wrongpass", "newpass"));
    }

    @Test
    void testUpdatePassword_userNotFound() {
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> settingsService.updatePassword(1L, "oldpass", "newpass"));
    }
}