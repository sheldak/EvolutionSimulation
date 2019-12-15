package mapElements;

import features.Vector2d;
import org.junit.jupiter.api.Test;
import features.MapDirection;
import map.WorldMap;
import mapElements.Animal;
import features.Genome;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    private WorldMap map = new WorldMap(10, 20, 10, 1);

    @Test
    void testToString(){
        Animal animal_N = new Animal (map, 1, 1, MapDirection.NORTH, new Genome(),
                30, 30);
        Animal animal_NE = new Animal (map, 1, 1, MapDirection.NORTHEAST, new Genome(),
                30, 30);
        Animal animal_E = new Animal (map, 1, 1, MapDirection.EAST, new Genome(),
                30, 30);
        Animal animal_SE = new Animal (map, 1, 1, MapDirection.SOUTHEAST, new Genome(),
                30, 30);
        Animal animal_S = new Animal (map, 1, 1, MapDirection.SOUTH, new Genome(),
                30, 30);
        Animal animal_SW = new Animal (map, 1, 1, MapDirection.SOUTHWEST, new Genome(),
                30, 30);
        Animal animal_W = new Animal (map, 1, 1, MapDirection.WEST, new Genome(),
                30, 30);
        Animal animal_NW = new Animal (map, 1, 1, MapDirection.NORTHWEST, new Genome(),
                30, 30);

        assertEquals(" N ", animal_N.toString());
        assertEquals("N E", animal_NE.toString());
        assertEquals(" E ", animal_E.toString());
        assertEquals("S E", animal_SE.toString());
        assertEquals(" S ", animal_S.toString());
        assertEquals("S W", animal_SW.toString());
        assertEquals(" W ", animal_W.toString());
        assertEquals("N W", animal_NW.toString());
    }

    @Test
    void testMove(){
        int[] genome = new int[32];
        for (int i=0; i<32; i++) genome[i] = 1;

        Animal animal = new Animal (map, 0, 18, MapDirection.WEST, new Genome(genome), 3, 3);
        assertEquals(new Vector2d(0, 18), animal.getPosition());
        assertFalse(animal.isDead());

        animal.move();

        assertEquals(new Vector2d(9, 19), animal.getPosition());
        assertFalse(animal.isDead());

        animal.move();

        assertEquals(new Vector2d(9, 0), animal.getPosition());
        assertFalse(animal.isDead());

        animal.move();

        assertEquals(new Vector2d(0, 1), animal.getPosition());
        assertTrue(animal.isDead());
    }

    @Test
    void testReproduce() {
        Animal animalA = new Animal(map, 1, 1, MapDirection.NORTH, new Genome(), 16, 20);
        Animal animalB = new Animal(map, 1, 1, MapDirection.NORTH, new Genome(), 12, 20);

        Animal babyAnimal = animalA.reproduce(animalB, new Vector2d(2, 2));

        assertEquals(7, babyAnimal.getEnergy());
    }
}
