package miage.groupe6.reseausocial.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.service.PostService;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService pr;

    @PostMapping("/creerPost")
    public String creerPost(@ModelAttribute Post newPost, HttpSession session, @RequestParam("imageFile") MultipartFile imageFile)throws IOException{
        System.out.println(newPost.getContenuP());
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

        pr.save(newPost);
        return "redirect:/";
    }

}
