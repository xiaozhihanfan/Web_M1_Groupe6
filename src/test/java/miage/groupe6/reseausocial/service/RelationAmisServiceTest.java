package miage.groupe6.reseausocial.service;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import miage.groupe6.reseausocial.model.entity.RelationAmis;
import miage.groupe6.reseausocial.model.entity.StatutRelation;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.RelationAmisRepository;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;
import miage.groupe6.reseausocial.model.jpa.service.RelationAmisService;



@SpringBootTest
public class RelationAmisServiceTest {

    @Autowired
    private RelationAmisService relationAmisService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RelationAmisRepository relationAmisRepository;

    private Utilisateur demandeur;
    private Utilisateur receveur;

    @BeforeEach
    void setup() {
        relationAmisRepository.deleteAll();
        utilisateurRepository.deleteAll();

        demandeur = new Utilisateur();
        demandeur.setEmailU("demandeur@mail.com");
        demandeur.setMpU("pwd");
        demandeur.setNomU("Demandeur");
        demandeur.setPrenomU("X");
        utilisateurRepository.save(demandeur);

        receveur = new Utilisateur();
        receveur.setEmailU("receveur@mail.com");
        receveur.setMpU("pwd");
        receveur.setNomU("Receveur");
        receveur.setPrenomU("Y");
        utilisateurRepository.save(receveur);
    }

    @Test
    void testEnvoyerDemandeAmi_Success() {
        boolean success = relationAmisService.envoyerDemandeAmi(demandeur, receveur);
        assertTrue(success);
        assertTrue(relationAmisService.demandeExisteDeja(demandeur.getIdU(), receveur.getIdU()));
    }

    @Test
    void testEnvoyerDemandeAmi_DejaEnvoyee() {
        relationAmisService.envoyerDemandeAmi(demandeur, receveur);
        boolean secondTry = relationAmisService.envoyerDemandeAmi(demandeur, receveur);
        assertFalse(secondTry);
    }


    @Test
    void testDemandeExisteDeja_false() {
        assertFalse(relationAmisService.demandeExisteDeja(demandeur.getIdU(), receveur.getIdU()));
    }


    /**
     * Vérifie que listerAmis renvoie une liste vide lorsqu'il n'y a aucune relation acceptée.
     */
    @Test
    void testListerAmisVide() {
        // Pas de demande envoyée ni reçue
        List<Utilisateur> amis = relationAmisService.listerAmis(demandeur);
        assertTrue(amis.isEmpty(), "La liste d'amis devrait être vide");
    }

    /**
     * Vérifie que listerAmis renvoie bien tous les amis acceptés, qu'ils soient
     * demandeurs ou receveurs.
     */
    @Test
    void testListerAmisComplet() {
        // 1) relation où 'demandeur' a envoyé à 'receveur'
        relationAmisService.envoyerDemandeAmi(demandeur, receveur);
        RelationAmis rel1 = relationAmisRepository
            .findByUtilisateurDemandeIdUAndUtilisateurRecuIdU(demandeur.getIdU(), receveur.getIdU())
            .get();
        rel1.setStatut(StatutRelation.ACCEPTEE);
        relationAmisRepository.save(rel1);

        // 2) relation inverse : un tiers envoie à 'demandeur'
        Utilisateur tiers = new Utilisateur();
        tiers.setEmailU("tiers@mail.com");
        tiers.setMpU("pwd");
        tiers.setNomU("Tiers");
        tiers.setPrenomU("Z");
        utilisateurRepository.save(tiers);

        relationAmisService.envoyerDemandeAmi(tiers, demandeur);
        RelationAmis rel2 = relationAmisRepository
            .findByUtilisateurDemandeIdUAndUtilisateurRecuIdU(tiers.getIdU(), demandeur.getIdU())
            .get();
        rel2.setStatut(StatutRelation.ACCEPTEE);
        relationAmisRepository.save(rel2);

        // Appel de la méthode à tester
        List<Utilisateur> amis = relationAmisService.listerAmis(demandeur);

        // Vérifications
        assertEquals(2, amis.size(), "Il devrait y avoir exactement 2 amis");
        assertTrue(amis.contains(receveur), "La liste doit contenir le receveur initial");
        assertTrue(amis.contains(tiers),     "La liste doit contenir le tiers");
    }
}
