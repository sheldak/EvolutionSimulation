package world;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldMapTest {
    private WorldMap map = new WorldMap(10, 20, 0.4, 10);

//    @Test
//    void runTest() {
//
//    }
//
//    @Test
//    void isOccupiedTest() {
//        GrassField map = new GrassField(1);
//        Vector2d grassPos = new Vector2d(-1, -1);
//        for (int i = 0; i < 3; i += 1) {
//            for (int j = 0; j < 3; j += 1) {
//                if (map.isOccupied(new Vector2d(i, j)))
//                    grassPos = new Vector2d(i, j);
//            }
//        }
//
//        assertTrue(map.isOccupied(grassPos));
//        Animal animal = new Animal(map, grassPos.x, grassPos.y);
//        map.place(animal);
//
//        for (int i = 0; i < 3; i += 1) {
//            for (int j = 0; j < 3; j += 1) {
//                if (map.isOccupied(new Vector2d(i, j)) && map.objectAt(new Vector2d(i, j)) instanceof Grass)
//                    grassPos = new Vector2d(i, j);
//            }
//        }
//
//        for (int i = 0; i < 3; i += 1) {
//            for (int j = 0; j < 3; j += 1) {
//                if (i == grassPos.x && j == grassPos.y || i == animal.getPosition().x && j == animal.getPosition().y)
//                    assertTrue(map.isOccupied(new Vector2d(i, j)));
//                else
//                    assertFalse(map.isOccupied(new Vector2d(i, j)));
//            }
//        }
//    }
//
//    @Test
//    void objectAtTest() {
//        GrassField map = new GrassField(1);
//
//        Animal animal_0_0 = new Animal(map, 0, 0);
//        map.place(animal_0_0);
//
//        assertEquals(map.objectAt(new Vector2d(0, 0)), animal_0_0);
//    }
//
//    @Test
//    void placeTest() {
//        GrassField map = new GrassField(1);
//        map.place(new Animal(map, 1, 1));
//
//        for (int i = 0; i <= 2; i++) {
//            for (int j = 0; j <= 1; j++) {
//                if (i != 1 && j != 1)
//                    assertTrue(map.place(new Animal(map, i, j)));
//            }
//        }
//    }

//    @Test
//    void consumeGrassTest() {
//        GrassField map = new GrassField(1);
//        int grassPosX = -1;
//        int grassPosY = -1;
//
//        for (int i = 0; i < 3; i += 1) {
//            for (int j = 0; j < 3; j += 1) {
//                if (map.isOccupied(new Vector2d(i, j))) {
//                    grassPosX = i;
//                    grassPosY = j;
//                }
//            }
//        }
//
//        assertTrue(map.isOccupied(new Vector2d(grassPosX, grassPosY)));
//        Animal animal = new Animal(map, grassPosX, grassPosY - 1);
//        map.place(animal);
//
//        assertTrue(map.isOccupied(new Vector2d(grassPosX, grassPosY)));
//        assertTrue(map.isOccupied(new Vector2d(grassPosX, grassPosY - 1)));
//
//        MoveDirection[] directions = new MoveDirection[2];
//        directions[0] = MoveDirection.FORWARD;
//        directions[1] = MoveDirection.FORWARD;
//        map.run(directions);
//
//        assertFalse(map.isOccupied(new Vector2d(grassPosX, grassPosY)));
//    }
//

}
