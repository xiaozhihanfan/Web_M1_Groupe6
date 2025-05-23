package miage.groupe6.reseausocial.model.jpa.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import miage.groupe6.reseausocial.model.entity.ActionPost;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.StatutActionPost;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.ActionPostRepository;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;

@Service
public class ActionPostService {

    @Autowired
    private ActionPostRepository apr;

    @Autowired
    private UtilisateurRepository ur;

    /**
     * Enregistre un "like" sur un post de la part d'un utilisateur,
     * à condition qu'il n'existe pas déjà.
     *
     * @param utilisateur l'utilisateur qui effectue le like
     * @param post        la publication concernée
     */
    public void likePost(Utilisateur utilisateur, Post post){
        Optional<ActionPost> optAP = apr.findByUtilisateurAndPostAndStatut(utilisateur, post, StatutActionPost.LIKE);
        if (!optAP.isPresent()) {
            Date dateActionPost = new Date();
            ActionPost ap = new ActionPost(dateActionPost, StatutActionPost.LIKE, utilisateur, post);
            apr.save(ap);
        }
    }

    /**
     * Compte le nombre de likes associés à une publication.
     *
     * @param post la publication cible
     * @return le nombre total de likes
     */
    public int countLikes(Post post) {
        return apr.countByPostAndStatut(post, StatutActionPost.LIKE);
    }
    
    /**
     * Recherche une action précise (ex: LIKE) d'un utilisateur sur une publication.
     *
     * @param utilisateur l'utilisateur concerné
     * @param post        la publication concernée
     * @param statut      le type d'action (LIKE, etc.)
     * @return un {@link Optional} contenant l'action si elle existe
     */
    public Optional<ActionPost> findByUtilisateurAndPostAndStatut(Utilisateur utilisateur, Post post, StatutActionPost statut){
		return apr.findByUtilisateurAndPostAndStatut(utilisateur, post, statut);
    }

    
    /**
     * Supprime une action donnée (ex: un like) d’un utilisateur sur une publication.
     *
     * @param utilisateur l'utilisateur qui a effectué l'action
     * @param post        la publication cible
     * @param statut      le type d'action à supprimer
     */
    @Transactional
    public void deleteByUtilisateurAndPostAndStatut(Utilisateur utilisateur, Post post ,StatutActionPost statut) {
        apr.deleteByUtilisateurAndPostAndStatut(utilisateur,post,statut);
    }
    

}
