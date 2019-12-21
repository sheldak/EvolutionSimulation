package visualization;

import features.Vector2d;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import map.WorldMap;
import mapElements.Animal;
import mapElements.Grass;
import mapElements.IMapElement;
import utilities.ImageLoader;

import java.util.List;

public class MapVisualizer {
    private WorldMap map;
    private ImageLoader imageLoader;

    private Canvas canvas;

    public MapVisualizer(WorldMap map, ImageLoader imageLoader, Canvas canvas) {
        this.map = map;
        this.imageLoader = imageLoader;

        this.canvas = canvas;
    }

    public void drawBackground() {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        gc.setFill(Color.DARKGREEN);
        gc.fillRect(0, 0, 400, 400);
    }

    public void drawObjects() {
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

    private void drawObject(IMapElement object, int layoutX, int layoutY) {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        int energyBarOffset = (400 / this.map.getHeight()) * 7 / 10;

        if (object instanceof Animal) {
            gc.drawImage(this.imageLoader.getAnimalImage(), layoutX, layoutY);

            if (((Animal) object).hasHigherThanStartEenrgy())
                gc.drawImage(this.imageLoader.getHighEnergyBar(), layoutX, layoutY + energyBarOffset);
            else if (((Animal) object).hasMinimumReproductionEnergy())
                gc.drawImage(this.imageLoader.getMediumEnergyBar(), layoutX, layoutY + energyBarOffset);
            else
                gc.drawImage(this.imageLoader.getLowEnergyBar(), layoutX, layoutY + energyBarOffset);
        }

        else if (object instanceof Grass)
            gc.drawImage(this.imageLoader.getGrassImage(), layoutX + 2, layoutY + 2);
    }
}