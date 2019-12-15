package features;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    private Vector2d v_1_1 = new Vector2d(1, 1);
    private Vector2d v_0_0 = new Vector2d(0, 0);
    private Vector2d v_0_1 = new Vector2d(0, 1);
    private Vector2d v_1_0 = new Vector2d(1, 0);
    private Vector2d v_2_0 = new Vector2d(2, 0);
    private Vector2d v_0_2 = new Vector2d(0, 2);
    private Vector2d v_2_1 = new Vector2d(2, 1);
    private Vector2d v_1_2 = new Vector2d(1, 2);
    private Vector2d v_2_2 = new Vector2d(2, 2);

    private Vector2d v_5_7 = new Vector2d(5, 7);
    private Vector2d v_neg8_3 = new Vector2d(-8, 3);

    @Test
    void testEquals(){
        Vector2d v_0_0a = new Vector2d(0, 0);

        Object notPosition = new Object();

        assertEquals(v_0_0, v_0_0);
        assertNotEquals(v_0_0, notPosition);
        assertEquals(v_0_0, v_0_0a);
        assertNotEquals(v_0_0, v_1_1);
        assertNotEquals(v_0_0, v_0_1);
        assertNotEquals(v_1_1, v_0_0);
    }

    @Test
    void testToString(){
        assertEquals(v_0_1.toString(), "(0,1)");
    }

    @Test
    void testPrecedes(){
        assertTrue(v_1_1.precedes(v_1_1));
        assertTrue(v_0_0.precedes(v_1_1));
        assertTrue(v_1_1.precedes(v_1_2));
        assertTrue(v_1_1.precedes(v_2_1));
        assertFalse(v_1_1.precedes(v_0_2));
        assertFalse(v_1_1.precedes(v_2_0));
        assertFalse(v_1_1.precedes(v_0_1));
        assertFalse(v_1_1.precedes(v_1_0));
        assertFalse(v_1_1.precedes(v_0_0));
    }

    @Test
    void testFollows(){
        assertTrue(v_1_1.follows(v_1_1));
        assertTrue(v_1_1.follows(v_0_0));
        assertTrue(v_1_1.follows(v_0_1));
        assertTrue(v_1_1.follows(v_1_0));
        assertFalse(v_1_1.follows(v_2_0));
        assertFalse(v_1_1.follows(v_0_2));
        assertFalse(v_1_1.follows(v_2_1));
        assertFalse(v_1_1.follows(v_1_2));
        assertFalse(v_1_1.follows(v_2_2));
    }

    @Test
    void testUpperRight(){
        assertEquals(v_1_1.upperRight(v_1_1), v_1_1);
        assertEquals(v_1_1.upperRight(v_0_0), v_1_1);
        assertEquals(v_1_1.upperRight(v_0_1), v_1_1);
        assertEquals(v_1_1.upperRight(v_1_0), v_1_1);
        assertEquals(v_1_1.upperRight(v_2_1), v_2_1);
        assertEquals(v_1_1.upperRight(v_1_2), v_1_2);
        assertEquals(v_1_1.upperRight(v_2_0), v_2_1);
        assertEquals(v_1_1.upperRight(v_0_2), v_1_2);
    }

    @Test
    void testLowerLeft(){
        assertEquals(v_1_1.lowerLeft(v_1_1), v_1_1);
        assertEquals(v_1_1.lowerLeft(v_2_2), v_1_1);
        assertEquals(v_1_1.lowerLeft(v_1_2), v_1_1);
        assertEquals(v_1_1.lowerLeft(v_2_1), v_1_1);
        assertEquals(v_1_1.lowerLeft(v_0_1), v_0_1);
        assertEquals(v_1_1.lowerLeft(v_1_0), v_1_0);
        assertEquals(v_1_1.lowerLeft(v_0_2), v_0_1);
        assertEquals(v_1_1.lowerLeft(v_2_0), v_1_0);
    }

    @Test
    void testAdd(){
        Vector2d v_neg3_10 = new Vector2d(-3, 10);

        assertEquals(v_5_7.add(v_neg8_3), v_neg3_10);
        assertEquals(v_neg8_3.add(v_5_7), v_neg3_10);
    }

    @Test
    void testSubtract(){
        Vector2d v_13_4 = new Vector2d(13, 4);

        assertEquals(v_5_7.subtract(v_neg8_3), v_13_4);
    }

    @Test
    void testOpposite(){
        Vector2d v_1_neg1 = new Vector2d(1, -1);
        Vector2d v_neg1_1 = new Vector2d(-1, 1);

        assertEquals(v_1_neg1.opposite(), v_neg1_1);
    }
}
