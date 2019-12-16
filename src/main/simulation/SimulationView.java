package simulation;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import features.Vector2d;
import javafx.scene.text.Font;
import map.WorldMap;
import mapElements.Animal;
import mapElements.Grass;
import mapElements.IMapElement;

public class SimulationView extends BorderPane {
    private Canvas canvasA;
    private Canvas canvasB;

    private Font font = new Font("Arial", 30);

    private VBox menuPanel = new VBox();
    private Button pauseButton;

    private Image animalImage;
    private Image grassImage;
    private Image highEnergyBar;
    private Image mediumEnergyBar;
    private Image lowEnergyBar;
    private Image veryLowEnergyBar;

    private WorldMap mapA;
    private WorldMap mapB;
    private Simulation simulation;

    public SimulationView() {
        this.canvasA = new Canvas(400, 400);
        this.canvasB = new Canvas(400, 400);
        this.canvasB.setLayoutX(600);

        this.configureMenu();

        this.getChildren().addAll(this.canvasA, this.canvasB);

        this.loadImages();
    }

    public void configureMenu() {
        pauseButton = new Button("Pause");
        pauseButton.setFont(font);
        pauseButton.setOnAction(event -> {
            if (simulation.getSimulationActive())
                pauseButton.setText("Resume");
            else
                pauseButton.setText("Pause");

            simulation.changeState();
        });
        pauseButton.setMinSize(200,50);

        menuPanel.getChildren().add(pauseButton);
        setMargin(menuPanel, new Insets(0, 400, 0, 400));
        this.setCenter(menuPanel);
    }

    public void addMaps(WorldMap mapA, WorldMap mapB) {
        this.mapA = mapA;
        this.mapB = mapB;
    }

    public void passSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void draw() {
        this.drawBackground();
        this.drawObjects();
    }

    private void drawBackground() {
        GraphicsContext gcA = this.canvasA.getGraphicsContext2D();
        GraphicsContext gcB = this.canvasB.getGraphicsContext2D();

        gcA.setFill(Color.DARKGREEN);
        gcA.fillRect(0, 0, 400, 400);

        gcB.setFill(Color.DARKGREEN);
        gcB.fillRect(0, 0, 400, 400);
    }

    private void drawObjects() {
        // map A and map B have the same size
        for (int x=0; x<mapA.getWidth(); x++) {
            for (int y=0; y<mapA.getHeight(); y++) {
                Vector2d currArea = new Vector2d(x, y);

                if (mapA.isOccupied(currArea)) {
                    int layoutX = x * 10;  // TODO  make it more general
                    int layoutY = 400 - y*10;

                    drawObject((IMapElement) mapA.objectsAt(currArea).toArray()[0], layoutX, layoutY, canvasA);
                }

                if (mapB.isOccupied(currArea)) {
                    int layoutX = x * 10;  // TODO  make it more general
                    int layoutY = 400 - y*10;

                    drawObject((IMapElement) mapB.objectsAt(currArea).toArray()[0], layoutX, layoutY, canvasB);
                }
            }
        }
    }

    private void drawObject(IMapElement object, int layoutX, int layoutY, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        if (object instanceof Animal) {
            gc.drawImage(animalImage, layoutX + 1, layoutY + 1);

            int animalEnergy = ((Animal) object).getEnergy();
            int animalMaxEnergy = ((Animal) object).getMaxEnergy();

            if (animalEnergy > animalMaxEnergy * 3 / 4)
                gc.drawImage(highEnergyBar, layoutX + 1, layoutY + 7);
            else if (animalEnergy > animalMaxEnergy / 2)
                gc.drawImage(mediumEnergyBar, layoutX + 1, layoutY + 7);
            else if (animalEnergy > animalMaxEnergy / 4)
                gc.drawImage(lowEnergyBar, layoutX + 1, layoutY + 7);
            else
                gc.drawImage(veryLowEnergyBar, layoutX + 1, layoutY + 7);
        }

        else if (object instanceof Grass)
            gc.drawImage(grassImage, layoutX + 2, layoutY + 2);
    }

    private void loadImages() {
        try {
            animalImage = new Image(new FileInputStream("src/res/images/animal.png"));
        }
        catch (FileNotFoundException ex) {
            System.out.println("Animal image not found");
            System.exit(1);
        }

        try {
            grassImage = new Image(new FileInputStream("src/res/images/grass.png"));
        }
        catch (FileNotFoundException ex) {
            System.out.println("Grass image not found");
            System.exit(1);
        }

        try {
            highEnergyBar = new Image(new FileInputStream("src/res/images/highEnergyBar.png"));
        }
        catch (FileNotFoundException ex) {
            System.out.println("High energy bar image not found");
            System.exit(1);
        }

        try {
            mediumEnergyBar = new Image(new FileInputStream("src/res/images/mediumEnergyBar.png"));
        }
        catch (FileNotFoundException ex) {
            System.out.println("Medium energy bar image not found");
            System.exit(1);
        }

        try {
            lowEnergyBar = new Image(new FileInputStream("src/res/images/lowEnergyBar.png"));
        }
        catch (FileNotFoundException ex) {
            System.out.println("High energy bar image not found");
            System.exit(1);
        }

        try {
            veryLowEnergyBar = new Image(new FileInputStream("src/res/images/veryLowEnergyBar.png"));
        }
        catch (FileNotFoundException ex) {
            System.out.println("High energy bar image not found");
            System.exit(1);
        }

    }
}
