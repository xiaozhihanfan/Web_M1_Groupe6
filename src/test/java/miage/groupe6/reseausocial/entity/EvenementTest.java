package miage.groupe6.reseausocial.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import miage.groupe6.reseausocial.model.entity.ActionEvenement;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Tests unitaires pour la classe {@link Evenement}.
 */
class EvenementTest {

    @Test
    @DisplayName("Constructeur par défaut initialise correctement les collections et les autres champs à null")
    void testDefaultConstructor() {
        Evenement evt = new Evenement();
        // Les champs simples doivent être null
        assertNull(evt.getIdE(), "idE doit être null après le constructeur par défaut");
        assertNull(evt.getTitre(), "titre doit être null après le constructeur par défaut");
        assertNull(evt.getLieu(), "lieu doit être null après le constructeur par défaut");
        assertNull(evt.getAvatarE(), "avatarE doit être null après le constructeur par défaut");
        assertNull(evt.getDescriptionE(), "descriptionE doit être null après le constructeur par défaut");
        assertNull(evt.getDateDebut(), "dateDebut doit être null après le constructeur par défaut");
        assertNull(evt.getDateFin(), "dateFin doit être null après le constructeur par défaut");
        assertNull(evt.getUtilisateur(), "utilisateur doit être null après le constructeur par défaut");
        // Les collections doivent être non null et vides
        assertNotNull(evt.getActionEvenement(), "La collection actionEvenement ne doit pas être null");
        assertTrue(evt.getActionEvenement().isEmpty(), "La collection actionEvenement doit être vide");
        assertNotNull(evt.getGroupes(), "La collection groupes ne doit pas être null");
        assertTrue(evt.getGroupes().isEmpty(), "La collection groupes doit être vide");
    }

    @Test
    @DisplayName("Constructeur complet initialise tous les champs et collections")
    void testAllArgsConstructor() {
        Long idE        = 42L;
        String titre    = "Fête étudiante";
        String lieu     = "Campus MIAGE";
        String avatar   = "/images/fete.png";
        String desc     = "Grande fête sur le campus.";
        Date debut      = new Date(1_600_000_000_000L);
        Date fin        = new Date(1_600_000_360_000L);
        Utilisateur user = mock(Utilisateur.class);

        ActionEvenement ae = mock(ActionEvenement.class);
        Groupe grp        = mock(Groupe.class);

        Set<ActionEvenement> actions = new HashSet<>();
        actions.add(ae);
        Set<Groupe> groupes = new HashSet<>();
        groupes.add(grp);

        Evenement evt = new Evenement(
            idE, titre, lieu, desc, avatar,
            debut, fin, user, actions, groupes
        );

        // Vérification des champs simples
        assertEquals(idE, evt.getIdE(),        "L'idE doit être celui passé au constructeur");
        assertEquals(titre, evt.getTitre(),    "Le titre doit être celui passé au constructeur");
        assertEquals(lieu, evt.getLieu(),      "Le lieu doit être celui passé au constructeur");
        assertEquals(avatar, evt.getAvatarE(), "L'avatar doit être celui passé au constructeur");
        assertEquals(desc, evt.getDescriptionE(), "La description doit être celle passée au constructeur");
        assertEquals(debut, evt.getDateDebut(), "La dateDebut doit être celle passée au constructeur");
        assertEquals(fin, evt.getDateFin(),     "La dateFin doit être celle passée au constructeur");
        assertSame(user, evt.getUtilisateur(),  "L'utilisateur doit être celui passé au constructeur");

        // Vérification des collections
        assertSame(actions, evt.getActionEvenement(),
            "La collection actionEvenement doit être celle passée au constructeur");
        assertTrue(evt.getActionEvenement().contains(ae),
            "La collection actionEvenement doit contenir l'action passée");
        assertSame(groupes, evt.getGroupes(),
            "La collection groupes doit être celle passée au constructeur");
        assertTrue(evt.getGroupes().contains(grp),
            "La collection groupes doit contenir le groupe passé");
    }

    @Test
    @DisplayName("Getters et setters fonctionnent correctement pour tous les attributs")
    void testSettersAndGetters() {
        Evenement evt = new Evenement();

        Long idE        = 7L;
        String titre    = "Atelier";
        String lieu     = "Salle 101";
        String avatar   = "/img/atelier.jpg";
        String desc     = "Atelier de codage.";
        Date debut      = new Date(1_610_000_000_000L);
        Date fin        = new Date(1_610_000_360_000L);
        Utilisateur user = mock(Utilisateur.class);

        ActionEvenement ae = mock(ActionEvenement.class);
        Groupe grp        = mock(Groupe.class);

        Set<ActionEvenement> actions = new HashSet<>();
        actions.add(ae);
        Set<Groupe> groupes = new HashSet<>();
        groupes.add(grp);

        // Appliquer les setters
        evt.setIdE(idE);
        evt.setTitre(titre);
        evt.setLieu(lieu);
        evt.setAvatarE(avatar);
        evt.setDescriptionE(desc);
        evt.setDateDebut(debut);
        evt.setDateFin(fin);
        evt.setUtilisateur(user);
        evt.setActionEvenement(actions);
        evt.setGroupes(groupes);

        // Vérifier avec les getters
        assertEquals(idE, evt.getIdE());
        assertEquals(titre, evt.getTitre());
        assertEquals(lieu, evt.getLieu());
        assertEquals(avatar, evt.getAvatarE());
        assertEquals(desc, evt.getDescriptionE());
        assertEquals(debut, evt.getDateDebut());
        assertEquals(fin, evt.getDateFin());
        assertSame(user, evt.getUtilisateur());
        assertSame(actions, evt.getActionEvenement());
        assertSame(groupes, evt.getGroupes());
    }
}

