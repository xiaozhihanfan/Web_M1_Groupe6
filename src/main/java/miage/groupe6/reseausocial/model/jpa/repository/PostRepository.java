package miage.groupe6.reseausocial.model.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Repository JPA pour l'entité {@link Post}.
 * Fournit les opérations CRUD de base pour les publications.
 * 
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Integer countByAuteur(Utilisateur auteur);

    /**
     * Récupère la liste de tous les posts,
     * triés par date de publication croissante (plus anciens en premier).
     */
    List<Post> findAllByOrderByDateP();

    /**
     * Récupère la liste des posts d’un auteur donné, triés par dateP décroissante.
     */
    List<Post> findByAuteurOrderByDatePDesc(Utilisateur auteur);

    /**
     * Récupère la liste de tous les posts,
     * triés par date de publication décroissante (plus récents en premier).
     *
     * @return liste de tous les posts ordonnée par dateP desc.
     */
    List<Post> findAllByOrderByDatePDesc();

    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.commentaires ORDER BY p.dateP DESC")
    List<Post> findAllWithCommentaires();

}
