package miage.groupe6.reseausocial.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import miage.groupe6.reseausocial.model.entity.ActionEvenement;
import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.EvenementRepository;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class EvenementRepositoryTest {

    @Autowired
    private EvenementRepository evenementRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void testFindByUtilisateur() {
        // 1) 插入一个用户
        Utilisateur u = new Utilisateur();
        u.setPrenomU("Alice");
        u.setNomU("Dupont");
        u = utilisateurRepository.save(u);

        // 2) 插入两个活动，其中只有一个由 u 创建
        Evenement e1 = new Evenement();
        e1.setTitre("Soirée Alice");
        e1.setLieu("Paris");
        e1.setDescriptionE("Fête chez Alice");
        Date now = new Date();
        e1.setDateDebut(now);
        e1.setDateFin(new Date(now.getTime()+3600_000));
        e1.setUtilisateur(u);
        evenementRepository.save(e1);

        Evenement e2 = new Evenement();
        e2.setTitre("Bal Mystère");
        e2.setLieu("Lyon");
        e2.setDescriptionE("Déguisement obligatoire");
        e2.setDateDebut(now);
        e2.setDateFin(new Date(now.getTime()+7200_000));
        // 由另一个用户创建
        Utilisateur other = new Utilisateur();
        other.setPrenomU("Bob");
        other.setNomU("Martin");
        other = utilisateurRepository.save(other);
        e2.setUtilisateur(other);
        evenementRepository.save(e2);

        // 强制刷新到数据库
        em.flush();

        // 验证 findByUtilisateur 只返回 e1
        List<Evenement> crees = evenementRepository.findByUtilisateur(u);
        assertThat(crees)
            .hasSize(1)
            .containsExactly(e1);
    }

    @Test
    public void testFindByParticipantAndStatut() {
        // 假设上个测试已提交了 u, e2；这里我们再插入一个 u2 和一个活动 e3
        Utilisateur u = utilisateurRepository.findAll().get(0); // Alice
        Evenement e3 = new Evenement();
        e3.setTitre("Conférence Java");
        e3.setLieu("Toulouse");
        e3.setDescriptionE("Apprendre Spring");
        Date now = new Date();
        e3.setDateDebut(now);
        e3.setDateFin(new Date(now.getTime()+3600_000));
        e3.setUtilisateur(u);
        evenementRepository.save(e3);

        // 插入一条 ActionEvenement：u 对 e3 做 INSCRIRE
        ActionEvenement ae = new ActionEvenement();
        ae.setUtilisateur(u);
        ae.setEvenement(e3);
        ae.setDateActionEvenemnt(new Date());
        ae.setStatut(StatutActionEvenement.INSCRIRE);
        em.persist(ae);
        em.flush();

        // 验证 findByParticipantAndStatut 返回 e3
        List<Evenement> inscrits = evenementRepository
            .findByParticipantAndStatut(u, StatutActionEvenement.INSCRIRE);
        assertThat(inscrits)
            .isNotEmpty()
            .contains(e3);
    }
}
