
package miage.groupe6.reseausocial.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import miage.groupe6.reseausocial.model.entity.Post;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

import miage.groupe6.reseausocial.model.jpa.service.PostService;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private MockHttpSession session;
    private Utilisateur utilisateur;

    @BeforeEach
    public void setup() {
        utilisateur = new Utilisateur();
        utilisateur.setNomU("testUser");
        
        session = new MockHttpSession();
        session.setAttribute("utilisateur", utilisateur);
    }

    @Test
    public void testCreatePostWithoutImage() throws Exception {
        Post savedPost = new Post();
        savedPost.setContenuP("Test content");
        
        when(postService.save(any(Post.class))).thenReturn(savedPost);
        
        mockMvc.perform(((MockMultipartHttpServletRequestBuilder) multipart("/posts/creerPost")
                .param("contenuP", "Test content"))
                .file(new MockMultipartFile("imageFile", "", "text/plain", new byte[0]))
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        
        // Verify the post was saved with correct attributes
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postService).save(postCaptor.capture());
        
        Post capturedPost = postCaptor.getValue();
        assertEquals("Test content", capturedPost.getContenuP());
        assertEquals(utilisateur, capturedPost.getAuteur());
        assertNotNull(capturedPost.getDateP());
    }

    @Test
    public void testCreatePostWithImage() throws Exception {
        byte[] imageContent = "fake image content".getBytes();
        MockMultipartFile imageFile = new MockMultipartFile(
            "imageFile", 
            "test.jpg", 
            "image/jpeg", 
            imageContent
        );
        
        Post savedPost = new Post();
        when(postService.save(any(Post.class))).thenReturn(savedPost);
        
        mockMvc.perform(((MockMultipartHttpServletRequestBuilder) multipart("/posts/creerPost")
                .param("contenuP", "Test with image"))
                .file(imageFile)
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        
    }


}

