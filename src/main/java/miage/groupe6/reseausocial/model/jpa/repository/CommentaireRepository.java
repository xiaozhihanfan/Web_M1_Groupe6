package miage.groupe6.reseausocial.model.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import miage.groupe6.reseausocial.model.entity.Commentaire;
import miage.groupe6.reseausocial.model.entity.Post;

/**
 * Repository JPA pour l'entité {@link Commentaire}.
 * Fournit les opérations de base CRUD ainsi que des requêtes personnalisées.
 */

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    /**
     * Récupère la liste des commentaires d’un post, triés par date/heure croissante.
     *
     * @param post l’entité {@link Post} dont on veut les commentaires
     * @return liste ordonnée des commentaires du post
     */
    List<Commentaire> findByPostOrderByTempsC(Post post);

    /**
     * Récupère tous les commentaires d’un post en fonction de son ID.
     *
     * @param postId l’identifiant du post
     * @return liste des commentaires associés à ce post
     */
    List<Commentaire> findByPost_IdP(Long postId);

}
