package utilities;

import features.Genome;
import map.WorldMap;

import java.util.HashMap;
import java.util.Map;

public class Statistics {
    private WorldMap map;

    private int currentDay;

    private int animals;
    private int grasses;
    private Genome dominantGenome;
    private int averageEnergy;
    private int averageLifetime;
    private double averageChildren;

    private int averageAnimalsAfterNDays;
    private int averageGrassesAfterNDays;
    private Genome averageGenomeAfterNDays;
    private double averageEnergyAfterNDays;
    private double averageLifetimeAfterNDays;
    private double averageChildrenAfterNDays;

    private Map<Genome, Integer> dominantGenomeHistory = new HashMap<>();


    public Statistics(WorldMap map) {
        this.map = map;
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
    }

    public void writeStatistics() {
        JSONWriter jsonWriter = new JSONWriter("src/res/statistics/statistics.json");

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

            if(dominantGenomeOccurrence >= 1) {
                this.dominantGenomeHistory.remove(this.dominantGenome);
                this.dominantGenomeHistory.put(this.dominantGenome, dominantGenomeOccurrence + 1);
            }
            else
                this.dominantGenomeHistory.put(this.dominantGenome, 1);
        }
    }
}
