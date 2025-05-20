package miage.groupe6.reseausocial.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import miage.groupe6.reseausocial.model.entity.RelationAmis;
import miage.groupe6.reseausocial.model.entity.StatutRelation;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.RelationAmisRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class RelationAmisRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private RelationAmisRepository repository;

    @Test
    @DisplayName("findByUtilisateurDemandeAndStatut ne renvoie que les relations acceptées où l'utilisateur est demandeur")
    void testFindByUtilisateurDemandeAndStatut() {
        // --- 1) Préparer les trois utilisateurs ---
        Utilisateur u1 = new Utilisateur();
        u1.setPrenomU("Alice");
        u1.setNomU("Liddell");
        u1.setEmailU("alice@example.com");
        em.persist(u1);

        Utilisateur u2 = new Utilisateur();
        u2.setPrenomU("Bob");
        u2.setNomU("Smith");
        u2.setEmailU("bob@example.com");
        em.persist(u2);

        // tiers pour la relation TRAITEE
        Utilisateur u3 = new Utilisateur();
        u3.setPrenomU("Tiers");
        u3.setNomU("Z");
        u3.setEmailU("tiers@example.com");
        em.persist(u3);

        em.flush();

        // --- 2) Créer et persister les relations ---
        // rel1 : acceptée, u1->u2
        RelationAmis rel1 = new RelationAmis(u1, u2, new Date(), StatutRelation.ACCEPTEE);
        em.persist(rel1);
        // rel2 : acceptée, u2->u1 (ne doit pas être retournée ici)
        RelationAmis rel2 = new RelationAmis(u2, u1, new Date(), StatutRelation.ACCEPTEE);
        em.persist(rel2);
        // rel3 : traitée (pending), u1->u3, ne doit pas être retournée
        RelationAmis rel3 = new RelationAmis(u1, u3, new Date(), StatutRelation.TRAITEE);
        em.persist(rel3);

        em.flush();

        // --- 3) Exécuter la méthode à tester ---
        List<RelationAmis> result = repository
            .findByUtilisateurDemandeAndStatut(u1, StatutRelation.ACCEPTEE);

        // --- 4) Vérifier qu'on n'obtient que rel1 ---
        assertThat(result)
            .hasSize(1)
            .allMatch(r ->
                r.getUtilisateurDemande().equals(u1) &&
                r.getStatut() == StatutRelation.ACCEPTEE
            );
    }

    @Test
    @DisplayName("findByUtilisateurRecuAndStatut ne renvoie que les relations acceptées où l'utilisateur est receveur")
    void testFindByUtilisateurRecuAndStatut() {
        // --- 1) Préparer les trois utilisateurs ---
        Utilisateur u1 = new Utilisateur();
        u1.setPrenomU("Alice");
        u1.setNomU("Liddell");
        u1.setEmailU("alice@example.com");
        em.persist(u1);

        Utilisateur u2 = new Utilisateur();
        u2.setPrenomU("Bob");
        u2.setNomU("Smith");
        u2.setEmailU("bob@example.com");
        em.persist(u2);

        // tiers pour la relation REFUSEE
        Utilisateur u3 = new Utilisateur();
        u3.setPrenomU("Tiers");
        u3.setNomU("Z");
        u3.setEmailU("tiers@example.com");
        em.persist(u3);

        em.flush();

        // --- 2) Créer et persister les relations ---
        // rel1 : acceptée, u1->u2 (ne doit pas être retournée ici)
        RelationAmis rel1 = new RelationAmis(u1, u2, new Date(), StatutRelation.ACCEPTEE);
        em.persist(rel1);
        // rel2 : acceptée, u2->u1
        RelationAmis rel2 = new RelationAmis(u2, u1, new Date(), StatutRelation.ACCEPTEE);
        em.persist(rel2);
        // rel3 : refusée, u3->u1, ne doit pas être retournée
        RelationAmis rel3 = new RelationAmis(u3, u1, new Date(), StatutRelation.TRAITEE);
        em.persist(rel3);

        em.flush();

        // --- 3) Exécuter la méthode à tester ---
        List<RelationAmis> result = repository
            .findByUtilisateurRecuAndStatut(u1, StatutRelation.ACCEPTEE);

        // --- 4) Vérifier qu'on n'obtient que rel2 ---
        assertThat(result)
            .hasSize(1)
            .allMatch(r ->
                r.getUtilisateurRecu().equals(u1) &&
                r.getStatut() == StatutRelation.ACCEPTEE
            );
    }
}
