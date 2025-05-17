package miage.groupe6.reseausocial.model.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Repository JPA pour l'entité {@link Post}.
 * Fournit les opérations CRUD de base pour les publications.
 * 
 * Auteur : Mengyi YANG
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Integer countByAuteur(Utilisateur auteur);

    List<Post> findAllByOrderByDateP();


}
