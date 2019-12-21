package simulation;

import map.WorldBuilder;
import map.WorldMap;
import utilities.JSONReader;
import utilities.Statistics;

public class Simulation {
    private boolean simulationActive;
    private SimulationView simulationView;

    private Statistics statisticsA, statisticsB;

    private WorldMap mapA, mapB;

    public Simulation(SimulationView simulationView) {
        this.simulationView = simulationView;
        this.simulationActive = true;

        JSONReader jsonReader = new JSONReader("src/res/parameters/parameters.json");

        this.mapA = new WorldMap(jsonReader.getWidth(), jsonReader.getHeight(),
                jsonReader.getPlantEnergy(), jsonReader.getMoveEnergy(), jsonReader.getStartEnergy());
        this.mapB = new WorldMap(jsonReader.getWidth(), jsonReader.getHeight(),
                jsonReader.getPlantEnergy(), jsonReader.getMoveEnergy(), jsonReader.getStartEnergy());

        WorldBuilder worldBuilderA = new WorldBuilder(this.mapA);
        WorldBuilder worldBuilderB = new WorldBuilder(this.mapB);

        this.mapA.startWorld(worldBuilderA, jsonReader.getJungleRatio(),
                jsonReader.getInitialNumberOfAnimals());
        this.mapB.startWorld(worldBuilderB, jsonReader.getJungleRatio(),
                jsonReader.getInitialNumberOfAnimals());

        this.statisticsA = new Statistics(this.mapA);
        this.statisticsB = new Statistics(this.mapB);

        this.simulationView.passSimulationAndStatistics(this, this.statisticsA, this.statisticsB);
        this.simulationView.createView(this.mapA, this.mapB);
    }

    public void simulate() {
        this.mapA.nextDay();
        this.mapB.nextDay();

        this.statisticsA.updateStatistics();
        this.statisticsB.updateStatistics();

        this.simulationView.draw();
    }

    public void changeState() {
        this.simulationActive = !this.simulationActive;
    }

    public boolean getSimulationActive() {
        return this.simulationActive;
    }
}
