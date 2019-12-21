package map;

import features.Genome;
import features.MapDirection;
import mapElements.Grass;
import org.junit.jupiter.api.Test;
import features.Vector2d;
import mapElements.Animal;

import static org.junit.jupiter.api.Assertions.*;

class WorldMapTest {
    private WorldMap map = new WorldMap(4, 8, 30, 10, 30);
    private WorldBuilder worldBuilder = new WorldBuilder(map);

    private Animal animal_1_1 = new Animal(map, 1, 1, MapDirection.getRandomDirection(), new Genome(), 10);

    private Grass grass_2_2 = new Grass(2, 2);

    @Test
    void testStartWorld() {
        map.startWorld(worldBuilder, 0.5, 15);

        int animalsCounter = 0;

        for(int i=0; i<4; i++) {
            for (int j=0; j<8; j++) {
                if (map.isOccupied(new Vector2d(i, j)))
                    animalsCounter += 1;
            }
        }
        assertEquals(15, animalsCounter);
    }

    @Test
    void testRun() {
        int[] genomeArrayA = new int[32];
        int[] genomeArrayB = new int[32];

        for(int i=0; i<32; i++) {
            genomeArrayA[i] = 0;
            genomeArrayB[i] = 2;
        }

        Animal animal_0_0 = new Animal(map, 0, 0, MapDirection.NORTHEAST,
                new Genome(genomeArrayA), 30);

        Animal animal_3_5 = new Animal(map, 3, 5, MapDirection.WEST,
                new Genome(genomeArrayB), 30);

        map.addAnimal(animal_0_0);
        map.addAnimal(animal_3_5);

        map.run();
        map.run();
        map.run();

        for (int i=0; i<4; i++) {
            for (int j=0; j<8; j++) {
                if (i==3 && j==3 || i==0 && j==5)
                    assertTrue(map.isOccupied(new Vector2d(i, j)));
                else {
                    assertFalse(map.isOccupied(new Vector2d(i, j)));
                }

            }
        }
    }

    @Test
    void testIsOccupied() {
        map.addAnimal(animal_1_1);

        for (int i=0; i<4; i++) {
            for (int j=0; j<8; j++) {
                if (i == 1 && j == 1)
                    assertTrue(map.isOccupied(new Vector2d(i, j)));
                else
                    assertFalse(map.isOccupied(new Vector2d(i, j)));
            }
        }
    }

    @Test
    void testObjectsAt() {
        map.addAnimal(animal_1_1);
        map.addGrass(grass_2_2);

        for (int i=0; i<4; i++) {
            for (int j=0; j<8; j++) {
                if (i == 1 && j == 1) {
                    for (Object object : map.objectsAt(new Vector2d(i, j))) {
                        assertEquals(animal_1_1, object);
                    }
                }
                else if (i == 2 && j == 2) {
                    for (Object object : map.objectsAt(new Vector2d(i, j))) {
                        assertEquals(grass_2_2, object);
                    }
                }
                else
                    assertEquals(0, map.objectsAt(new Vector2d(i, j)).size());
            }
        }
    }

