package agh.iet.cs.simulation;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;

import agh.iet.cs.map.WorldMap;
import agh.iet.cs.utilities.ImageLoader;
import agh.iet.cs.utilities.Statistics;
import agh.iet.cs.visualization.MapVisualizer;
import agh.iet.cs.visualization.MenuPanel;
import agh.iet.cs.visualization.MenuState;
import agh.iet.cs.visualization.MouseManager;

public class SimulationView extends BorderPane {
    // centre of simulation graphics

    private WorldMap mapA;
    private WorldMap mapB;

    private Canvas canvasA;
    private Canvas canvasB;

    private MapVisualizer mapVisualizerA;
    private MapVisualizer mapVisualizerB;

    private MenuPanel menuPanel;

    private Statistics statisticsA;
    private Statistics statisticsB;

    private MouseManager mouseManager;

    public SimulationView() {
        // setting main graphics constraints

        // canvas for drawing first map
        this.canvasA = new Canvas(400, 400);

        // canvas for drawing second map
        this.canvasB = new Canvas(400, 400);
        this.canvasB.setLayoutX(700); // second map starts at x coordinate 700

        // menu with buttons and text
        this.menuPanel = new MenuPanel();

        setMargin(this.menuPanel, new Insets(0, 400, 0, 400));
        setCenter(this.menuPanel);
    }

    public void passSimulationAndStatistics(Simulation simulation, Statistics statisticsA, Statistics statisticsB) {
        // passing a few objects
        this.statisticsA = statisticsA;
        this.statisticsB = statisticsB;

        this.menuPanel.passSimulationAndStatistics(simulation, statisticsA, statisticsB);
    }

    public void createView(WorldMap mapA, WorldMap mapB) {
        // initializing a few more objects

        // world maps
        this.mapA = mapA;
        this.mapB = mapB;

        // object responsible for loading images
        ImageLoader imageLoader = new ImageLoader(this.mapA.getWidth(), this.mapA.getHeight());

        // map visualizers
        this.mapVisualizerA = new MapVisualizer(this.mapA, imageLoader, this.canvasA);
        this.mapVisualizerB = new MapVisualizer(this.mapB, imageLoader, this.canvasB);

        // adding canvas to this simulationView object
        this.getChildren().addAll(this.canvasA, this.canvasB);

        // making menu configurations
        this.menuPanel.configureMenu();

        // initializing object responsible for clicking on animals
        this.mouseManager = new MouseManager(this.menuPanel);
    }

    public void handleClick(double posX, double posY) {
        // initial handling with clicks

        // first map
        if (posX <= 400) {
            this.statisticsA.changeFollowedAnimal(this.mouseManager.getAnimalFromClick(posX, posY, mapA));
            this.statisticsB.changeFollowedAnimal(null);

        }

        // second map
        else if (posX >= 700) {
            this.statisticsB.changeFollowedAnimal(this.mouseManager.getAnimalFromClick(posX - 700, posY, mapB));
            this.statisticsA.changeFollowedAnimal(null);
        }

        this.menuPanel.updateLabel();
    }

    public void draw() {
        // drawing all elements

        // updating label (changing shown statistics)
        this.menuPanel.updateLabel();

        // drawing green background of maps
        this.mapVisualizerA.drawBackground();
        this.mapVisualizerB.drawBackground();

        // drawing all animals in proper colors and grass
        this.mapVisualizerA.drawObjects();
        this.mapVisualizerB.drawObjects();

        // marking proper animals when we want to see dominant genome holders
        if (menuPanel.getMenuState() == MenuState.GENOME) {
            mapVisualizerA.drawMarkedAnimals();
            mapVisualizerB.drawMarkedAnimals();
        }

        // marking animal which is followed
        if (menuPanel.getMenuState() == MenuState.FOLLOWING) {
            mapVisualizerA.drawFollowedAnimal(this.statisticsA.getFollowedAnimal());
            mapVisualizerB.drawFollowedAnimal(this.statisticsB.getFollowedAnimal());
        }
    }
}
