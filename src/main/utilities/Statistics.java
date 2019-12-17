package utilities;

import javafx.util.Pair;
import map.WorldMap;

import java.util.HashMap;
import java.util.Map;

public class Statistics {
    private WorldMap mapA;
    private WorldMap mapB;

    private Map<Integer, Integer> animalsAfterNDaysA = new HashMap<>();
    private Map<Integer, Integer> grassAfterNDaysA = new HashMap<>();
    private Map<Integer, Integer> dominantGeneAfterNDaysA = new HashMap<>();
    private Map<Integer, Integer> averageEnergyAfterNDaysA = new HashMap<>();
    private Map<Integer, Integer> averageLifetimeAfterNDaysA = new HashMap<>();
    private Map<Integer, Integer> averageChildrenAfterNDaysA = new HashMap<>();

    private Map<Integer, Integer> animalsAfterNDaysB = new HashMap<>();
    private Map<Integer, Integer> grassAfterNDaysB = new HashMap<>();
    private Map<Integer, Integer> dominantGeneAfterNDaysB = new HashMap<>();
    private Map<Integer, Integer> averageEnergyAfterNDaysB = new HashMap<>();
    private Map<Integer, Integer> averageLifetimeAfterNDaysB = new HashMap<>();
    private Map<Integer, Integer> averageChildrenAfterNDaysB = new HashMap<>();

    String statisticsString;

    public Statistics(WorldMap mapA, WorldMap mapB) {
        this.mapA = mapA;
        this.mapB = mapB;
    }

    public void updateStatistics() {
        // same day for both maps
        int day = this.mapA.getCurrentDay();

        this.animalsAfterNDaysA.put(day, this.mapA.getNumberOfAnimals());
        this.grassAfterNDaysA.put(day, this.mapA.getAmountOfGrass());
        this.dominantGeneAfterNDaysA.put(day, this.mapA.getDominantGene());
        this.averageEnergyAfterNDaysA.put(day, this.mapA.getAverageAnimalsEnergy());
        this.averageLifetimeAfterNDaysA.put(day, this.mapA.getAverageAnimalsLifetime());
        this.averageChildrenAfterNDaysA.put(day, this.mapA.getAverageChildrenNumber());

        this.animalsAfterNDaysB.put(day, this.mapB.getNumberOfAnimals());
        this.grassAfterNDaysB.put(day, this.mapB.getAmountOfGrass());
        this.dominantGeneAfterNDaysB.put(day, this.mapB.getDominantGene());
        this.averageEnergyAfterNDaysB.put(day, this.mapB.getAverageAnimalsEnergy());
        this.averageLifetimeAfterNDaysB.put(day, this.mapB.getAverageAnimalsLifetime());
        this.averageChildrenAfterNDaysB.put(day, this.mapB.getAverageChildrenNumber());

        this.prepareStatistics();
    }

    public void prepareStatistics() {
        int day = this.mapA.getCurrentDay();

        int animalsA = this.animalsAfterNDaysA.get(day);
        int grassesA = this.grassAfterNDaysA.get(day);
        int geneA = this.dominantGeneAfterNDaysA.get(day);
        int energyA = this.averageEnergyAfterNDaysA.get(day);
        int lifetimeA = this.averageLifetimeAfterNDaysA.get(day);
        int childrenA = this.averageChildrenAfterNDaysA.get(day);

        int animalsB = this.animalsAfterNDaysB.get(day);
        int grassesB = this.grassAfterNDaysB.get(day);
        int geneB = this.dominantGeneAfterNDaysB.get(day);
        int energyB = this.averageEnergyAfterNDaysB.get(day);
        int lifetimeB = this.averageLifetimeAfterNDaysB.get(day);
        int childrenB = this.averageChildrenAfterNDaysB.get(day);

        this.statisticsString = String.format("Statistics     map A     map B \n " +
                                         "Animals        %d      %d  \n" +
                                         "Plants         %d      %d", animalsA, animalsB, grassesA, grassesB);
    }

    public String getStatistics() {
        return this.statisticsString;
    }
}
