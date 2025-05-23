package miage.groupe6.reseausocial.model.jpa.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miage.groupe6.reseausocial.model.entity.Commentaire;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.CommentaireRepository;

/**
 * Service métier pour la gestion des commentaires.
 * <p>
 * Fournit des méthodes pour ajouter un commentaire à un post et
 * récupérer les commentaires associés à une publication.
 * </p>
 */
@Service
public class CommentaireService {

    @Autowired
    private CommentaireRepository cr;

    /**
     * Ajoute un nouveau commentaire à une publication.
     *
     * @param utilisateur l'utilisateur qui rédige le commentaire
     * @param post la publication concernée
     * @param contenu le contenu textuel du commentaire
     */
    public void ajouterCommentaire(Utilisateur utilisateur, Post post, String contenu) {
        Commentaire commentaire = new Commentaire();
        commentaire.setUtilisateur(utilisateur);
        commentaire.setPost(post);
        commentaire.setContenueC(contenu);
        commentaire.setTempsC(new Date());
        cr.save(commentaire);
    }

    /**
     * Récupère tous les commentaires associés à une publication,
     * triés par date croissante (ancien → récent).
     *
     * @param post la publication cible
     * @return la liste des commentaires associés
     */
    public List<Commentaire> getCommentairesByPost(Post post) {
        return cr.findByPostOrderByTempsC(post);
    }

}
