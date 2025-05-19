package miage.groupe6.reseausocial.model.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import miage.groupe6.reseausocial.model.entity.ActionPost;
import miage.groupe6.reseausocial.model.entity.ActionPostId;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.StatutActionPost;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Repository JPA pour l'entité {@link ActionPost}.
 * Gère les opérations de persistance concernant les relations de type "like"
 * entre les utilisateurs et les posts.
 * <p>
 * Utilise {@link ActionPostId} comme clé primaire composite.
 * 
 * Auteur : Mengyi YANG
 */

 @Repository
public interface ActionPostRepository extends JpaRepository<ActionPost, ActionPostId> {

    // Compter le nombre de likes pour un post
    Integer countByPostAndStatut(Post post, StatutActionPost statut);

    Optional<ActionPost> findByUtilisateurAndPostAndStatut(Utilisateur utilisateur, Post post, StatutActionPost statut);
}
