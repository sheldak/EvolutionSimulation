package simulation;

import map.WorldBuilder;
import map.WorldMap;
import utilities.JSONReader;

import static java.lang.System.out;

public class Simulation {
    private boolean simulationActive;
    private SimulationView simulationView;

    private WorldMap mapA, mapB;
    private WorldBuilder worldBuilderA, worldBuilderB;

    public Simulation(SimulationView simulationView) {
        this.simulationView = simulationView;
        this.simulationActive = true;

        JSONReader jsonReader = new JSONReader("src/res/parameters.json");

        this.mapA = new WorldMap(jsonReader.getWidth(), jsonReader.getHeight(),
                jsonReader.getPlantEnergy(), jsonReader.getMoveEnergy());
        this.mapB = new WorldMap(jsonReader.getWidth(), jsonReader.getHeight(),
                jsonReader.getPlantEnergy(), jsonReader.getMoveEnergy());

        this.worldBuilderA = new WorldBuilder(mapA);
        this.worldBuilderB = new WorldBuilder(mapB);

        mapA.startWorld(worldBuilderA, jsonReader.getJungleRatio(),
                jsonReader.getInitialNumberOfAnimals(), jsonReader.getStartEnergy());
        mapB.startWorld(worldBuilderB, jsonReader.getJungleRatio(),
                jsonReader.getInitialNumberOfAnimals(), jsonReader.getStartEnergy());
    }

    public void simulate() {
        mapA.nextDay();
        out.println(mapA.toString());

        mapB.nextDay();
        out.println(mapB.toString());

        simulationView.draw();
    }

    public boolean getSimulationActive() {
        return this.simulationActive;
    }
}
