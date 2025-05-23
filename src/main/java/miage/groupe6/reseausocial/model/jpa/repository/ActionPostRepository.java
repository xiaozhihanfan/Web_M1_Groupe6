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
 */

 @Repository
public interface ActionPostRepository extends JpaRepository<ActionPost, ActionPostId> {

    
    // Compter le nombre de likes pour un post
    /**
     * Compte le nombre d’actions d’un certain statut (par exemple, LIKE) sur un post donné.
     *
     * @param post le post concerné
     * @param statut le type d’action (ex: LIKE)
     * @return le nombre total d’actions de ce type sur ce post
     */
    Integer countByPostAndStatut(Post post, StatutActionPost statut);

    /**
     * Recherche une action spécifique d’un utilisateur sur un post avec un statut donné.
     *
     * @param utilisateur l’utilisateur ayant effectué l’action
     * @param post le post concerné
     * @param statut le type d’action (ex: LIKE)
     * @return une {@link Optional} contenant l’action si elle existe, vide sinon
     */
    Optional<ActionPost> findByUtilisateurAndPostAndStatut(Utilisateur utilisateur, Post post, StatutActionPost statut);
    
    /**
     * Supprime une action spécifique d’un utilisateur sur un post selon son statut.
     *
     * @param utilisateur l’utilisateur ayant effectué l’action
     * @param post le post concerné
     * @param statut le type d’action à supprimer (ex: LIKE)
     */
    void deleteByUtilisateurAndPostAndStatut(Utilisateur utilisateur, Post post ,StatutActionPost statut);
    

}
