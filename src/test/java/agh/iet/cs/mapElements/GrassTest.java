package agh.iet.cs.mapElements;

import org.junit.jupiter.api.Test;
import agh.iet.cs.features.Vector2d;
import agh.iet.cs.mapElements.Grass;

import static org.junit.jupiter.api.Assertions.*;

class GrassTest {
    private Grass grass = new Grass(2, 2);

    @Test
    void testToString() {
        assertEquals(" * ", grass.toString());
    }

    @Test
    void testGetPosition() {
        assertEquals(new Vector2d(2, 2), grass.getPosition());
    }
}
