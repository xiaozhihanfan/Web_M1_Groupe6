package miage.groupe6.reseausocial.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import miage.groupe6.reseausocial.model.entity.ActionPostId;

@SpringBootTest
class ActionPostIdTest {

    @Test
    void testDefaultConstructor() {
        ActionPostId id = new ActionPostId();
        assertNull(id.getIdU());
        assertNull(id.getIdP());
    }

    @Test
    void testParameterizedConstructor() {
        Long userId = 1L;
        Long postId = 2L;
        ActionPostId id = new ActionPostId(userId, postId);
        
        assertEquals(userId, id.getIdU());
        assertEquals(postId, id.getIdP());
    }

    @Test
    void testGettersAndSetters() {
        ActionPostId id = new ActionPostId();
        
        Long userId = 3L;
        Long postId = 4L;
        
        id.setIdU(userId);
        id.setIdP(postId);
        
        assertEquals(userId, id.getIdU());
        assertEquals(postId, id.getIdP());
    }

    @Test
    void testEqualsAndHashCode() {
        
        ActionPostId id1 = new ActionPostId(1L, 2L);
        ActionPostId id2 = new ActionPostId(1L, 2L);
        
        assertEquals(id1, id2);
        
        assertEquals(id1.hashCode(), id2.hashCode());
        
        ActionPostId id3 = new ActionPostId(3L, 4L);
        assertNotEquals(id1, id3);
        assertNotEquals(id1.hashCode(), id3.hashCode());
        
        ActionPostId id4 = new ActionPostId(null, null);
        ActionPostId id5 = new ActionPostId(null, null);
        assertEquals(id4, id5);
        assertEquals(id4.hashCode(), id5.hashCode());
    }

    @Test
    void testEqualsWithDifferentNullValues() {
        
        ActionPostId id1 = new ActionPostId(1L, null);
        ActionPostId id2 = new ActionPostId(1L, null);
        assertEquals(id1, id2);
        
        ActionPostId id3 = new ActionPostId(null, 2L);
        ActionPostId id4 = new ActionPostId(null, 2L);
        assertEquals(id3, id4);
        
        ActionPostId id5 = new ActionPostId(1L, null);
        ActionPostId id6 = new ActionPostId(2L, null);
        assertNotEquals(id5, id6);
        
        ActionPostId id7 = new ActionPostId(null, 1L);
        ActionPostId id8 = new ActionPostId(null, 2L);
        assertNotEquals(id7, id8);
    }

    @Test
    void testEqualsWithDifferentTypes() {
        ActionPostId id = new ActionPostId(1L, 2L);
        assertNotEquals(id, new Object());
        assertNotEquals(id, null);
    }
}
