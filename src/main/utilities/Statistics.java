package utilities;

import features.Genome;
import features.Vector2d;
import map.WorldMap;
import mapElements.Animal;

import java.util.HashMap;
import java.util.Map;

public class Statistics {
    private WorldMap map;

    private String writingPath;

    private int currentDay;

    private int animals;
    private int grasses;
    private Genome dominantGenome;
    private int averageEnergy;
    private int averageLifetime;
    private double averageChildren;

    private int averageAnimalsAfterNDays;
    private int averageGrassesAfterNDays;
    private double averageEnergyAfterNDays;
    private double averageLifetimeAfterNDays;
    private double averageChildrenAfterNDays;

    private Map<Genome, Integer> dominantGenomeHistory = new HashMap<>();

    private Animal followedAnimal;
    private Vector2d followedAnimalPosition;
    private int followedAnimalChildrenAtStart; // in the beginning of being followed
    private int followedAnimalOffspringAtStart;
    private int followedAnimalChildren;   // all children - that at the beginning of being followed
    private int followedAnimalOffspring;
    private int followingEndTime;


    public Statistics(WorldMap map, String writingPath) {
        this.map = map;

        this.writingPath = writingPath;

        this.followedAnimal = null;
        this.followedAnimalPosition = new Vector2d(-1, -1);
        this.followedAnimalChildrenAtStart = 0;
        this.followedAnimalOffspringAtStart = 0;
        this.followedAnimalChildren = 0;
        this.followedAnimalOffspring = 0;
        this.followingEndTime = 100000;
    }

    public void updateStatistics() {
        this.currentDay = map.getCurrentDay();

        this.animals = this.map.getNumberOfAnimals();
        this.grasses = this.map.getAmountOfGrass();
        this.dominantGenome = this.map.getDominantGenome();
        this.averageEnergy = this.map.getAverageAnimalsEnergy();
        this.averageLifetime = this.map.getAverageAnimalsLifetime();
        this.averageChildren = Math.round(this.map.getAverageChildrenNumber() * 10) / 10.0;

        this.updateAverageStatistics();
        this.updateFollowedAnimalStatistics();
    }

    public void changeFollowedAnimal(Animal animal) {
        this.followedAnimal = animal;

        if (this.followedAnimal != null) {
            this.followedAnimalChildrenAtStart = animal.getNumberOfChildren();
            this.followedAnimalOffspringAtStart = animal.getNumberOfOffspring();
            this.followedAnimalChildren = this.followedAnimalChildrenAtStart;
            this.followedAnimalOffspring = this.followedAnimalOffspringAtStart;
        }
        else {
            this.followedAnimalPosition = new Vector2d(-1, -1);
            this.followedAnimalChildrenAtStart = 0;
            this.followedAnimalOffspringAtStart = 0;
            this.followedAnimalChildren = 0;
            this.followedAnimalOffspring = 0;
            this.followingEndTime = 100000;
        }
    }

    public void setFollowingEndTime(String endTimeString) {
        try {
            int endTime = Integer.parseInt(endTimeString);
            if (this.currentDay < endTime)
                this.followingEndTime = endTime;
        } catch (NumberFormatException ex) {
            // if is invalid just do not do anything
        }
    }

    public void writeStatistics() {
        JSONWriter jsonWriter = new JSONWriter(this.writingPath);
        try {
            jsonWriter.writeToJSON(this.averageAnimalsAfterNDays, this.averageGrassesAfterNDays,
                    this.getDominantGenome(this.dominantGenomeHistory).getArray(), this.averageEnergyAfterNDays,
                    this.averageLifetimeAfterNDays, this.averageChildrenAfterNDays);
        } catch (Exception ex) {
            System.out.println("Writing to file failed");
        }
    }

    public int getCurrentDay() {
        return this.currentDay;
    }

    public int getAnimals() {
        return this.animals;
    }

    public int getGrasses() {
        return this.grasses;
    }

    public Genome getDominantGenome() {
        return this.dominantGenome;
    }

    public int getAverageEnergy() {
        return this.averageEnergy;
    }

    public int getAverageLifetime() {
        return this.averageLifetime;
    }

    public double getAverageChildren() {
        return this.averageChildren;
    }

    public Animal getFollowedAnimal() {
        return this.followedAnimal;
    }

    public Vector2d getFollowedAnimalPosition() {
        return this.followedAnimalPosition;
    }

    public int getFollowedAnimalChildren() {
        return this.followedAnimalChildren;
    }

    public int getFollowedAnimalOffspring() {
        return this.followedAnimalOffspring;
    }

    public int getFollowingEndTime() {
        return this.followingEndTime;
    }

    private Genome getDominantGenome(Map<Genome, Integer> dominantGenomeHistory) { // TODO the same method in WorldMap so maybe put it in Genome?
        Genome mostPopularGenome = null;

        for (Map.Entry<Genome, Integer> genomeEntry : dominantGenomeHistory.entrySet()) {
            if (mostPopularGenome == null || genomeEntry.getValue() > dominantGenomeHistory.get(mostPopularGenome))
                mostPopularGenome = genomeEntry.getKey();
        }

        return mostPopularGenome;
    }

    private void updateAverageStatistics() {
        int currDay = this.map.getCurrentDay();

        this.averageAnimalsAfterNDays = (this.averageAnimalsAfterNDays * (currDay - 1) + this.animals) / currDay;
        this.averageGrassesAfterNDays = (this.averageGrassesAfterNDays * (currDay - 1) + this.grasses) / currDay;
        this.averageEnergyAfterNDays = (this.averageEnergyAfterNDays * (currDay - 1) + this.averageEnergy) / currDay;
        this.averageLifetimeAfterNDays =
                (this.averageLifetimeAfterNDays * (currDay - 1) + this.averageLifetime) / currDay;
        this.averageChildrenAfterNDays =
                (this.averageChildrenAfterNDays * (currDay - 1) + this.averageChildren) / currDay;

        if (this.dominantGenomeHistory.containsKey(this.dominantGenome)) {
            int dominantGenomeOccurrence = this.dominantGenomeHistory.get(this.dominantGenome);

            if (dominantGenomeOccurrence >= 1) {
                this.dominantGenomeHistory.remove(this.dominantGenome);
                this.dominantGenomeHistory.put(this.dominantGenome, dominantGenomeOccurrence + 1);
            }
        }
        else
            this.dominantGenomeHistory.put(this.dominantGenome, 1);
    }

    private void updateFollowedAnimalStatistics() {
        if (this.followedAnimal != null && this.followingEndTime >= this.currentDay) {
            this.followedAnimalPosition = this.followedAnimal.getPosition();

            this.followedAnimalChildren =
                    this.followedAnimal.getNumberOfChildren() - this.followedAnimalChildrenAtStart;

            this.followedAnimalOffspring =
                    this.followedAnimal.getNumberOfOffspring() - this.followedAnimalOffspringAtStart;
        }
    }
}
