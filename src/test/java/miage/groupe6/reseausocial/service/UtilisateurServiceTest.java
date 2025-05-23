package miage.groupe6.reseausocial.service;

import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository ur;

    @InjectMocks
    private UtilisateurService service;

    private Utilisateur user;

    @BeforeEach
    void setUp() {
        user = new Utilisateur();
        user.setIdU(123L);
        user.setEmailU("test@example.com");
        user.setMpU("password");
        user.setNomU("Doe");
        user.setPrenomU("John");
        user.setUniversite("MIAGE");
        user.setVille("Paris");
        user.setBirthday(LocalDate.of(2000, 1, 15));
        user.setIne("INE123");
        user.setDescriptionU("desc");
        user.setAvatarU("/img.png");
    }

    @Test
    void testSaveDelegatesToRepository() {
        when(ur.save(user)).thenReturn(user);

        Utilisateur returned = service.save(user);

        assertSame(user, returned);
        verify(ur, times(1)).save(user);
    }

    @Test
    void testVerifierSignUpEmailExists() {
        when(ur.findByEmailU("exists@example.com")).thenReturn(Optional.of(new Utilisateur()));

        String result = service.verifierSignUp(
            "exists@example.com", "pass", "pass",
            "Nom", "Prenom", "Univ", "Ville",
            LocalDate.of(1990, 5, 20), "INE001"
        );

        assertEquals("Cet email existe déjà !", result);
        verify(ur, never()).save(any());
    }

    @Test
    void testVerifierSignUpPasswordMismatch() {
        when(ur.findByEmailU("new@example.com")).thenReturn(Optional.empty());

        String result = service.verifierSignUp(
            "new@example.com", "pass1", "pass2",
            "Nom", "Prenom", "Univ", "Ville",
            LocalDate.of(1990, 5, 20), "INE002"
        );

        assertEquals("Les deux mots de passe ne sont pas identiques !", result);
        verify(ur, never()).save(any());
    }

    @Test
    void testVerifierSignUpSuccess() {
        when(ur.findByEmailU("unique@example.com")).thenReturn(Optional.empty());
        ArgumentCaptor<Utilisateur> captor = ArgumentCaptor.forClass(Utilisateur.class);
        // stub save to just return its argument
        when(ur.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        String result = service.verifierSignUp(
            "unique@example.com", "pwd", "pwd",
            "NomU", "PrenomU", "UnivU", "VilleU",
            LocalDate.of(1985, 12, 1), "INE999"
        );

        assertEquals("succès", result);
        verify(ur, times(1)).save(captor.capture());

        Utilisateur saved = captor.getValue();
        assertEquals("unique@example.com", saved.getEmailU());
        assertEquals("pwd", saved.getMpU());
        assertEquals("NomU", saved.getNomU());
        assertEquals("PrenomU", saved.getPrenomU());
        assertEquals("UnivU", saved.getUniversite());
        assertEquals("VilleU", saved.getVille());
        assertEquals(LocalDate.of(1985, 12, 1), saved.getBirthday());
        assertEquals("INE999", saved.getIne());
        assertEquals(LocalDate.now().toString(), saved.getDateInscription());
    }

    @Test
    void testVerifierSignInNotFound() {
        when(ur.findByEmailU("missing@example.com")).thenReturn(Optional.empty());

        Utilisateur out = service.verifierSignIn("missing@example.com", "any");

        assertNull(out);
    }

    @Test
    void testVerifierSignInWrongPassword() {
        Utilisateur u = new Utilisateur();
        u.setEmailU("user@example.com");
        u.setMpU("correct");
        when(ur.findByEmailU("user@example.com")).thenReturn(Optional.of(u));

        Utilisateur out = service.verifierSignIn("user@example.com", "wrong");

        assertNull(out);
    }

    @Test
    void testVerifierSignInSetsDefaultsWhenFieldsNullOrEmpty() {
        Utilisateur u = new Utilisateur();
        u.setEmailU("u@example.com");
        u.setMpU("secret");
        // nomU & prenomU null by default
        // descriptionU & avatarU null by default
        when(ur.findByEmailU("u@example.com")).thenReturn(Optional.of(u));

        Utilisateur out = service.verifierSignIn("u@example.com", "secret");

        assertNotNull(out);
        assertEquals("", out.getNomU());
        assertEquals("", out.getPrenomU());
        assertEquals("", out.getDescriptionU());
        assertEquals("/assets/images/avatar/placeholder.jpg", out.getAvatarU());
    }

    @Test
    void testVerifierSignInKeepsExistingValues() {
        Utilisateur u = new Utilisateur();
        u.setEmailU("kept@example.com");
        u.setMpU("pass");
        u.setNomU("Keep");
        u.setPrenomU("User");
        u.setDescriptionU("my desc");
        u.setAvatarU("/keep.png");
        when(ur.findByEmailU("kept@example.com")).thenReturn(Optional.of(u));

        Utilisateur out = service.verifierSignIn("kept@example.com", "pass");

        assertNotNull(out);
        assertEquals("Keep", out.getNomU());
        assertEquals("User", out.getPrenomU());
        assertEquals("my desc", out.getDescriptionU());
        assertEquals("/keep.png", out.getAvatarU());
    }

    @Test
    void testGetUtilisateurByIdFound() {
        when(ur.findById(123L)).thenReturn(Optional.of(user));

        Optional<Utilisateur> opt = service.getUtilisateurById(123L);

        assertTrue(opt.isPresent());
        assertSame(user, opt.get());
    }

    @Test
    void testGetUtilisateurByIdNotFound() {
        when(ur.findById(999L)).thenReturn(Optional.empty());

        Optional<Utilisateur> opt = service.getUtilisateurById(999L);

        assertTrue(opt.isEmpty());
    }

    @Test
    void testRechercherParNomOuPrenomDelegates() {
        List<Utilisateur> list = Collections.singletonList(user);
        String q = "doe";
        when(ur.findByNomUContainingIgnoreCaseOrPrenomUContainingIgnoreCase(q, q))
            .thenReturn(list);

        List<Utilisateur> out = service.rechercherParNomOuPrenom(q);

        assertEquals(list, out);
        verify(ur).findByNomUContainingIgnoreCaseOrPrenomUContainingIgnoreCase(q, q);
    }

    @Test
    void testRechercherParEmailFound() {
        when(ur.findByEmailU("found@example.com")).thenReturn(Optional.of(user));

        Utilisateur out = service.rechercherParEmail("found@example.com");

        assertSame(user, out);
    }

    @Test
    void testRechercherParEmailNotFound() {
        when(ur.findByEmailU("none@example.com")).thenReturn(Optional.empty());

        Utilisateur out = service.rechercherParEmail("none@example.com");

        assertNull(out);
    }
}

