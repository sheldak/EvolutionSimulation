package world;

import static java.lang.System.out;

public class World {
    public static void main(String[] args){
        JSONReader jsonReader = new JSONReader("src/res/parameters.json");
        WorldMap map = new WorldMap(jsonReader.getWidth(), jsonReader.getHeight(), jsonReader.getJungleRatio(),
                jsonReader.getPlantEnergy());

        map.placeInitialAnimals(jsonReader.getInitialNumberOfAnimals(),
                jsonReader.getStartEnergy(), jsonReader.getMoveEnergy());

        out.println(map.toString());
        for (int i=0; i<31; i++){
            map.nextDay();
            out.println(map.toString());
        }

    }
}
