package miage.groupe6.reseausocial.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.ActionPost;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.StatutActionPost;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.ActionPostService;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

/**
 * Contrôleur Spring MVC pour gérer les opérations liées aux publications (posts)
 * telles que la création, le like, le repost, etc.
 * 
 * Gère les requêtes HTTP associées aux publications des utilisateurs sur le réseau social.
 */
@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService ps;

    @Autowired
    private ActionPostService aps;

    @Autowired
    private UtilisateurService utilisateurService;


    /**
     * Crée un nouveau post texte depuis la page d'accueil.
     * 
     * @param contenuP le contenu du post
     * @param session la session HTTP contenant l'utilisateur connecté
     * @return redirection vers la page d'accueil
     * @throws IOException si une erreur d'entrée/sortie survient
     */
    @PostMapping("/creerPost")
    public String creerPost(String contenuP, HttpSession session)throws IOException{
        Utilisateur poster = (Utilisateur) session.getAttribute("utilisateur");
        Post newPost = new Post();
        newPost.setContenuP(contenuP);
        newPost.setAuteur(poster);
        newPost.setDateP(new Date());
        ps.save(newPost);
        
        return "redirect:/";
    }



    /**
     * Crée un post avec une image, via une requête JSON.
     * 
     * @param newPost le post reçu dans le corps de la requête
     * @param session la session HTTP contenant l'utilisateur connecté
     * @return le post créé (id uniquement) en tant que réponse JSON
     * @throws IOException si une erreur d'entrée/sortie survient
     */
    @PostMapping("/creerPostImage")
    public ResponseEntity<Post> creerPost(@RequestBody Post newPost, HttpSession session)throws IOException{
        System.out.println(newPost.getContenuP());
        System.out.println(newPost.getImageP());
        Utilisateur poster = (Utilisateur) session.getAttribute("utilisateur");
        newPost.setAuteur(poster);
        newPost.setDateP(new Date());
        ps.save(newPost);
        Post postResponse = new Post();
        postResponse.setIdP(newPost.getIdP());
        return ResponseEntity.ok(postResponse);
    }


    /**
     * Permet à un utilisateur de liker un post.
     * Récupère d'abord l'utilisateur en session, puis le post par son ID.
     * Si l'utilisateur n'est pas connecté, redirige vers la page de connexion.
     * Si le post n'existe pas, redirige vers l'accueil.
     */
    @GetMapping("/{id}/like")
    public String likePost(@PathVariable("id") String id, HttpSession session) {
        Utilisateur liker = (Utilisateur) session.getAttribute("utilisateur");
        if (liker == null) {
            return "redirect:/utilisateurs/signin";
        }

        Optional<Post> optPost = ps.findById(Long.parseLong(id));
        if (!optPost.isPresent()) {
            return "redirect:/";
        }
        Optional<ActionPost> optActionPost = aps.findByUtilisateurAndPostAndStatut(liker, optPost.get(),StatutActionPost.LIKE);
        if(!optActionPost.isPresent()) {
        aps.likePost(liker, optPost.get());
        }else
        	aps.deleteByUtilisateurAndPostAndStatut(liker, optPost.get(),StatutActionPost.LIKE);
        
        
        return "redirect:/";
    }

    /**
     * Crée un post depuis la page profil d'un utilisateur.
     * 
     * @param idU l'identifiant de l'utilisateur concerné
     * @param newPost le post soumis via formulaire
     * @param imageFile un fichier image facultatif
     * @return redirection vers la page des posts du profil utilisateur
     * @throws IOException si une erreur d'entrée/sortie survient
     */
    @PostMapping("/creerPostPageProfile")
    public String creerPostPageProfile(
            @RequestParam Long idU,
            @ModelAttribute Post newPost,
            @RequestParam(value="imageFile", required=false) MultipartFile imageFile
    ) throws IOException {
        Utilisateur poster = utilisateurService
                                .getUtilisateurById(idU)
                                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable : " + idU));
    
        newPost.setAuteur(poster);
        newPost.setDateP(new Date());
        ps.save(newPost);
    
        return "redirect:/utilisateurs/" + idU + "/profile-post";
    }

    /**
     * Permet à un utilisateur de repartager un post (fonction de "repost").
     * 
     * @param originalPostId l'identifiant du post original
     * @param session la session HTTP contenant l'utilisateur connecté
     * @return redirection vers la page d'accueil ou de connexion
     */
    @PostMapping("/repost")
    public String repost(@RequestParam("idP") Long originalPostId, HttpSession session) {
        Utilisateur reposter = (Utilisateur) session.getAttribute("utilisateur");
        if (reposter == null) {
            return "redirect:/utilisateurs/signin";
        }
        ps.repostPost(originalPostId, reposter.getIdU());
        return "redirect:/";
    }
    
    

    

}
