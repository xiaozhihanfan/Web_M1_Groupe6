package miage.groupe6.reseausocial.service;


import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;
import miage.groupe6.reseausocial.model.jpa.service.ProfilService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.List;
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
    @Mock
    private miage.groupe6.reseausocial.model.jpa.repository.EvenementRepository evenementRepository;

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
    

    /**
     * getEvenementsCrees(id) 应该调用 evenementRepository.findByUtilisateur(...)
     * 并返回仓库模拟的数据列表。
     */
    @Test
    void getEvenementsCrees_existingUser_returnsEvents() {
        // 准备：用户存在
        when(utilisateurRepository.findById(42L))
            .thenReturn(Optional.of(utilisateur));

        // 准备：模拟两个活动
        miage.groupe6.reseausocial.model.entity.Evenement e1 =
            new miage.groupe6.reseausocial.model.entity.Evenement();
        e1.setIdE(100L);
        e1.setTitre("Événement créé");
        // ... 可以不设置别的字段
        
        List<miage.groupe6.reseausocial.model.entity.Evenement> creeList = List.of(e1);
        when(evenementRepository.findByUtilisateur(utilisateur))
            .thenReturn(creeList);

        // 执行
        List<miage.groupe6.reseausocial.model.entity.Evenement> result =
            profilService.getEvenementsCrees(42L);

        // 验证
        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(e1, result.get(0));
        verify(evenementRepository).findByUtilisateur(utilisateur);
    }

    /**
     * getEvenementsInscrits(id) 应该调用 findByParticipantAndStatut(..., INSCRIRE)
     */
    @Test
    void getEvenementsInscrits_existingUser_returnsEvents() {
        when(utilisateurRepository.findById(42L))
            .thenReturn(Optional.of(utilisateur));

        miage.groupe6.reseausocial.model.entity.Evenement e2 =
            new miage.groupe6.reseausocial.model.entity.Evenement();
        e2.setIdE(101L);
        e2.setTitre("Événement inscrit");
        List<miage.groupe6.reseausocial.model.entity.Evenement> insList = List.of(e2);

        when(evenementRepository.findByParticipantAndStatut(
                utilisateur,
                miage.groupe6.reseausocial.model.entity.StatutActionEvenement.INSCRIRE))
            .thenReturn(insList);

        List<miage.groupe6.reseausocial.model.entity.Evenement> result =
            profilService.getEvenementsInscrits(42L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(e2, result.get(0));
        verify(evenementRepository)
            .findByParticipantAndStatut(utilisateur, miage.groupe6.reseausocial.model.entity.StatutActionEvenement.INSCRIRE);
    }

    /**
     * getEvenementsIntereses(id) 应该调用 findByParticipantAndStatut(..., INTERESSER)
     */
    @Test
    void getEvenementsIntereses_existingUser_returnsEvents() {
        when(utilisateurRepository.findById(42L))
            .thenReturn(Optional.of(utilisateur));

        miage.groupe6.reseausocial.model.entity.Evenement e3 =
            new miage.groupe6.reseausocial.model.entity.Evenement();
        e3.setIdE(102L);
        e3.setTitre("Événement intéressant");
        List<miage.groupe6.reseausocial.model.entity.Evenement> intList = List.of(e3);

        when(evenementRepository.findByParticipantAndStatut(
                utilisateur,
                miage.groupe6.reseausocial.model.entity.StatutActionEvenement.INTERESSER))
            .thenReturn(intList);

        List<miage.groupe6.reseausocial.model.entity.Evenement> result =
            profilService.getEvenementsInteresses(42L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(e3, result.get(0));
        verify(evenementRepository)
            .findByParticipantAndStatut(utilisateur, miage.groupe6.reseausocial.model.entity.StatutActionEvenement.INTERESSER);
    }
}
