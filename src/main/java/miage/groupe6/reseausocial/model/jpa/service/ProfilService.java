package miage.groupe6.reseausocial.model.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
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

    /**
     * Constructeur par injection de dépendance du repository Utilisateur.
     *
     * @param utilisateurRepository le repository JPA pour l’entité Utilisateur
     */
    @Autowired
    public ProfilService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
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
}
