package miage.groupe6.reseausocial.model.jpa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miage.groupe6.reseausocial.model.entity.Commentaire;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.CommentaireRepository;

@Service
public class CommentaireService {

    @Autowired
    private CommentaireRepository cr;

    public void ajouterCommentaire(Utilisateur utilisateur, Post post, String contenu) {
        Commentaire commentaire = new Commentaire();
        commentaire.setUtilisateur(utilisateur);
        commentaire.setPost(post);
        commentaire.setContenueC(contenu);
        commentaire.setTempsC(new Date());
        cr.save(commentaire);
    }

    public List<Commentaire> getCommentairesByPost(Post post) {
        return cr.findByPostOrderByTempsC(post);
    }

}
