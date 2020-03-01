package agh.iet.cs.utilities;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageLoader {
    // class responsible for loading all images

    private Image highEnergyAnimal;
    private Image mediumEnergyAnimal;
    private Image lowEnergyAnimal;
    private Image markedAnimalImage;
    private Image grassImage;

    public ImageLoader(int mapWidth, int mapHeight) {
        // loading all images when the object is created
        try {
            this.loadImages(mapWidth, mapHeight);
        }
        catch (FileNotFoundException ex) {
            System.out.println("Image not found");
            System.exit(1);
        }
    }

    // getters
    public Image getHighEnergyAnimal() {
        return this.highEnergyAnimal;
    }

    public Image getMediumEnergyAnimal() {
        return this.mediumEnergyAnimal;
    }

    public Image getLowEnergyAnimal() {
        return this.lowEnergyAnimal;
    }

    public Image getMarkedAnimalImage() {
        return this.markedAnimalImage;
    }

    public Image getGrassImage() {
        return this.grassImage;
    }

    // loading images
    private void loadImages(int mapWidth, int mapHeight) throws FileNotFoundException{
        int imageWidth = 400 / mapWidth;
        int imageHeight = 400 / mapHeight;

        this.highEnergyAnimal = new Image(new FileInputStream("src/main/resources/images/highEnergyAnimal.png"),
                imageWidth, imageHeight, false, false);

        this.mediumEnergyAnimal = new Image(new FileInputStream("src/main/resources/images/mediumEnergyAnimal.png"),
                imageWidth, imageHeight, false, false);

        this.lowEnergyAnimal = new Image(new FileInputStream("src/main/resources/images/lowEnergyAnimal.png"),
                imageWidth, imageHeight, false, false);

        this.markedAnimalImage = new Image(new FileInputStream("src/main/resources/images/markedAnimal.png"),
                imageWidth, imageHeight, false, false);
        this.grassImage = new Image(new FileInputStream("src/main/resources/images/grass.png"),
                imageWidth, imageHeight, false, false);
    }
}
