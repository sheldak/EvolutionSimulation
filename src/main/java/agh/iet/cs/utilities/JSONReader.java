package agh.iet.cs.utilities;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

import static java.lang.System.out;

public class JSONReader {
    // class responsible for extracting world parameters from json file

    // worlds' parameters
    private int width;
    private int height;
    private double jungleRatio;

    private int startEnergy;
    private int moveEnergy;
    private int plantEnergy;

    private int initialNumberOfAnimals;

    public JSONReader(String path) {
        // reading parameters
        try {
            Object object = new JSONParser().parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) object;

            this.width = Math.toIntExact((Long) jsonObject.get("width"));
            this.height = Math.toIntExact((Long) jsonObject.get("height"));
            this.jungleRatio = (Double) jsonObject.get("jungleRatio");

            this.startEnergy = Math.toIntExact((Long) jsonObject.get("startEnergy"));
            this.moveEnergy = Math.toIntExact((Long) jsonObject.get("moveEnergy"));
            this.plantEnergy = Math.toIntExact((Long) jsonObject.get("plantEnergy"));

            this.initialNumberOfAnimals = Math.toIntExact((Long) jsonObject.get("initialNumberOfAnimals"));

        } catch (Exception ex) {
            out.println("loading parameters has failed");
        }
    }

    // getters
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public double getJungleRatio() {
        return this.jungleRatio;
    }

    public int getStartEnergy() {
        return this.startEnergy;
    }

    public int getMoveEnergy() {
        return this.moveEnergy;
    }

    public int getPlantEnergy() {
        return this.plantEnergy;
    }

    public int getInitialNumberOfAnimals() {
        return this.initialNumberOfAnimals;
    }
}
