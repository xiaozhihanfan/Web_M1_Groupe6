package miage.groupe6.reseausocial.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import miage.groupe6.reseausocial.model.entity.*;

class ActionPostTest {

    private ActionPost actionPost;
    private Utilisateur utilisateur;
    private Post post;
    private Date testDate;

    @BeforeEach
    void setUp() {
        utilisateur = new Utilisateur();
        utilisateur.setIdU(1L);
        
        post = new Post();
        post.setIdP(100L);
        
        testDate = new Date();
        
        actionPost = new ActionPost(testDate, StatutActionPost.LIKE, utilisateur, post);
    }

    @Test
    void testConstructor() {
        assertNotNull(actionPost);
        assertEquals(testDate, actionPost.getDateActionPost());
        assertEquals(StatutActionPost.LIKE, actionPost.getStatut());
        assertEquals(utilisateur, actionPost.getUtilisateur());
        assertEquals(post, actionPost.getPost());
        
        ActionPostId id = actionPost.getId();
        assertNotNull(id);
        assertEquals(1L, id.getIdU());
        assertEquals(100L, id.getIdP());
    }

    @Test
    void testDefaultConstructor() {
        ActionPost defaultActionPost = new ActionPost();
        
        assertNull(defaultActionPost.getId());
        assertNull(defaultActionPost.getDateActionPost());
        assertNull(defaultActionPost.getStatut());
        assertNull(defaultActionPost.getUtilisateur());
        assertNull(defaultActionPost.getPost());
    }

    @Test
    void testGettersAndSetters() {
        Date newDate = new Date();
        Utilisateur newUtilisateur = new Utilisateur();
        newUtilisateur.setIdU(2L);
        Post newPost = new Post();
        newPost.setIdP(200L);
        
        ActionPostId newId = new ActionPostId(2L, 200L);
        actionPost.setId(newId);
        assertEquals(newId, actionPost.getId());
        
        actionPost.setDateActionPost(newDate);
        assertEquals(newDate, actionPost.getDateActionPost());
        
        actionPost.setStatut(StatutActionPost.UNLIKE);
        assertEquals(StatutActionPost.UNLIKE, actionPost.getStatut());
        
        actionPost.setUtilisateur(newUtilisateur);
        assertEquals(newUtilisateur, actionPost.getUtilisateur());
        
        actionPost.setPost(newPost);
        assertEquals(newPost, actionPost.getPost());
    }

    @ParameterizedTest
    @EnumSource(StatutActionPost.class)
    void testAllStatutValues(StatutActionPost statut) {
        actionPost.setStatut(statut);
        assertEquals(statut, actionPost.getStatut());
    }

    @Test
    void testactionDatePersistenceType() throws NoSuchFieldException, SecurityException {
        java.lang.reflect.Field field = actionPost.getClass().getDeclaredField("dateActionPost");
        Temporal temporalAnnotation = field.getAnnotation(Temporal.class);
        assertNotNull(temporalAnnotation);
        assertEquals(TemporalType.TIMESTAMP, temporalAnnotation.value());
    }

    @Test
    void testEmbeddedIdAnnotation() throws NoSuchFieldException, SecurityException {
        java.lang.reflect.Field field = actionPost.getClass().getDeclaredField("id");
        EmbeddedId embeddedIdAnnotation = field.getAnnotation(EmbeddedId.class);
        assertNotNull(embeddedIdAnnotation);
    }

    @Test
    <T> void testManyToOneRelationships() {
        try {
            java.lang.reflect.Field utilisateurField = actionPost.getClass().getDeclaredField("utilisateur");
            ManyToOne utilisateurAnnotation = utilisateurField.getAnnotation(ManyToOne.class);
            assertNotNull(utilisateurAnnotation);
            
            MapsId mapsIdAnnotation = utilisateurField.getAnnotation(MapsId.class);
            assertNotNull(mapsIdAnnotation);
            assertEquals("idU", mapsIdAnnotation.value());
            
            JoinColumn joinColumnAnnotation = utilisateurField.getAnnotation(JoinColumn.class);
            assertNotNull(joinColumnAnnotation);
            assertEquals("idU", joinColumnAnnotation.name());
            
            java.lang.reflect.Field postField = actionPost.getClass().getDeclaredField("post");
            ManyToOne postAnnotation = postField.getAnnotation(ManyToOne.class);
            assertNotNull(postAnnotation);
            
            MapsId postMapsIdAnnotation = postField.getAnnotation(MapsId.class);
            assertNotNull(postMapsIdAnnotation);
            assertEquals("idP", postMapsIdAnnotation.value());
            
            JoinColumn postJoinColumnAnnotation = postField.getAnnotation(JoinColumn.class);
            assertNotNull(postJoinColumnAnnotation);
            assertEquals("idP", postJoinColumnAnnotation.name());
        } catch (NoSuchFieldException e) {
            fail("Field not found: " + e.getMessage());
        }
    }

    @Test
    void testEntityAnnotations() {
        java.lang.reflect.Field[] fields = actionPost.getClass().getDeclaredFields();
        assertTrue(fields.length > 0);
        
        Entity entityAnnotation = actionPost.getClass().getAnnotation(Entity.class);
        assertNotNull(entityAnnotation);
        
        Table tableAnnotation = actionPost.getClass().getAnnotation(Table.class);
        assertNotNull(tableAnnotation);
        assertEquals("action_post", tableAnnotation.name());
    }
}
