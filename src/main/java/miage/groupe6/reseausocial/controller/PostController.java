package miage.groupe6.reseausocial.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.ActionPostService;
import miage.groupe6.reseausocial.model.jpa.service.PostService;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService ps;

    @Autowired
    private ActionPostService aps;

    @PostMapping("/creerPost")
    public String creerPost(@ModelAttribute Post newPost, HttpSession session, @RequestParam("imageFile") MultipartFile imageFile)throws IOException{
        Utilisateur poster = (Utilisateur) session.getAttribute("utilisateur");
        newPost.setAuteur(poster);
        newPost.setDateP(new Date());

        // if (imageFile != null && !imageFile.isEmpty()) {
        //     String realPath = servletContext.getRealPath("/uploads");
        //     Path uploadDir = Paths.get(realPath);
        //     Files.createDirectories(uploadDir);

        //     String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        //     Path target = uploadDir.resolve(filename);
        //     imageFile.transferTo(target.toFile());

        //     newPost.setImageP("/uploads/" + filename);
        // }

        ps.save(newPost);
        return "redirect:/";
    }


    /**
     * Permet à un utilisateur de liker un post.
     * Récupère d'abord l'utilisateur en session, puis le post par son ID.
     * Si l'utilisateur n'est pas connecté, redirige vers la page de connexion.
     * Si le post n'existe pas, redirige vers l'accueil.
     */
    @PostMapping("/{id}/like")
    public String likePost(@PathVariable("id") Long id, HttpSession session) {
        Utilisateur liker = (Utilisateur) session.getAttribute("utilisateur");
        if (liker == null) {
            return "redirect:/utilisateurs/signin";
        }
        Optional<Post> optPost = ps.findById(id);
        if (!optPost.isPresent()) {
            return "redirect:/";
        }
        aps.likePost(liker, optPost.get());
        return "redirect:/";
    }

}
