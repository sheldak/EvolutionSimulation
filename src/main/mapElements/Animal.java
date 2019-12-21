package mapElements;

import features.Genome;
import features.MapDirection;
import features.Vector2d;
import map.IPositionChangeObserver;
import map.WorldMap;

import java.util.*;

public class Animal implements IMapElement {
    // class containing animal features
    public static int moveEnergy;
    public static int startEnergy;

    private WorldMap map;
    private Vector2d position;
    private MapDirection animalDirection;

    private final Genome genome;
    private int energy;

    private int age;
    private int deathTime;

    private List<Animal> children = new ArrayList<>();

    private List<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(WorldMap map, int x, int y, MapDirection direction, Genome genome, int energy){
        // setting initial variables
        this.map = map;
        this.position = new Vector2d(x, y);
        this.animalDirection = direction;

        this.genome = genome;
        this.energy = energy;

        this.age = 0;
    }

    public String toString(){
        switch(this.animalDirection){
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

    public Vector2d move() { // changing position depending on genome
        this.updateDirection();
        Vector2d destination = this.position.add(this.animalDirection.toUnitVector());
        destination = this.map.correctDestination(destination);

        this.changePosition(this.getPosition(), destination);
        this.position = destination;

        this.energy -= Animal.moveEnergy;

        return destination;
    }

    public void consumeGrass(int plantEnergy) {
        this.energy += plantEnergy;
    }

    public boolean hasHigherThanStartEnergy() {
        // color green on the map
        return this.energy >= Animal.startEnergy;
    }

    public boolean hasMinimumReproductionEnergy()
    {   // color yellow on the map; red if the animal has less than startEnergy/2
        return this.energy >= Animal.startEnergy/2;
    }

    public Animal reproduce(Animal otherAnimal, Vector2d babyPosition) {
        Genome babyGenome = this.genome.crossingOver(otherAnimal.genome);
        Animal babyAnimal = new Animal(this.map, babyPosition.getX(), babyPosition.getY(),
                MapDirection.getRandomDirection(), babyGenome, this.energy/4 + otherAnimal.energy/4);

        this.energy -= this.energy/4;
        otherAnimal.energy -= otherAnimal.energy/4;

        this.addChild(babyAnimal);
        otherAnimal.addChild(babyAnimal);

        return babyAnimal;
    }

    public void makeOlder() {
        this.age += 1;
    }

    public void kill(int currentDay) {
        this.deathTime = currentDay;
    }

    public void addChild(Animal child) {
        this.children.add(child);
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    public boolean isDead() {
        return this.energy <= 0;
    }

    // getters
    public Vector2d getPosition(){
        return this.position;
    }

    public Genome getGenome() {
        return  this.genome;
    }

    public int[] getGenomeArray() {
        return this.genome.getArray();
    }

    public int getEnergy() {
        return this.energy;
    }

    public int getAge() {
        return this.age;
    }

    public int getDeathTime() {
        return this.deathTime;
    }

    public int getNumberOfChildren() {
        return this.children.size();
    }

    public int getNumberOfOffspring() {
        Set<Animal> offspring = new HashSet<>();

        for (Animal child : this.children){
            offspring.add(child);
            child.getOffspring(offspring);
        }

        return offspring.size();
    }

    private void getOffspring(Set<Animal> offspring) {
        // recursive function to find every offspring
        for (Animal child : this.children) {
            offspring.add(child);
            child.getOffspring(offspring);
        }
    }

    private void changePosition(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : observers)
            observer.changePosition(oldPosition, newPosition, this);
    }

    private void updateDirection() {
        // change in direction depends on genes
        int intDirection = this.genome.getRandomGene();

        this.animalDirection = this.animalDirection.change(intDirection);
    }
}
