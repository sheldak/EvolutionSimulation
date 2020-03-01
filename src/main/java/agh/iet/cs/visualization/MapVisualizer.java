package agh.iet.cs.visualization;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import agh.iet.cs.features.Vector2d;
import agh.iet.cs.map.WorldMap;
import agh.iet.cs.mapElements.Animal;
import agh.iet.cs.mapElements.Grass;
import agh.iet.cs.mapElements.IMapElement;
import agh.iet.cs.utilities.ImageLoader;

import java.util.List;

public class MapVisualizer {
    // class responsible for visualising maps (one visualizer for one map)
    private WorldMap map;
    private ImageLoader imageLoader;

    private Canvas canvas;

    public MapVisualizer(WorldMap map, ImageLoader imageLoader, Canvas canvas) {
        // passing necessary objects
        this.map = map;
        this.imageLoader = imageLoader;

        this.canvas = canvas;
    }

    public void drawBackground() {
        // drawing green background for the map
        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        gc.setFill(Color.DARKGREEN);
        gc.fillRect(0, 0, 400, 400);
    }

    public void drawObjects() {
        // drawing all animals and grass on the map
        int width = map.getWidth();
        int height = map.getHeight();

        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                Vector2d currArea = new Vector2d(x, y);

                if (map.isOccupied(currArea)) {
                    int layoutX = x * (400 / width);
                    int layoutY = 400 - y * (400 / height);

                    drawObject((IMapElement) map.objectsAt(currArea).toArray()[0], layoutX, layoutY);
                }
            }
        }
    }

    public void drawMarkedAnimals() {
        // drawing animals which are marked because of having dominant gene
        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        List<Animal> animals = this.map.getDominantAnimals();

        int width = map.getWidth();
        int height = map.getHeight();

        for (Animal animal : animals) {
            int layoutX = animal.getPosition().getX() * (400 / width);
            int layoutY = 400 - animal.getPosition().getY() * (400 / height);
            gc.drawImage(this.imageLoader.getMarkedAnimalImage(), layoutX, layoutY);
        }
    }

    public void drawFollowedAnimal(Animal followedAnimal) {
        // drawing followed animal if such exists
        if (followedAnimal != null) {
            GraphicsContext gc = this.canvas.getGraphicsContext2D();

            int layoutX = followedAnimal.getPosition().getX() * (400 / map.getWidth());
            int layoutY = 400 - followedAnimal.getPosition().getY() * (400 / map.getHeight());
            gc.drawImage(this.imageLoader.getMarkedAnimalImage(), layoutX, layoutY);
        }
    }

    private void drawObject(IMapElement object, int layoutX, int layoutY) {
        // draw all animals (not marked) and grass

        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        if (object instanceof Animal) {
            if (((Animal) object).hasHigherThanStartEnergy())
                gc.drawImage(this.imageLoader.getHighEnergyAnimal(), layoutX, layoutY);
            else if (((Animal) object).hasMinimumReproductionEnergy())
                gc.drawImage(this.imageLoader.getMediumEnergyAnimal(), layoutX, layoutY );
            else
                gc.drawImage(this.imageLoader.getLowEnergyAnimal(), layoutX, layoutY );
        }

        else if (object instanceof Grass)
            gc.drawImage(this.imageLoader.getGrassImage(), layoutX + 2, layoutY + 2);
    }
}