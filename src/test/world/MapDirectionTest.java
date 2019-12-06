package world;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {
    @Test
    void testGetRandomDirection() {
        for (int i=0; i<8; i++) {
            MapDirection rand = MapDirection.getRandomDirection();
            assertTrue(rand == MapDirection.NORTH || rand == MapDirection.NORTHEAST ||
                    rand == MapDirection.EAST || rand == MapDirection.SOUTHEAST ||
                    rand == MapDirection.SOUTH || rand == MapDirection.SOUTHWEST ||
                    rand == MapDirection.WEST|| rand == MapDirection.NORTHWEST);
        }
    }

    @Test
    void testToString() {
        assertEquals("North", MapDirection.NORTH.toString());
        assertEquals("Northeast", MapDirection.NORTHEAST.toString());
        assertEquals("East", MapDirection.EAST.toString());
        assertEquals("Southeast", MapDirection.SOUTHEAST.toString());
        assertEquals("South", MapDirection.SOUTH.toString());
        assertEquals("Southwest", MapDirection.SOUTHWEST.toString());
        assertEquals("West", MapDirection.WEST.toString());
        assertEquals("Northwest", MapDirection.NORTHWEST.toString());

    }

    @Test
    void testChange() {
        assertEquals(MapDirection.NORTH.change(0), MapDirection.NORTH);
        assertEquals(MapDirection.NORTHEAST.change(1), MapDirection.EAST);
        assertEquals(MapDirection.EAST.change(2), MapDirection.SOUTH);
        assertEquals(MapDirection.SOUTHEAST.change(3), MapDirection.WEST);
        assertEquals(MapDirection.SOUTH.change(4), MapDirection.NORTH);
        assertEquals(MapDirection.SOUTHWEST.change(5), MapDirection.EAST);
        assertEquals(MapDirection.WEST.change(6), MapDirection.SOUTH);
        assertEquals(MapDirection.NORTHWEST.change(7), MapDirection.WEST);
    }

    @Test
    void testToUnitVector() {
        Vector2d v_0_1 = new Vector2d(0, 1);
        Vector2d v_1_1 = new Vector2d(1, 1);
        Vector2d v_1_0 = new Vector2d(1, 0);
        Vector2d v_1_neg1 = new Vector2d(1, -1);
        Vector2d v_0_neg1 = new Vector2d(0, -1);
        Vector2d v_neg1_neg1 = new Vector2d(-1, -1);
        Vector2d v_neg1_0 = new Vector2d(-1, 0);
        Vector2d v_neg1_1 = new Vector2d(-1, 1);
        assertEquals(MapDirection.NORTH.toUnitVector(), v_0_1);
        assertEquals(MapDirection.NORTHEAST.toUnitVector(), v_1_1);
        assertEquals(MapDirection.EAST.toUnitVector(), v_1_0);
        assertEquals(MapDirection.SOUTHEAST.toUnitVector(), v_1_neg1);
        assertEquals(MapDirection.SOUTH.toUnitVector(), v_0_neg1);
        assertEquals(MapDirection.SOUTHWEST.toUnitVector(), v_neg1_neg1);
        assertEquals(MapDirection.WEST.toUnitVector(), v_neg1_0);
        assertEquals(MapDirection.NORTHWEST.toUnitVector(), v_neg1_1);
    }

}
