package miage.groupe6.reseausocial.service;
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
    void testCountFollowingAndFollowers_Acceptee() {
        // Envoyer une demande, l'accepter manuellement
        relationAmisService.envoyerDemandeAmi(demandeur, receveur);
        RelationAmis relation = relationAmisRepository
                .findByUtilisateurDemandeIdUAndUtilisateurRecuIdU(demandeur.getIdU(), receveur.getIdU())
                .get();
        relation.setStatut(StatutRelation.ACCEPTEE);
        relationAmisRepository.save(relation);

        int following = relationAmisService.countFollowingAccepte(demandeur);
        int followers = relationAmisService.countFollowersAccepte(receveur);

        assertEquals(1, following);
        assertEquals(1, followers);
    }

    @Test
    void testDemandeExisteDeja_false() {
        assertFalse(relationAmisService.demandeExisteDeja(demandeur.getIdU(), receveur.getIdU()));
    }
}
