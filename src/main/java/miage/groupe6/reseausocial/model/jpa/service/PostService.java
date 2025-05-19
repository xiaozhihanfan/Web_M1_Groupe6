package miage.groupe6.reseausocial.model.jpa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.PostRepository;

/**
 * Service de la couche métier pour la gestion des publications (posts).
 * Fournit des opérations liées aux publications d’un utilisateur,
 * notamment le comptage du nombre de posts qu’il a rédigés.
 * 
 * Auteur : Mengyi YANG
 */
@Service
public class PostService {

    @Autowired
    private PostRepository pr;

    /**
     * Compte le nombre de publications (posts) rédigées par l’utilisateur spécifié.
     * Fait appel à {@link PostRepository#countByAuteur(Utilisateur)}
     * pour obtenir le nombre total de posts dont l’auteur est cet utilisateur.
     *
     * @param utilisateur l’entité utilisateur dont on souhaite compter les posts
     * @return le nombre total de posts rédigés par cet utilisateur
     */
    public int countPostByUtilisateur(Utilisateur utilisateur){
        int res = pr.countByAuteur(utilisateur);
        return res;
    }

    /**
     * Sauvegarde ou met à jour un post en base de données.
     *
     * @param newPost l’entité Post à persister
     * @return l’entité Post sauvegardée (avec id généré, date, etc.)
     */
    public Post save(Post newPost) {
        return pr.save(newPost);
    }

    /**
     * Récupère la liste de tous les posts ordonnés par date de création.
     *
     * @return liste des posts (ordre croissant sur dateP)
     */
    public List<Post> findAllOrderedByDate() {
        return pr.findAllByOrderByDateP();
    }


    /**
     * Récupère un post par son identifiant.
     *
     * @param id l'identifiant du post
     * @return Optional contenant le post s'il existe, sinon empty
     */
    public Optional<Post> findById(Long id) {
        return pr.findById(id);
    }




    /**
     * Récupère tous les posts de l’utilisateur passé en paramètre,
     * triés par date de publication décroissante (plus récents d’abord).
     */
    public List<Post> findByAuteurOrderByDateDesc(Utilisateur auteur) {
        return pr.findByAuteurOrderByDatePDesc(auteur);
    }


    /**
     * Récupère tous les posts de la base,
     * triés par date de publication décroissante
     * (les plus récents en premier).
     *
     * @return liste de tous les posts
     */
    public List<Post> findAllOrderedByDateDesc() {
        return pr.findAllByOrderByDatePDesc();
    }

}
