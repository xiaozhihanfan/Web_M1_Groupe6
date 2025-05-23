package miage.groupe6.reseausocial.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import miage.groupe6.reseausocial.model.entity.ActionEvenement;
import miage.groupe6.reseausocial.model.entity.ActionEvenementId;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Tests unitaires pour la classe {@link ActionEvenement}.
 */
class ActionEvenementTest {

    @Test
    @DisplayName("Test du constructeur par défaut et des getters/setters")
    void testDefaultConstructorAndAccessors() {
        ActionEvenement ae = new ActionEvenement();
        assertNotNull(ae.getId(), "L'ID composite ne doit pas être null après le constructeur par défaut");
        assertNull(ae.getDateActionEvenemnt(), "La date doit être null par défaut");
        assertNull(ae.getStatut(), "Le statut doit être null par défaut");
        assertNull(ae.getUtilisateur(), "L'utilisateur doit être null par défaut");
        assertNull(ae.getEvenement(), "L'événement doit être null par défaut");

        // Préparation des valeurs
        Date now = new Date();
        StatutActionEvenement statut = StatutActionEvenement.INSCRIRE;
        Utilisateur user = mock(Utilisateur.class);
        Evenement event = mock(Evenement.class);

        // Test des setters
        ae.setDateActionEvenemnt(now);
        ae.setStatut(statut);
        ae.setUtilisateur(user);
        ae.setEvenement(event);

        // Vérification via getters
        assertEquals(now, ae.getDateActionEvenemnt(), "Le getter doit retourner la même date que le setter");
        assertEquals(statut, ae.getStatut(), "Le getter doit retourner le même statut que le setter");
        assertSame(user, ae.getUtilisateur(), "Le getter doit retourner la même instance Utilisateur");
        assertSame(event, ae.getEvenement(), "Le getter doit retourner la même instance Evenement");

        // Test du setter/getter de l'ID composite
        ActionEvenementId customId = new ActionEvenementId(123L, 456L);
        ae.setId(customId);
        assertSame(customId, ae.getId(), "Le getter d'ID composite doit retourner l'instance définie");
    }

    @Test
    @DisplayName("Test du constructeur principal et génération automatique de l'ID composite")
    void testAllArgsConstructor() {
        // Stub Utilisateur et Evenement pour renvoyer des IDs connus
        Utilisateur user = mock(Utilisateur.class);
        when(user.getIdU()).thenReturn(10L);
        Evenement event = mock(Evenement.class);
        when(event.getIdE()).thenReturn(20L);

        Date actionDate = new Date(1_620_000_000_000L);  // date fixe pour test
        StatutActionEvenement statut = StatutActionEvenement.INTERESSER;

        // Appel du constructeur principal
        ActionEvenement ae = new ActionEvenement(actionDate, statut, user, event);

        // Vérification du mapping des champs
        assertEquals(actionDate, ae.getDateActionEvenemnt(), "La date doit être celle passée au constructeur");
        assertEquals(statut, ae.getStatut(), "Le statut doit être celui passé au constructeur");
        assertSame(user, ae.getUtilisateur(), "L'utilisateur doit être celui passé au constructeur");
        assertSame(event, ae.getEvenement(), "L'événement doit être celui passé au constructeur");

        // Vérification de l'ID composite généré
        ActionEvenementId id = ae.getId();
        assertNotNull(id, "L'ID composite ne doit pas être null");
        assertEquals(10L, id.getIdU(), "Le composant idU de l'ID composite doit provenir de user.getIdU()");
        assertEquals(20L, id.getIdE(), "Le composant idE de l'ID composite doit provenir de event.getIdE()");
    }

}

