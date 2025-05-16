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

    Optional<Utilisateur> findByEmailU(String emailU);

    Utilisateur findByEmailUIgnoreCase(String emailU);
    
    List<Utilisateur> findByNomUContainingIgnoreCaseOrPrenomUContainingIgnoreCase(String nomU, String prenomU);
}
