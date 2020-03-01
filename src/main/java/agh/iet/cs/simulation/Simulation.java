package agh.iet.cs.simulation;

import agh.iet.cs.map.WorldBuilder;
import agh.iet.cs.map.WorldMap;
import agh.iet.cs.utilities.JSONReader;
import agh.iet.cs.utilities.Statistics;

public class Simulation {
    // here is the centre of the simulation; method 'simulate' is working when the simulation is active
    private boolean simulationActive;

    private SimulationView simulationView;

    // A B because two maps are working simultaneously
    private Statistics statisticsA, statisticsB;

    private WorldMap mapA, mapB;

    public Simulation(SimulationView simulationView) {
        // setting initial conditions

        // object which is responsible for visualization
        this.simulationView = simulationView;

        this.simulationActive = true;

        // object responsible for reading parameters from json
        JSONReader jsonReader = new JSONReader("src/main/resources/parameters/parameters.json");

        // making maps
        this.mapA = new WorldMap(jsonReader.getWidth(), jsonReader.getHeight(),
                jsonReader.getPlantEnergy(), jsonReader.getMoveEnergy(), jsonReader.getStartEnergy());
        this.mapB = new WorldMap(jsonReader.getWidth(), jsonReader.getHeight(),
                jsonReader.getPlantEnergy(), jsonReader.getMoveEnergy(), jsonReader.getStartEnergy());

        // making world builders for maps
        WorldBuilder worldBuilderA = new WorldBuilder(this.mapA);
        WorldBuilder worldBuilderB = new WorldBuilder(this.mapB);

        // setting initial map conditions
        this.mapA.startWorld(worldBuilderA, jsonReader.getJungleRatio(),
                jsonReader.getInitialNumberOfAnimals());
        this.mapB.startWorld(worldBuilderB, jsonReader.getJungleRatio(),
                jsonReader.getInitialNumberOfAnimals());

        // objects responsible for maps' statistics
        this.statisticsA = new Statistics(this.mapA, "src/main/resources/statistics/statisticsA.json");
        this.statisticsB = new Statistics(this.mapB, "src/main/resources/statistics/statisticsB.json");

        // passing a few objects
        this.simulationView.passSimulationAndStatistics(this, this.statisticsA, this.statisticsB);

        // creating display
        this.simulationView.createView(this.mapA, this.mapB);
    }

    public void simulate() {
        // main method of the simulation

        // next days in the worlds
        this.mapA.nextDay();
        this.mapB.nextDay();

        // updating statistics
        this.statisticsA.updateStatistics();
        this.statisticsB.updateStatistics();

        // visualizing
        this.simulationView.draw();
    }

    public void changeState() {
        // called when using pause button
        this.simulationActive = !this.simulationActive;
    }

    // getter
    public boolean getSimulationActive() {
        return this.simulationActive;
    }
}
