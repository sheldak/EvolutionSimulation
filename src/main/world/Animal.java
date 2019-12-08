package world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Animal implements IMapElement {
    private WorldMap map;
    private Vector2d position;
    private MapDirection animalDirection;

    private final int[] genome;
    private int energy;

    private final int maxEnergy;

    private List<IPositionChangeObserver> observers = new ArrayList<>();

    Animal(WorldMap map, int x, int y, MapDirection direction, int[] genome, int energy, int maxEnergy){
        this.map = map;
        this.position = new Vector2d(x, y);
        this.animalDirection = direction;

        this.genome = genome;
        this.energy = energy;

        this.maxEnergy = maxEnergy;
    }

    static int[] getRandomGenome() {
        int[] genome = new int[32];

        int[] genesOccurrence = new int[8];
        for (int i=0; i<8; i++) genesOccurrence[i] = 0;

        for (int i=0; i<32; i++) {
            genome[i] = new Random().nextInt(8);
            genesOccurrence[genome[i]] += 1;
        }

        return correctGenome(genome, genesOccurrence);
    }

    static int[] crossingOver(int[] genomeA, int[] genomeB) {
        int divisionPointA = new Random().nextInt(30) + 1; // from 1 to 30
        int divisionPointB = new Random().nextInt(
                31 - divisionPointA) + divisionPointA + 1; // from divisionPointA + 1 to 31

        int[] newGenome = new int[32];
        int[] genesOccurrence = new int[8];
        for (int i=0; i<8; i++) genesOccurrence[i] = 0;

        for (int i=0; i<32; i++){
            if (i < divisionPointA)
                newGenome[i] = genomeA[i];
            else if (i < divisionPointB)
                newGenome[i] = genomeB[i];
            else
                newGenome[i] = genomeA[i];

            genesOccurrence[newGenome[i]] += 1;
        }

        return correctGenome(newGenome, genesOccurrence);
    }

    static int[] correctGenome(int[] genome, int[] genesOccurrence) { // to ensure having all types of genes
        for (int i=0; i<8; i++) {
            if (genesOccurrence[i] == 0) {
                int changeIndex = new Random().nextInt(32);
                while (genesOccurrence[genome[changeIndex]] == 0)
                    changeIndex += 1;
                genome[changeIndex] = i;
            }
        }

        Arrays.sort(genome);

        return genome;
    }

    public String toString(){
        switch(animalDirection){
            case NORTH:
                return " N ";
            case NORTHEAST:
                return "N E";
            case EAST:
                return " E ";
            case SOUTHEAST:
                return "S E";
            case SOUTH:
                return " S ";
            case SOUTHWEST:
                return "S W";
            case WEST:
                return " W ";
            case NORTHWEST:
                return "N W";
            default:
                return "Error";
        }
    }

    Vector2d move(){
        this.updateDirection();
        Vector2d destination = this.position.add(this.animalDirection.toUnitVector());
        destination = this.map.correctDestination(destination);

        this.changePosition(this.getPosition(), destination);
        this.position = destination;

        this.energy -= WorldMap.moveEnergy;

        return destination;
    }

    void consumeGrass(int plantEnergy) {
        this.energy += plantEnergy;

        if (this.energy > this.maxEnergy)
            this.energy = this.maxEnergy;
    }

    boolean hasMinimumReproductionEnergy() {
        return this.energy >= this.maxEnergy/2;
    }

    Animal reproduce(Animal otherAnimal) {
        Vector2d babyPosition = this.map.getBabyPosition(this.position);
        int[] babyGenome = Animal.crossingOver(this.genome, otherAnimal.genome);
        Animal babyAnimal = new Animal(this.map, babyPosition.getX(), babyPosition.getY(),
                MapDirection.getRandomDirection(), babyGenome, this.energy/4 + otherAnimal.energy/4,
                this.maxEnergy);

        this.energy -= this.energy/4;
        otherAnimal.energy -= otherAnimal.energy/4;

        return babyAnimal;
    }

    void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    boolean isDead() {
        return this.energy == 0;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public int getEnergy() {
        return this.energy;
    }

    private void changePosition(Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer : observers)
            observer.changePosition(oldPosition, newPosition, this);
    }

    private void updateDirection(){
        int index = new Random().nextInt(32);

        int intDirection = this.genome[index];

        this.animalDirection = this.animalDirection.change(intDirection);
    }
}
