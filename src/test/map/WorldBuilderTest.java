package map;

import features.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorldBuilderTest {
    private WorldMap map = new WorldMap(4, 8, 50, 10, 30);
    WorldBuilder worldBuilder = new WorldBuilder(map);

    @Test
    void testPlaceGrass() {
        map.startWorld(worldBuilder, 0.5f, 0);
        map.nextDay();
        map.nextDay();
        map.nextDay();

        int counterSavannah = 0;
        int counterJungle = 0;
        for (int i=0; i<4; i++) {
            for (int j=0; j<8; j++) {
                if (map.isOccupied(new Vector2d(i, j))) {
                    if (i >= 1 && i <= 2 && j >= 2 & j <= 5)
                        counterJungle += 1;
                    else
                        counterSavannah += 1;
                }
            }
        }

        assertEquals(3, counterJungle);
        assertEquals(3, counterSavannah);
    }
}
