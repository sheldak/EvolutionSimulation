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
    private final int moveEnergy; // TODO maybe make static?

    private List<IPositionChangeObserver> observers = new ArrayList<>();

    Animal(WorldMap map, int x, int y, MapDirection direction, int[] genome,
                  int energy, int maxEnergy, int moveEnergy){
        this.map = map;
        this.position = new Vector2d(x, y);
        this.animalDirection = direction;

        this.genome = genome;
        this.energy = energy;

        this.maxEnergy = maxEnergy;
        this.moveEnergy = moveEnergy;
    }

    static int[] getRandomGenome() {
        int[] genome = new int[32];
        for (int i=0; i<32; i++) {
            genome[i] = new Random().nextInt(8);
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

    void move(){
        this.updateDirection();
        Vector2d destination = this.position.add(this.animalDirection.toUnitVector());
        destination = this.map.correctDestination(destination);

        if(this.map.canMoveTo(destination)) {
            this.positionChanged(this.getPosition(), destination); // TODO changed -> will change, because not changed yet
            this.position = destination;
        }

        this.energy -= moveEnergy;
    }

    void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer : observers)
            observer.positionChanged(oldPosition, newPosition);
    }

    boolean isDead() {
        return this.energy == 0;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    private void updateDirection(){
        int index = new Random().nextInt(32);

        int intDirection = this.genome[index];

        this.animalDirection = this.animalDirection.change(intDirection);
    }
}
