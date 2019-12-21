package map;

import features.Genome;
import features.Vector2d;
import mapElements.Animal;
import mapElements.Grass;

import java.util.*;

public class WorldMap implements IPositionChangeObserver {
    // class represents one map
    private final int width;
    private final int height;

    private final int plantEnergy;

    private WorldBuilder worldBuilder;

    private List<Animal> animals = new ArrayList<>();
    private List<Grass> grasses = new ArrayList<>();
    private Map<Vector2d, List<Object>> elementsMap = new HashMap<>();
    private Map<Genome, Integer> genomes = new HashMap<>();

    private List<Animal> deadAnimals = new ArrayList<>();

    private int currentDay;

    public WorldMap(int width, int height, int plantEnergy, int moveEnergy, int startEnergy) {
        // setting map parameters got from json file
        this.width = width;
        this.height = height;

        this.plantEnergy = plantEnergy;
        Animal.moveEnergy = moveEnergy;
        Animal.startEnergy = startEnergy;

        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                List<Object> newList = new ArrayList<>();
                elementsMap.put(new Vector2d(i, j), newList);
            }
        }
    }

    public void startWorld(WorldBuilder worldBuilder, double jungleRatio, int numberOfAnimals) {
        // using world builder to set start conditions
        this.worldBuilder = worldBuilder;

        worldBuilder.passMapSize(this.width, this.height);
        worldBuilder.placeJungle(jungleRatio);
        worldBuilder.placeInitialAnimals(numberOfAnimals);

        this.currentDay = 0;
    }

    public void run() {
        // all alive animals are changing position
        Set <Vector2d> toCheck = new HashSet<>();
        for (Animal animal : this.animals) {
            toCheck.add(animal.move());
        }

        this.checkGrassConsumption(toCheck);
        this.checkReproduction(toCheck);
    }

    public boolean isOccupied(Vector2d position) {
        // checking if the place is occupied
        return !this.objectsAt(position).isEmpty();
    }

    public List<Object> objectsAt(Vector2d position) {
        // returning list of all objects at this position
        return this.elementsMap.get(position);
    }

    @Override
    public void changePosition(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        // to take animals from old position to the new one and save it in elementsMap
        this.elementsMap.get(oldPosition).remove(animal);
        this.elementsMap.get(newPosition).add(animal);
    }

    public Vector2d correctDestination(Vector2d destination) {
        // world has no borders; reaching end of the map the animal is appearing on the other side
        return new Vector2d((destination.getX() + this.width) % this.width,
                (destination.getY() + this.height) % this.height);
    }

    public void nextDay(){
        // one day contains making animals older, getting rid of dead ones, moving, eating and reproduction
        this.currentDay += 1;

        for(Animal animal : this.animals)
            animal.makeOlder();

        this.getRidOfDeadAnimals();

        this.run();

        this.worldBuilder.placeGrassInJungle();
        this.worldBuilder.placeGrassInSavannah();
    }

    public void addAnimal(Animal animal) { // adding animal to the list of animals and map of world elements
        animal.addObserver(this);
        this.animals.add(animal);
        this.elementsMap.get(animal.getPosition()).add(animal);

        this.addGenome(animal.getGenome());
    }

    public void removeAnimal(Animal animal) { // removing animal from the list of animals and map of world elements
        animal.kill(currentDay);

        this.elementsMap.get(animal.getPosition()).remove(animal);
        this.animals.remove(animal);

        this.deadAnimals.add(animal);

        this.removeGenome(animal.getGenome());
    }

    public void addGrass(Grass grass) { // adding grass to the list and map
        this.grasses.add(grass);
        this.elementsMap.get(grass.getPosition()).add(grass);
    }

    public void addGenome(Genome genome) {  // adding new genome to map with all genomes (to get dominant genome later)
        if (this.genomes.containsKey(genome)) {
            int genomeOccurrence = this.genomes.get(genome);
            this.genomes.remove(genome);
            this.genomes.put(genome, genomeOccurrence + 1);
        }
        else
            this.genomes.put(genome, 1);
    }

    public void removeGenome(Genome genome) {   // removing genome from map with all genomes in the world
        if (this.genomes.containsKey(genome)) {
            int genomeOccurrence = this.genomes.get(genome);

            this.genomes.remove(genome);

            if (genomeOccurrence >= 2)
                this.genomes.put(genome, genomeOccurrence - 1);
        }
    }

    // getters
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getCurrentDay() {
        return this.currentDay;
    }

    public int getNumberOfAnimals() {
        return this.animals.size();
    }

    public int getAmountOfGrass() {
        return this.grasses.size();
    }

    public Genome getDominantGenome() {
         Genome dominantGenome = null;

         for (Map.Entry<Genome, Integer> genomeEntry : this.genomes.entrySet()) {
             if (dominantGenome == null || genomeEntry.getValue() > this.genomes.get(dominantGenome))
                 dominantGenome = genomeEntry.getKey();
         }

         return dominantGenome;
    }

    public int getAverageAnimalsEnergy() {
        int sum = 0;
        for (Animal animal : this.animals)
            sum += animal.getEnergy();

        if (this.animals.size() == 0)
            return 0;

        return sum / this.animals.size();
    }

    public int getAverageAnimalsLifetime() {
        int sum = 0;
        for (Animal deadAnimal : this.deadAnimals)
            sum += deadAnimal.getAge();

        if (this.deadAnimals.size() == 0)
            return 0;

        return sum / this.deadAnimals.size();
    }

    public double getAverageChildrenNumber() {
        int sum = 0;
        for (Animal animal : this.animals)
            sum += animal.getNumberOfChildren();

        if (this.animals.size() == 0)
            return 0;

        return (double) sum / (double) this.animals.size();
    }

    public List<Animal> getDominantAnimals() {
        List <Animal> dominantAnimals = new ArrayList<>();
        Genome dominantGenome = this.getDominantGenome();

        for(Animal animal : animals) {
            if (animal.getGenome().equals(dominantGenome))
                dominantAnimals.add(animal);
        }
        return dominantAnimals;
    }

    private void checkGrassConsumption(Set <Vector2d> positions) {
        // checking if grasses are being eaten and finding eaters of them
        for (Vector2d position : positions) {
            Grass grass = null;

            for (Object element : this.elementsMap.get(position)) {
                if (element instanceof Grass) {
                    grass = (Grass) element;
                    break;
                }
            }

            if (grass != null) {
                Animal eater = null;

                // finding the highest energy level
                for (Object element : this.elementsMap.get(position)) {
                    if (element instanceof Animal &&
                            (eater == null || ((Animal) element).getEnergy() > eater.getEnergy()))
                        eater = (Animal) element;
                }

                if (eater != null) {
                    // finding all eaters (animals with the same energy)
                    List<Animal> eaters = new ArrayList<>();
                    int eatersEnergy = eater.getEnergy();

                    for (Object element : this.elementsMap.get(position)) {
                        if (element instanceof Animal && ((Animal) element).getEnergy() == eatersEnergy)
                            eaters.add((Animal) element);
                    }

                    for (Animal animal : eaters)
                        animal.consumeGrass(this.plantEnergy/eaters.size());

                    this.grasses.remove(grass);
                    this.elementsMap.get(position).remove(grass);
                }
            }
        }
    }

    private void checkReproduction(Set <Vector2d> positions) {
        // checking occurrence of reproduction and animals taking part
        for (Vector2d position : positions) {
            if (this.elementsMap.get(position).size() >=2) {  // there are at least 2 animals to reproduce
                Animal firstAnimal = null;
                Animal secondAnimal = null;

                for (Object element : this.elementsMap.get(position)) {
                    // finding a pair with the highest level of energy
                    Animal currAnimal = (Animal) element;
                    if (firstAnimal == null || currAnimal.getEnergy() > firstAnimal.getEnergy()) {
                        secondAnimal = firstAnimal;
                        firstAnimal = currAnimal;
                    }
                    else if (secondAnimal == null || currAnimal.getEnergy() > secondAnimal.getEnergy())
                        secondAnimal = currAnimal;
                }

                if (firstAnimal != null && secondAnimal != null &&
                        firstAnimal.hasMinimumReproductionEnergy() && secondAnimal.hasMinimumReproductionEnergy()) {
                    Animal babyAnimal = firstAnimal.reproduce(
                            secondAnimal, this.worldBuilder.getBabyPosition(firstAnimal.getPosition()));

                    this.addAnimal(babyAnimal);
                }
            }
        }
    }

    private void getRidOfDeadAnimals() {
        // erasing dead animals from the map
        List<Animal> animalsToRemove = new ArrayList<>();
        for (Animal animal : this.animals) {
            if (animal.isDead()) {
                animalsToRemove.add(animal);
            }
        }

        for (Animal animal : animalsToRemove) {
            this.removeAnimal(animal);
        }
    }
}
