package simulation;

import features.Genome;
import features.MapDirection;
import map.WorldBuilder;
import map.WorldMap;
import mapElements.Animal;
import utilities.JSONReader;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class World {
    public static void main(String[] args){
        JSONReader jsonReader = new JSONReader("src/res/parameters.json");
        WorldMap map = new WorldMap(jsonReader.getWidth(), jsonReader.getHeight(),
                jsonReader.getPlantEnergy(), jsonReader.getMoveEnergy());

        WorldBuilder worldBuilder = new WorldBuilder(map);

        map.startWorld(worldBuilder, jsonReader.getJungleRatio(), jsonReader.getInitialNumberOfAnimals(), jsonReader.getStartEnergy());

        out.println(map.toString());
        for (int i=0; i<31; i++){
            map.nextDay();
            out.println(map.toString());
        }

    }
}
