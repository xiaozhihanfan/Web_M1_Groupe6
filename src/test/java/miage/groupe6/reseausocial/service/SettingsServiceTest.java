package miage.groupe6.reseausocial.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;
import miage.groupe6.reseausocial.model.jpa.service.SettingsService;

@ExtendWith(MockitoExtension.class)
class SettingsServiceTest {

    @Mock
    private UtilisateurRepository ur;

    @InjectMocks
    private SettingsService settingsService;

    @Test
    @DisplayName("getUtilisateurById retourne Optional non vide quand trouvé")
    void testGetUtilisateurByIdFound() {
        Utilisateur user = new Utilisateur();
        user.setIdU(1L);
        when(ur.findById(1L)).thenReturn(Optional.of(user));

        Optional<Utilisateur> opt = settingsService.getUtilisateurById(1L);
        assertTrue(opt.isPresent());
        assertSame(user, opt.get());

        verify(ur).findById(1L);
    }

    @Test
    @DisplayName("getUtilisateurById retourne Optional vide quand non trouvé")
    void testGetUtilisateurByIdNotFound() {
        when(ur.findById(2L)).thenReturn(Optional.empty());

        Optional<Utilisateur> opt = settingsService.getUtilisateurById(2L);
        assertFalse(opt.isPresent());

        verify(ur).findById(2L);
    }

    @Test
    @DisplayName("updateUtilisateur met à jour et renvoie l'utilisateur")
    void testUpdateUtilisateurSuccess() {
        Utilisateur existing = new Utilisateur();
        existing.setIdU(3L);
        existing.setNomU("OldNom");
        existing.setPrenomU("OldPrenom");
        existing.setEmailU("old@example.com");
        existing.setDescriptionU("old desc");
        existing.setBirthday(null);
        existing.setTelephone("000");
        existing.setIne("OLD");
        existing.setUniversite("U1");
        existing.setVille("V1");

        Utilisateur newData = new Utilisateur();
        newData.setNomU("NewNom");
        newData.setPrenomU("NewPrenom");
        newData.setEmailU("new@example.com");
        newData.setDescriptionU("new desc");
        newData.setBirthday(java.time.LocalDate.of(2000, 1, 1));
        newData.setTelephone("111");
        newData.setIne("NEW");
        newData.setUniversite("U2");
        newData.setVille("V2");

        when(ur.findById(3L)).thenReturn(Optional.of(existing));
        when(ur.save(existing)).thenReturn(existing);

        Utilisateur updated = settingsService.updateUtilisateur(3L, newData);

        // Vérifier que les champs ont été mis à jour
        assertEquals("NewNom", updated.getNomU());
        assertEquals("NewPrenom", updated.getPrenomU());
        assertEquals("new@example.com", updated.getEmailU());
        assertEquals("new desc", updated.getDescriptionU());
        assertEquals(java.time.LocalDate.of(2000, 1, 1), updated.getBirthday());
        assertEquals("111", updated.getTelephone());
        assertEquals("NEW", updated.getIne());
        assertEquals("U2", updated.getUniversite());
        assertEquals("V2", updated.getVille());

        verify(ur).findById(3L);
        verify(ur).save(existing);
    }


    @Test
    @DisplayName("updatePassword réussit quand mot de passe actuel correct")
    void testUpdatePasswordSuccess() {
        Utilisateur user = new Utilisateur();
        user.setIdU(5L);
        user.setMpU("secret");

        when(ur.findById(5L)).thenReturn(Optional.of(user));
        when(ur.save(user)).thenReturn(user);

        // Ne doit pas lancer d'exception
        settingsService.updatePassword(5L, "secret", "newpass");

        // Vérifier que le mot de passe a bien été changé
        assertEquals("newpass", user.getMpU());
        verify(ur).findById(5L);
        verify(ur).save(user);
    }



    @Test
    @DisplayName("updatePassword lève exception si mot de passe actuel incorrect")
    void testUpdatePasswordIncorrect() {
        Utilisateur user = new Utilisateur();
        user.setIdU(7L);
        user.setMpU("correct");

        when(ur.findById(7L)).thenReturn(Optional.of(user));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            settingsService.updatePassword(7L, "wrong", "new")
        );
        assertEquals("Mot de passe actuel incorrect", ex.getMessage());

        verify(ur).findById(7L);
        verify(ur, never()).save(any());
    }

    @Test
    @DisplayName("updateAvatarUrl met à jour l'avatar et sauvegarde")
    void testUpdateAvatarUrlSuccess() {
        Utilisateur user = new Utilisateur();
        user.setIdU(8L);
        user.setAvatarU("/old.png");

        when(ur.findById(8L)).thenReturn(Optional.of(user));
        when(ur.save(user)).thenReturn(user);

        String url = "data:image/png;base64,AAA";
        settingsService.updateAvatarUrl(8L, url);

        assertEquals(url, user.getAvatarU());
        verify(ur).findById(8L);
        verify(ur).save(user);
    }

    @Test
    @DisplayName("updateUtilisateur lève exception si utilisateur introuvable")
    void testUpdateUtilisateurNotFound() {
        when(ur.findById(4L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> settingsService.updateUtilisateur(4L, new Utilisateur()));
        assertTrue(ex.getMessage().contains("Utilisateur non trouvé avec id: 4"));

        verify(ur).findById(4L);
        verify(ur, never()).save(any());
    }

    @Test
    @DisplayName("updatePassword lève exception si utilisateur introuvable")
    void testUpdatePasswordUserNotFound() {
        when(ur.findById(6L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> settingsService.updatePassword(6L, "any", "new"));
        assertTrue(ex.getMessage().contains("Utilisateur non trouvé avec id: 6"));

        verify(ur).findById(6L);
        verify(ur, never()).save(any());
    }

    @Test
    @DisplayName("updateAvatarUrl lève exception si utilisateur introuvable")
    void testUpdateAvatarUrlNotFound() {
        when(ur.findById(9L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> settingsService.updateAvatarUrl(9L, "url"));
        assertTrue(ex.getMessage().contains("Utilisateur non trouvé avec id: 9"));

        verify(ur).findById(9L);
        verify(ur, never()).save(any());
    }

}
