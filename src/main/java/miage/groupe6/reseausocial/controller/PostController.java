package miage.groupe6.reseausocial.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.ActionPostService;
import miage.groupe6.reseausocial.model.jpa.service.PostService;
import miage.groupe6.reseausocial.model.jpa.service.UtilisateurService;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService ps;

    @Autowired
    private ActionPostService aps;

    @Autowired
    private UtilisateurService utilisateurService;


    @PostMapping("/creerPost")

    public String creerPost(@ModelAttribute Post newPost, HttpSession session, @RequestParam(value="imageFile", required=false) MultipartFile imageFile)throws IOException{
        System.out.println(newPost.getContenuP());
        Utilisateur poster = (Utilisateur) session.getAttribute("utilisateur");
        newPost.setAuteur(poster);
        newPost.setDateP(new Date());
        ps.save(newPost);
        return "redirect:/";
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
        aps.likePost(liker, optPost.get());
        return "redirect:/";
    }

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
