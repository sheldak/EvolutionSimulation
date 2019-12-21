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
import utilities.ImageLoader;
import utilities.Statistics;
import visualization.MapVisualizer;
import visualization.MenuPanel;
import visualization.MenuState;

public class SimulationView extends BorderPane {
    private Simulation simulation;

    private WorldMap mapA;
    private WorldMap mapB;

    private Canvas canvasA;
    private Canvas canvasB;

    private MapVisualizer mapVisualizerA;
    private MapVisualizer mapVisualizerB;

    private MenuPanel menuPanel;
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

    private Statistics statisticsA;
    private Statistics statisticsB;

    private Font bigFont = new Font("Arial", 30);
    private Font smallFont = new Font("Arial", 18);
    private Font labelFont = new Font("Arial", 13);

    public SimulationView() {
        this.canvasA = new Canvas(400, 400);
        this.canvasB = new Canvas(400, 400);
        this.canvasB.setLayoutX(700);

        this.menuPanel = new MenuPanel();

        setMargin(this.menuPanel, new Insets(0, 400, 0, 400));
        setCenter(this.menuPanel);
    }

    public void passSimulationAndStatistics(Simulation simulation, Statistics statisticsA, Statistics statisticsB) {
        this.simulation = simulation;
        this.statisticsA = statisticsA;
        this.statisticsB = statisticsB;

        this.menuPanel.passSimulationAndStatistics(simulation, statisticsA, statisticsB);
    }

    public void createView(WorldMap mapA, WorldMap mapB) {
        this.mapA = mapA;
        this.mapB = mapB;

        ImageLoader imageLoader = new ImageLoader(this.mapA.getWidth(), this.mapA.getHeight());

        this.mapVisualizerA = new MapVisualizer(this.mapA, imageLoader, this.canvasA);
        this.mapVisualizerB = new MapVisualizer(this.mapB, imageLoader, this.canvasB);

        this.getChildren().addAll(this.canvasA, this.canvasB);

        this.menuPanel.configureMenu();
    }

    public void draw() {
        this.menuPanel.updateLabel();

        this.mapVisualizerA.drawBackground();
        this.mapVisualizerB.drawBackground();

        this.mapVisualizerA.drawObjects();
        this.mapVisualizerB.drawObjects();

        if (menuPanel.getMenuState() == MenuState.GENOME) {
            mapVisualizerA.drawMarkedAnimals();
            mapVisualizerB.drawMarkedAnimals();
        }
    }
}