    @Test
    void testChangePosition() {
        map.startWorld(worldBuilder, 0.5, 1);

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
                    System.out.println((new Vector2d(i, j)).toString());
                    System.out.println(oldPosition.toString());
                    assertNotEquals(oldPosition, new Vector2d(i, j));
                }
            }
        }
    }

    @Test
    void testCorrectDestination() {
        Vector2d v_10_10 = new Vector2d(10, 10);
        Vector2d correctedVector = map.correctDestination(v_10_10);

        assertEquals(new Vector2d(2, 2) ,correctedVector);
    }

    @Test
    void testNextDay() {
        map.startWorld(worldBuilder, 0.5, 0);

        Animal animal_1_3 = new Animal(map, 1, 3, MapDirection.getRandomDirection(), new Genome(), 10);
        map.addAnimal(animal_1_3);

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
    void testAddAnimal() {
        map.addAnimal(animal_1_1);

        for (int i=0; i<4; i++) {
            for (int j=0; j<8; j++) {
                if (i==1 && j==1)
                    assertEquals(animal_1_1, map.objectsAt(new Vector2d(i, j)).toArray()[0]);
                else
                    assertFalse(map.isOccupied(new Vector2d(i, j)));
            }
        }
    }

    @Test
    void testRemoveAnimal() {
        map.addAnimal(animal_1_1);

        Animal animal_1_3 = new Animal(map, 1, 3, MapDirection.getRandomDirection(), new Genome(), 10);
        map.addAnimal(animal_1_3);

        map.startWorld(worldBuilder, 0.5 ,0);
        assertEquals(2, map.getNumberOfAnimals());

        map.removeAnimal(animal_1_1);
        assertEquals(1, map.getNumberOfAnimals());

        map.nextDay();
        map.nextDay();
        assertEquals(0, map.getNumberOfAnimals());
        assertEquals(4, map.getAmountOfGrass());
    }

    @Test
    void testAddGrass() {
        map.addGrass(grass_2_2);

        for (int i=0; i<4; i++) {
            for (int j=0; j<8; j++) {
                if (i==2 && j==2)
                    assertEquals(grass_2_2, map.objectsAt(new Vector2d(i, j)).toArray()[0]);
                else
                    assertFalse(map.isOccupied(new Vector2d(i, j)));
            }
        }
    }

    @Test
    void testDominantGenome() {
        int[] genomeArray = new int[32];
        for (int i=0; i<32; i++) genomeArray[i] = 2;
        map.addGenome(new Genome(genomeArray));

        assertEquals(new Genome(genomeArray), map.getDominantGenome());

        map.removeGenome(new Genome(genomeArray));

        assertNull(map.getDominantGenome());
    }

    @Test
    void testGetNumberOfAnimals() {
        map.addAnimal(animal_1_1);

        map.startWorld(worldBuilder, 0.5, 5);
        assertEquals(6, map.getNumberOfAnimals());
    }

    @Test
    void testGetAmountOfGrass() {
        map.addGrass(grass_2_2);

        map.startWorld(worldBuilder, 0.5, 0);

        map.nextDay();
        map.nextDay();
        map.nextDay();

        assertEquals(7, map.getAmountOfGrass());
    }

    @Test
    void testGetDominantGene() {
        int[] genome1 = new int[32];
        int[] genome2 = new int[32];

        for(int i=0; i<32; i++) {
            genome1[i] = 1;
            genome2[i] = 2;
        }

        Animal animalA = new Animal(map, 1, 1, MapDirection.NORTH, new Genome(genome1), 100);
        Animal animalB = new Animal(map, 1, 1, MapDirection.NORTH, new Genome(genome2), 100);
        Animal animalC = new Animal(map, 1, 1, MapDirection.NORTH, new Genome(genome2), 100);

        map.startWorld(worldBuilder, 0.5, 0);
        map.addAnimal(animalA);
        map.addAnimal(animalB);
        map.addAnimal(animalC);

        assertEquals(2, map.getDominantGene());

        Animal animalD = new Animal(map, 1, 1, MapDirection.NORTH, new Genome(genome1), 100);
        Animal animalE = new Animal(map, 1, 1, MapDirection.NORTH, new Genome(genome1), 100);

        map.addAnimal(animalD);
        map.addAnimal(animalE);

        assertEquals(1, map.getDominantGene());
    }

    @Test
    void testGetAverageAnimalsEnergy() {
        int[] genomeArray = new int[32];
        for (int i=0; i<32; i++) genomeArray[i] = 0;
        Genome genome = new Genome(genomeArray);

        Animal animal_2_1 = new Animal(map, 2, 1, MapDirection.NORTH,genome, 40);

        Animal animal_2_3 = new Animal(map, 2, 1, MapDirection.SOUTH, genome, 30);

        Animal animal_0_0 = new Animal(map, 0, 0, MapDirection.SOUTH, genome, 80);

        map.startWorld(worldBuilder, 0.5, 0);

        map.addAnimal(animal_2_1);
        map.addAnimal(animal_2_3);
        map.addAnimal(animal_0_0);
        map.addGrass(grass_2_2);

        assertEquals(50, map.getAverageAnimalsEnergy());

        map.nextDay();

        assertEquals(50, map.getAverageAnimalsEnergy());
    }

    @Test
    void testGetAverageAnimalsLifetime() {
        WorldMap currMap = new WorldMap(4, 8, 30, 10, 100);
        WorldBuilder currWorldBuilder = new WorldBuilder(currMap);

        Animal animal_0_0 = new Animal(currMap, 0, 0, MapDirection.NORTH, new Genome(), 40);
        Animal animal_1_2 = new Animal(currMap, 1, 2, MapDirection.NORTH, new Genome(), 40);
        Animal animal_3_3 = new Animal(currMap, 3, 3, MapDirection.SOUTH, new Genome(), 40);

        currMap.startWorld(currWorldBuilder, 0.5, 0);
        currMap.addAnimal(animal_0_0);
        currMap.addAnimal(animal_1_2);
        currMap.addAnimal(animal_3_3);

        currMap.nextDay();

        currMap.removeAnimal(animal_0_0);
        assertEquals(1, currMap.getAverageAnimalsLifetime());

        currMap.nextDay();

        currMap.removeAnimal(animal_1_2);
        assertEquals(1, currMap.getAverageAnimalsLifetime());

        currMap.nextDay();

        currMap.removeAnimal(animal_3_3);
        assertEquals(2, currMap.getAverageAnimalsLifetime());
    }

    @Test
    void testCheckGrassConsumptionAndReproduction() {
        int[] genomeArray =
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        Animal animal_2_2 = new Animal(map, 2, 2, MapDirection.NORTH, new Genome(genomeArray), 30);

        Animal animal_2_4 = new Animal(map, 2, 4, MapDirection.SOUTH, new Genome(genomeArray), 40);

        Grass grass_2_3 = new Grass(2, 3);

        map.startWorld(worldBuilder, 0.5, 0);

        map.addAnimal(animal_2_2);
        map.addAnimal(animal_2_4);
        map.addGrass(grass_2_3);

        map.run();

        assertTrue(map.isOccupied(new Vector2d(2, 3)));
        for (Object object : map.objectsAt(new Vector2d(2, 3))) {
            assertTrue(object instanceof Animal);
            if (object == animal_2_4) {
                assertEquals(45, ((Animal) object).getEnergy());
            }
            else if (object == animal_2_2) {
                assertEquals(15, ((Animal) object).getEnergy());
            }
        }

        int counter = 0;
        for (Object ignored : map.objectsAt(new Vector2d(2, 3)))
            counter += 1;

        assertEquals(2, counter);

        // checking if there is new animal
        counter = 0;
        for (int i=0; i<8; i++) {
            if (map.isOccupied((new Vector2d(2, 3)).add(MapDirection.NORTH.change(i).toUnitVector())))
                counter += 1;
        }
        assertEquals(1, counter);
    }

    @Test
    void testGetRidOfDeadAnimals() {
        map.startWorld(worldBuilder, 0.5, 0);
        map.addAnimal(animal_1_1);

        map.nextDay();
        map.nextDay();

        for(int i=0; i<4; i++) {
            for (int j=0; j<8; j++) {
                if (map.isOccupied(new Vector2d(i, j))) {
                    for (Object object : map.objectsAt(new Vector2d(i, j)))
                        assertFalse(object instanceof Animal);
                }
            }
        }
    }
}
