package world;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldMapTest {
    private WorldMap map = new WorldMap(4, 8, 0.5, 10, 1);

    @Test
    void testIsOccupied() {
        map.placeInitialAnimals(2, 10);

        int counter = 0;

        for (int i=0; i<4; i++) {
            for (int j=0; j<8; j++) {
                if (map.isOccupied(new Vector2d(i, j))) {
                    counter += 1;
                }
            }
        }
        assertEquals(2, counter);
    }

    @Test
    void testObjectAt() {
        map.placeInitialAnimals(2, 0);

        int counter = 0;

        for (int i=0; i<4; i++) {
            for (int j=0; j<8; j++) {
                if (!map.objectsAt(new Vector2d(i, j)).isEmpty()) {
                    counter += 1;
                }
            }
        }
        assertEquals(2, counter);

        map.nextDay();

        for (int i=0; i<4; i++) {
            for (int j=0; j<8; j++) {
                if (!map.objectsAt(new Vector2d(i, j)).isEmpty()) {
                    counter += 1;
                }
            }
        }
        assertEquals(4, counter);
    }

    @ Test
    void testChangePosition() {
        map.placeInitialAnimals(1, 10);

        Vector2d oldPosition = new Vector2d(-1, -1);
        for (int i=0; i<4; i++) {
            for (int j=0; j<8; j++) {
                if (map.isOccupied(new Vector2d(i, j))) {
                    oldPosition = ((Animal) map.objectsAt(new Vector2d(i, j)).toArray()[0]).getPosition();
                }
            }
        }

        map.nextDay();
        for (int i=0; i<4; i++) {
            for (int j=0; j<8; j++) {
                if (map.isOccupied(new Vector2d(i, j)) &&
                        map.objectsAt(new Vector2d(i, j)).toArray()[0] instanceof Animal) {
                    assertNotEquals(oldPosition, new Vector2d(i, j));
                }
            }
        }
    }

    @Test
    void testNextDay() {
        map.placeInitialAnimals(3, 0);

        map.nextDay();
        map.nextDay();

        int counter = 0;
        for (int i=0; i<4; i++) {
            for (int j=0; j<8; j++) {
                if (map.isOccupied(new Vector2d(i, j))) {
                   counter += 1;

                   assertFalse(map.objectsAt(new Vector2d(i, j)).toArray()[0] instanceof Animal);
                }
            }
        }
        assertEquals(4, counter);
    }

    @Test
    void testPlaceGrass() {
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
