package simulation;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
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

    private Font bigFont = new Font("Arial", 30);
    private Font smallFont = new Font("Arial", 18);

    private VBox menuPanel = new VBox();
    private Button pauseButton;
    private Button dominantGenomeButton;
    private TextField textField;
    private Button followHistoryButton;
    private Button getStatisticsButton;
    private Label label;

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
        this.configurePauseButton();
        this.configureDominantGenomeButton();
        this.configureTextField();
        this.configureFollowHistoryButton();
        this.configureGetStatisticsButton();
        this.configureLabel();

        this.menuPanel.getChildren().addAll(this.pauseButton, this.dominantGenomeButton,
                this.textField, this.followHistoryButton, this.getStatisticsButton, this.label);

        this.menuPanel.setSpacing(7);

        setMargin(this.menuPanel, new Insets(0, 400, 0, 400));
        this.setCenter(this.menuPanel);
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

    private void configurePauseButton() {
        this.pauseButton = new Button("Pause");
        this.pauseButton.setFont(this.bigFont);
        this.pauseButton.setOnAction(event -> {
            if (this.simulation.getSimulationActive())
                this.pauseButton.setText("Resume");
            else
                this.pauseButton.setText("Pause");

            this.simulation.changeState();
        });
        this.pauseButton.setMinSize(200,50);
    }

    private void configureTextField() {
        this.textField = new TextField();
        this.textField.setMinSize(200, 20);
        this.textField.setPromptText("Enter day");
    }

    private void configureDominantGenomeButton() {
        this.dominantGenomeButton = new Button("Dominant genome");
        this.dominantGenomeButton.setFont(this.smallFont);
        this.dominantGenomeButton.setOnAction(event -> {

        });
        this.dominantGenomeButton.setMinSize(200, 30);
    }

    private void configureFollowHistoryButton() {
        this.followHistoryButton = new Button("Follow history");
        this.followHistoryButton.setFont(this.smallFont);
        this.followHistoryButton.setOnAction(event -> {

        });
        this.followHistoryButton.setMinSize(200, 30);
    }

    private void configureGetStatisticsButton() {
        this.getStatisticsButton = new Button("Get statistics");
        this.getStatisticsButton.setFont(this.smallFont);
        this.getStatisticsButton.setOnAction(event -> {

        });
        this.getStatisticsButton.setMinSize(200, 30);
    }

    private void configureLabel() {
        this.label = new Label();
        this.label.setText("Statistics");
        this.setMinSize(200, 300);
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

            if (((Animal) object).hasHigherThanStartEenrgy())
                gc.drawImage(highEnergyBar, layoutX + 1, layoutY + 7);
            else if (((Animal) object).hasMinimumReproductionEnergy())
                gc.drawImage(mediumEnergyBar, layoutX + 1, layoutY + 7);
            else
                gc.drawImage(lowEnergyBar, layoutX + 1, layoutY + 7);
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
