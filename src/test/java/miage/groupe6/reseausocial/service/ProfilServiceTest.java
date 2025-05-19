package miage.groupe6.reseausocial.service;


import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;
import miage.groupe6.reseausocial.model.jpa.service.ProfilService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class ProfilServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private ProfilService profilService;

    private Utilisateur utilisateur;

    @BeforeEach
    void setUp() {
        utilisateur = new Utilisateur();
        utilisateur.setIdU(42L);
        utilisateur.setNomU("Jean");
        utilisateur.setPrenomU("Dupont");
        utilisateur.setEmailU("jean.dupont@example.com");
        utilisateur.setBirthday(LocalDate.of(1985, 7, 15));
        utilisateur.setTelephone("0612345678");
    }

    @Test
    void getProfileById_existingUser_returnsUtilisateur() {
        when(utilisateurRepository.findById(42L))
            .thenReturn(Optional.of(utilisateur));

        Utilisateur result = profilService.getProfileById(42L);

        assertNotNull(result);
        assertEquals("Jean", result.getNomU());
        assertEquals("Dupont", result.getPrenomU());
        verify(utilisateurRepository).findById(42L);
    }

    @Test
    void getProfileById_notFound_throwsException() {
        when(utilisateurRepository.findById(42L))
            .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            profilService.getProfileById(42L)
        );
        assertTrue(ex.getMessage().contains("Utilisateur non trouvé"));
        verify(utilisateurRepository).findById(42L);
    }
}
