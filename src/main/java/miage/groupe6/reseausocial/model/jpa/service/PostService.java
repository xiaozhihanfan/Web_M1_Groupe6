package miage.groupe6.reseausocial.model.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
