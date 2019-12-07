package world;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    private WorldMap map = new WorldMap(10, 20, 0.4, 10);

    @Test
    void testGetRandomGenome() {
        int[] genome = Animal.getRandomGenome();

        assertTrue(genome[0] >= 0 && genome[0] <= 7);
        for (int i=1; i<32; i++) {
            assertTrue(genome[i] >= 0 && genome[i] <= 7);
            assertTrue(genome[i] >= genome[i-1]);
        }

        int[] genesOccurrence = new int[8];
        for (int i=0; i<32; i++) {
            genesOccurrence[genome[i]] += 1;
        }

        for (int i=0; i<8; i++) {
            assertTrue(genesOccurrence[i] > 0);
        }
    }

    @Test
    void testToString(){
        Animal animal_N = new Animal (map, 1, 1, MapDirection.NORTH, Animal.getRandomGenome(),
                30, 30, 1);
        Animal animal_NE = new Animal (map, 1, 1, MapDirection.NORTHEAST, Animal.getRandomGenome(),
                30, 30, 1);
        Animal animal_E = new Animal (map, 1, 1, MapDirection.EAST, Animal.getRandomGenome(),
                30, 30, 1);
        Animal animal_SE = new Animal (map, 1, 1, MapDirection.SOUTHEAST, Animal.getRandomGenome(),
                30, 30, 1);
        Animal animal_S = new Animal (map, 1, 1, MapDirection.SOUTH, Animal.getRandomGenome(),
                30, 30, 1);
        Animal animal_SW = new Animal (map, 1, 1, MapDirection.SOUTHWEST, Animal.getRandomGenome(),
                30, 30, 1);
        Animal animal_W = new Animal (map, 1, 1, MapDirection.WEST, Animal.getRandomGenome(),
                30, 30, 1);
        Animal animal_NW = new Animal (map, 1, 1, MapDirection.NORTHWEST, Animal.getRandomGenome(),
                30, 30, 1);

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

        Animal animal = new Animal (map, 0, 18, MapDirection.WEST, genome,
                6, 6, 2);
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


}
