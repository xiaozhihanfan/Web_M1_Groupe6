package miage.groupe6.reseausocial.controller;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.RelationAmis;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;

/**
 * Test de la classe GlobalModelAttribute qui injecte des données globales dans les vues.
 */

public class GobalModelattributeTest {
    @Mock
    private RelationAmisService relationAmisService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private GlobalModelAttribute globalModelAttribute;

    private Utilisateur utilisateur;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        utilisateur = new Utilisateur();
        utilisateur.setIdU(1L);
    }

    /**
     * Vérifie que les demandes d'amis sont retournées si l'utilisateur est en session.
     */
    @Test
    void testPopulateDemandesAmis_utilisateurPresent() {
        when(session.getAttribute("utilisateur")).thenReturn(utilisateur);
        RelationAmis demande = new RelationAmis();
        when(relationAmisService.getDemandesRecues(utilisateur)).thenReturn(List.of(demande));

        List<RelationAmis> result = globalModelAttribute.populateDemandesAmis(session);

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(demande);
    }

    /**
     * Vérifie que la méthode retourne une liste vide si aucun utilisateur en session.
     */
    @Test
    void testPopulateDemandesAmis_utilisateurAbsent() {
        when(session.getAttribute("utilisateur")).thenReturn(null);

        List<RelationAmis> result = globalModelAttribute.populateDemandesAmis(session);

        assertThat(result).isEmpty();
    }

    /**
     * Vérifie que l'attribut utilisateur de session est correctement retourné.
     */
    @Test
    void testSessionUser() {
        when(session.getAttribute("utilisateur")).thenReturn(utilisateur);

        Utilisateur result = globalModelAttribute.sessionUser(session);

        assertThat(result).isEqualTo(utilisateur);
    }

    /**
     * Vérifie que la méthode retourne null si aucun utilisateur dans la session.
     */
    @Test
    void testSessionUser_absent() {
        when(session.getAttribute("utilisateur")).thenReturn(null);

        Utilisateur result = globalModelAttribute.sessionUser(session);

        assertThat(result).isNull();
    }
}
