package miage.groupe6.reseausocial.model.jpa.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.PostRepository;

/**
 * Service de la couche métier pour la gestion des publications (posts).
 * Fournit des opérations liées aux publications d’un utilisateur,
 * notamment le comptage du nombre de posts qu’il a rédigés.
 */
@Service
public class PostService {

    @Autowired
    private PostRepository pr;

    @Autowired
    @Lazy
    private UtilisateurService us;

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

    /**
     * Récupère tous les posts avec leurs commentaires associés.
     *
     * @return liste de posts incluant les commentaires
     */
    public List<Post> findAllPostsWithCommentaires() {
        return pr.findAllWithCommentaires();
    }

    /**
     * Crée et sauvegarde un repost d’un post existant par un autre utilisateur.
     * <p>
     * Le repost conserve le contenu et l’image du post original, 
     * référence le post original, et attribue le nouvel auteur.
     * </p>
     *
     * @param originalPostId identifiant du post à repost
     * @param idU            identifiant de l’utilisateur qui repost
     * @return entité Post du repost sauvegardée
     * @throws RuntimeException si le post original ou l’utilisateur n’existe pas
     */
    public Post repostPost(Long originalPostId, Long idU) {

        Optional<Post> optOriginal = pr.findById(originalPostId);
        if (!optOriginal.isPresent()) {
            throw new RuntimeException("Post original introuvable, ID=" + originalPostId);
        }
        Post original = optOriginal.get();

        Optional<Utilisateur> optUtilisateur = us.getUtilisateurById(idU);
        if (!optUtilisateur.isPresent()) {
            throw new RuntimeException("Utilisateur introuvable, ID=" + idU);
        }
        Utilisateur user = optUtilisateur.get();

        Post repost = new Post();
        repost.setContenuP(original.getContenuP());
        repost.setImageP(original.getImageP());
        repost.setOriginalPost(original);
        repost.setAuteur(user);
        repost.setDateP(new Date());

        return pr.save(repost);
    }

}
