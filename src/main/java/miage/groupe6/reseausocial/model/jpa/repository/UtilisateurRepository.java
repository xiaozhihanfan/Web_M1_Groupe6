package miage.groupe6.reseausocial.model.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Repository JPA pour l'entité {@link Utilisateur}.
 * Fournit les opérations CRUD pour les utilisateurs de la plateforme.
 * 
 * Auteur : Mengyi YANG
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    /**
     * Recherche un utilisateur par son adresse e-mail exacte.
     *
     * @param emailU adresse e-mail de l'utilisateur
     * @return optionnel contenant l'utilisateur s'il existe, ou vide sinon
     */
    Optional<Utilisateur> findByEmailU(String emailU);

    /**
     * Recherche un utilisateur par son adresse e-mail, sans tenir compte de la casse.
     *
     * @param emailU adresse e-mail de l'utilisateur (case-insensitive)
     * @return l'utilisateur correspondant, ou null si aucun trouvé
     */
    Utilisateur findByEmailUIgnoreCase(String emailU);
    
    /**
     * Recherche les utilisateurs dont le nom ou le prénom contient la chaîne
     * de caractères spécifiée, sans tenir compte de la casse.
     *
     * @param nomU     fragment à rechercher dans le nom de famille
     * @param prenomU  fragment à rechercher dans le prénom
     * @return liste des utilisateurs correspondant aux critères
     */
    List<Utilisateur> findByNomUContainingIgnoreCaseOrPrenomUContainingIgnoreCase(String nomU, String prenomU);
}
