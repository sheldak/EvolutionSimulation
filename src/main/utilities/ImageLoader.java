package utilities;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageLoader {

    private Image animalImage;
    private Image markedAnimalImage;
    private Image grassImage;
    private Image highEnergyBar;
    private Image mediumEnergyBar;
    private Image lowEnergyBar;

    public ImageLoader(int mapWidth, int mapHeight) {
        try {
            this.loadImages(mapWidth, mapHeight);
        }
        catch (FileNotFoundException ex) {
            System.out.println("Image not found");
            System.exit(1);
        }
    }

    public Image getAnimalImage() {
        return this.animalImage;
    }

    public Image getMarkedAnimalImage() {
        return this.markedAnimalImage;
    }

    public Image getGrassImage() {
        return this.grassImage;
    }

    public Image getHighEnergyBar() {
        return this.highEnergyBar;
    }

    public Image getMediumEnergyBar() {
        return this.mediumEnergyBar;
    }

    public Image getLowEnergyBar() {
        return this.lowEnergyBar;
    }

    private void loadImages(int mapWidth, int mapHeight) throws FileNotFoundException{
//        this.animalImage = new Image(new FileInputStream("src/res/images/animal.png"));
//        this.grassImage = new Image(new FileInputStream("src/res/images/grass.png"));
//
//        this.highEnergyBar = new Image(new FileInputStream("src/res/images/highEnergyBar.png"));
//        this.mediumEnergyBar = new Image(new FileInputStream("src/res/images/mediumEnergyBar.png"));
//        this.lowEnergyBar = new Image(new FileInputStream("src/res/images/lowEnergyBar.png"));

        int imageWidth = 400 / mapWidth;
        int imageHeight = 400 / mapHeight;

        this.animalImage = new Image(new FileInputStream("src/res/images/animal.png"), imageWidth, imageHeight, false, false);
        this.markedAnimalImage = new Image(new FileInputStream("src/res/images/markedAnimal.png"), imageWidth, imageHeight, false, false);
        this.grassImage = new Image(new FileInputStream("src/res/images/grass.png"), imageWidth, imageHeight, false, false);

        this.highEnergyBar = new Image(new FileInputStream("src/res/images/highEnergyBar.png"), imageWidth, imageHeight, false, false);
        this.mediumEnergyBar = new Image(new FileInputStream("src/res/images/mediumEnergyBar.png"), imageWidth, imageHeight, false, false);
        this.lowEnergyBar = new Image(new FileInputStream("src/res/images/lowEnergyBar.png"), imageWidth, imageHeight, false, false);
    }
}
