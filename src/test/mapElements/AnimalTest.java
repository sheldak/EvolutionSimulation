package mapElements;

import features.Vector2d;
import map.WorldBuilder;
import org.junit.jupiter.api.Test;
import features.MapDirection;
import map.WorldMap;
import features.Genome;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    private WorldMap map = new WorldMap(10, 20, 30, 10, 30);
    WorldBuilder worldBuilder = new WorldBuilder(map);

    @Test
    void testToString(){
        Animal animal_N = new Animal (map, 1, 1, MapDirection.NORTH, new Genome(),
                30);
        Animal animal_NE = new Animal (map, 1, 1, MapDirection.NORTHEAST, new Genome(),
                30);
        Animal animal_E = new Animal (map, 1, 1, MapDirection.EAST, new Genome(),
                30);
        Animal animal_SE = new Animal (map, 1, 1, MapDirection.SOUTHEAST, new Genome(),
                30);
        Animal animal_S = new Animal (map, 1, 1, MapDirection.SOUTH, new Genome(),
                30);
        Animal animal_SW = new Animal (map, 1, 1, MapDirection.SOUTHWEST, new Genome(),
                30);
        Animal animal_W = new Animal (map, 1, 1, MapDirection.WEST, new Genome(),
                30);
        Animal animal_NW = new Animal (map, 1, 1, MapDirection.NORTHWEST, new Genome(),
                30);

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

        Animal animal = new Animal (map, 0, 18, MapDirection.WEST, new Genome(genome), 30);
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
        Animal animalA = new Animal(map, 1, 1, MapDirection.NORTH, new Genome(), 16);
        Animal animalB = new Animal(map, 1, 1, MapDirection.NORTH, new Genome(), 12);

        Animal babyAnimal = animalA.reproduce(animalB, new Vector2d(2, 2));

        assertEquals(7, babyAnimal.getEnergy());
    }

    @Test
    void testGetAgeAndGetDeathTime() {
        Animal animal_1_1 = new Animal(map, 1, 1, MapDirection.NORTH, new Genome(), 10);
        map.startWorld(worldBuilder, 0.5, 0);

        map.addAnimal(animal_1_1);
        assertEquals(0, animal_1_1.getAge());

        map.nextDay();
        assertEquals(1, animal_1_1.getAge());

        map.nextDay();
        assertEquals(2, animal_1_1.getDeathTime());
        assertEquals(2, animal_1_1.getAge());
    }

    @Test
    void testGetNumberOfChildrenAndOffspring() {
        Vector2d v = new Vector2d(1, 1);

        Animal animalA = new Animal(map, 1, 1, MapDirection.NORTH, new Genome(), 100);
        Animal animalB = new Animal(map, 1, 1, MapDirection.NORTH, new Genome(), 100);
        Animal animalC = new Animal(map, 1, 1, MapDirection.NORTH, new Genome(), 100);
        Animal animalD = new Animal(map, 1, 1, MapDirection.NORTH, new Genome(), 100);

        Animal animalE = animalA.reproduce(animalB, v);
        Animal animalF = animalB.reproduce(animalC, v);
        Animal animalG = animalA.reproduce(animalD, v);
        Animal animalH = animalF.reproduce(animalG, v);
        Animal animalI = animalB.reproduce(animalH, v);
        Animal animalJ = animalE.reproduce(animalI, v);

        assertEquals(2, animalA.getNumberOfChildren());
        assertEquals(3, animalB.getNumberOfChildren());
        assertEquals(1, animalC.getNumberOfChildren());
        assertEquals(1, animalD.getNumberOfChildren());
        assertEquals(1, animalE.getNumberOfChildren());
        assertEquals(1, animalF.getNumberOfChildren());
        assertEquals(1, animalG.getNumberOfChildren());
        assertEquals(1, animalH.getNumberOfChildren());
        assertEquals(1, animalI.getNumberOfChildren());
        assertEquals(0, animalJ.getNumberOfChildren());

        assertEquals(5, animalA.getNumberOfOffspring());
        assertEquals(5, animalB.getNumberOfOffspring());
        assertEquals(4, animalC.getNumberOfOffspring());
        assertEquals(4, animalD.getNumberOfOffspring());
        assertEquals(1, animalE.getNumberOfOffspring());
        assertEquals(3, animalF.getNumberOfOffspring());
        assertEquals(3, animalG.getNumberOfOffspring());
        assertEquals(2, animalH.getNumberOfOffspring());
        assertEquals(1, animalI.getNumberOfOffspring());
        assertEquals(0, animalJ.getNumberOfOffspring());
    }
}
