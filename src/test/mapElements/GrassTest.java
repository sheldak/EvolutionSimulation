package mapElements;

import org.junit.jupiter.api.Test;
import features.Vector2d;
import mapElements.Grass;

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
