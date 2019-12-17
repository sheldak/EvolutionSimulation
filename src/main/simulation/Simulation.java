package simulation;

import map.WorldBuilder;
import map.WorldMap;
import utilities.JSONReader;
import utilities.Statistics;

import static java.lang.System.out;

public class Simulation {
    private boolean simulationActive;
    private SimulationView simulationView;

    private Statistics statistics;

    private WorldMap mapA, mapB;
    private WorldBuilder worldBuilderA, worldBuilderB;

    public Simulation(SimulationView simulationView) {
        this.simulationView = simulationView;
        this.simulationActive = true;

        JSONReader jsonReader = new JSONReader("src/res/parameters/parameters.json");

        this.mapA = new WorldMap(jsonReader.getWidth(), jsonReader.getHeight(),
                jsonReader.getPlantEnergy(), jsonReader.getMoveEnergy(), jsonReader.getStartEnergy());
        this.mapB = new WorldMap(jsonReader.getWidth(), jsonReader.getHeight(),
                jsonReader.getPlantEnergy(), jsonReader.getMoveEnergy(), jsonReader.getStartEnergy());

        this.worldBuilderA = new WorldBuilder(this.mapA);
        this.worldBuilderB = new WorldBuilder(this.mapB);

        this.mapA.startWorld(this.worldBuilderA, jsonReader.getJungleRatio(),
                jsonReader.getInitialNumberOfAnimals());
        this.mapB.startWorld(this.worldBuilderB, jsonReader.getJungleRatio(),
                jsonReader.getInitialNumberOfAnimals());

        this.simulationView.addMaps(this.mapA, this.mapB);
        this.simulationView.passSimulation(this);

        this.statistics = new Statistics(this.mapA, this.mapB);
    }

    public void simulate() {
       this. mapA.nextDay();
//        out.println(mapA.toString());

        this.mapB.nextDay();
//        out.println(mapB.toString());

        this.simulationView.draw();
        this.statistics.updateStatistics();
    }

    public void changeState() {
        this.simulationActive = !this.simulationActive;
    }

    public boolean getSimulationActive() {
        return this.simulationActive;
    }
}
