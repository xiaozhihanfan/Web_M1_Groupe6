package miage.groupe6.reseausocial.model.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import miage.groupe6.reseausocial.model.entity.Evenement;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.StatutActionEvenement;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.EvenementRepository;
import miage.groupe6.reseausocial.model.jpa.repository.PostRepository;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;

/**
 * Service gérant la récupération des profils Utilisateur.
 * Fournit une méthode pour obtenir un utilisateur par son identifiant,
 * ou lancer une exception 404 si l’utilisateur n’existe pas.
 */
@Service
public class ProfilService {

    private final UtilisateurRepository utilisateurRepository;
    private final EvenementRepository   evenementRepository;

    /**
     * Constructeur par injection de dépendance du repository Utilisateur.
     *
     * @param utilisateurRepository le repository JPA pour l’entité Utilisateur
     * @param evenementRepository le repository JPA pour l’entité Utilisateur
     */
    @Autowired
    public ProfilService(UtilisateurRepository utilisateurRepository, EvenementRepository evenementRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.evenementRepository = evenementRepository;
    }

    /**
     * Récupère un utilisateur à partir de son identifiant.
     * Si l’utilisateur n’est pas trouvé, lève une ResponseStatusException
     * avec le statut HTTP 404 (NOT_FOUND).
     *
     * @param id l’identifiant de l’utilisateur à rechercher
     * @return l’objet Utilisateur correspondant à l’identifiant
     * @throws ResponseStatusException si aucun utilisateur n’est trouvé
     */
    public Utilisateur getProfileById(Long id) {
        return utilisateurRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Utilisateur non trouvé : " + id
            ));
    }

    /**
     * Récupère la liste des événements créés par l’utilisateur.
     *
     * @param idUtilisateur identifiant de l’utilisateur
     * @return liste des événements qu’il a créés
     * @throws ResponseStatusException 404 si l’utilisateur n’existe pas
     */
    public List<Evenement> getEvenementsCrees(Long idUtilisateur) {
        Utilisateur u = utilisateurRepository.findById(idUtilisateur)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Utilisateur non trouvé : " + idUtilisateur
            ));
        return evenementRepository.findByUtilisateur(u);
    }

    /**
     * Récupère la liste des événements auxquels l’utilisateur est inscrit.
     *
     * @param idUtilisateur identifiant de l’utilisateur
     * @return liste des événements où statut = INSCRIRE
     */
    public List<Evenement> getEvenementsInscrits(Long idUtilisateur) {
        Utilisateur u = utilisateurRepository.findById(idUtilisateur)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Utilisateur non trouvé : " + idUtilisateur
            ));
        return evenementRepository.findByParticipantAndStatut(u, StatutActionEvenement.INSCRIRE);
    }

    /**
     * Récupère la liste des événements qui intéressent l’utilisateur.
     *
     * @param idUtilisateur identifiant de l’utilisateur
     * @return liste des événements où statut = INTERESSER
     */
    public List<Evenement> getEvenementsInteresses(Long idUtilisateur) {
        Utilisateur u = utilisateurRepository.findById(idUtilisateur)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Utilisateur non trouvé : " + idUtilisateur
            ));
        return evenementRepository.findByParticipantAndStatut(u, StatutActionEvenement.INTERESSER);
    }
}
