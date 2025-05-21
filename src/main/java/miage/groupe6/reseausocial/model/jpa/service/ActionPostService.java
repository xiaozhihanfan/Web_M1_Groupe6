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

    public void likePost(Utilisateur utilisateur, Post post){
        Optional<ActionPost> optAP = apr.findByUtilisateurAndPostAndStatut(utilisateur, post, StatutActionPost.LIKE);
        if (!optAP.isPresent()) {
            Date dateActionPost = new Date();
            ActionPost ap = new ActionPost(dateActionPost, StatutActionPost.LIKE, utilisateur, post);
            apr.save(ap);
        }
    }

    public int countLikes(Post post) {
        return apr.countByPostAndStatut(post, StatutActionPost.LIKE);
    }
    
    public Optional<ActionPost> findByUtilisateurAndPostAndStatut(Utilisateur utilisateur, Post post, StatutActionPost statut){
		return apr.findByUtilisateurAndPostAndStatut(utilisateur, post, statut);
    }
    @Transactional
    public void deleteByUtilisateurAndPostAndStatut(Utilisateur utilisateur, Post post ,StatutActionPost statut) {
        apr.deleteByUtilisateurAndPostAndStatut(utilisateur,post,statut);
    }
    

}
