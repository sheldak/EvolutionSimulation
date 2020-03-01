package agh.iet.cs.visualization;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import agh.iet.cs.simulation.Simulation;
import agh.iet.cs.utilities.Statistics;

public class MenuPanel extends VBox {
    // menu panel between maps and all its functions

    private MenuState menuState;
    private LabelManager labelManager;

    private Simulation simulation;

    private Statistics statisticsA;
    private Statistics statisticsB;

    private Button pauseButton;
    private Button dominantGenomeButton;
    private Button followHistoryButton;
    private TextField textField;
    private Button submitButton;
    private Button getStatisticsButton;
    private Label label;

    private Font bigFont = new Font("Arial", 26);
    private Font smallFont = new Font("Arial", 18);
    private Font labelFont = new Font("Arial", 13);

    public MenuPanel() {
        // do not need to initialize anything when creating object
    }

    public void configureMenu() {
        // making configurations of all menu elements

        this.configurePauseButton();
        this.configureDominantGenomeButton();
        this.configureFollowHistoryButton();
        this.configureTextField();
        this.configureSubmitButton();
        this.configureGetStatisticsButton();
        this.configureLabel();

        this.menuState = MenuState.STATISTICS;

        getChildren().addAll(this.pauseButton, this.dominantGenomeButton, this.followHistoryButton,
                this.textField, this.submitButton, this.getStatisticsButton, this.label);

        setSpacing(7);
    }

    public void passSimulationAndStatistics(Simulation simulation, Statistics statisticsA, Statistics statisticsB) {
        // getting necessary objects
        this.simulation = simulation;
        this.statisticsA = statisticsA;
        this.statisticsB = statisticsB;
    }

    public void updateLabel() {
        // updating label
        this.labelManager.updateLabel(this.menuState);
        this.label.setText(this.labelManager.getText());
    }

    // getter
    public MenuState getMenuState() {
        return this.menuState;
    }

    // configurations
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
        this.pauseButton.setMinSize(300,40);
    }

    private void configureDominantGenomeButton() {
        this.dominantGenomeButton = new Button("Dominant genomes");
        this.dominantGenomeButton.setFont(this.smallFont);
        this.dominantGenomeButton.setOnAction(event -> {
            if (this.menuState != MenuState.GENOME)
                this.menuState = MenuState.GENOME;
            else
                this.menuState = MenuState.STATISTICS;

            this.updateLabel();
        });
        this.dominantGenomeButton.setMinSize(300, 30);
    }

    private void configureTextField() {
        this.textField = new TextField();
        this.textField.setMinSize(300, 20);
        this.textField.setPromptText("Enter day");
    }

    private void configureSubmitButton() {
        this.submitButton = new Button("Submit");
        this.submitButton.setFont(this.smallFont);
        this.submitButton.setOnAction(event -> {
            if (this.menuState == MenuState.FOLLOWING && !this.textField.getText().equals("")) {
                if (this.statisticsA.getFollowedAnimal() != null)
                    this.statisticsA.setFollowingEndTime(this.textField.getText());
                else if (this.statisticsB.getFollowedAnimal() != null)
                    this.statisticsB.setFollowingEndTime(this.textField.getText());
            }
        });
        this.submitButton.setMinSize(300, 30);
    }

    private void configureFollowHistoryButton() {
        this.followHistoryButton = new Button("Follow history");
        this.followHistoryButton.setFont(this.smallFont);
        this.followHistoryButton.setOnAction(event -> {
            if (this.menuState != MenuState.FOLLOWING)
                this.menuState = MenuState.FOLLOWING;
            else
                this.menuState = MenuState.STATISTICS;

            this.updateLabel();
        });
        this.followHistoryButton.setMinSize(300, 30);
    }

    private void configureGetStatisticsButton() {
        this.getStatisticsButton = new Button("Get statistics");
        this.getStatisticsButton.setFont(this.smallFont);
        this.getStatisticsButton.setOnAction(event -> {
            this.statisticsA.writeStatistics();
            this.statisticsB.writeStatistics();

            this.updateLabel();
        });
        this.getStatisticsButton.setMinSize(300, 30);
    }

    private void configureLabel() {
        this.label = new Label();
        this.label.setFont(this.labelFont);
        this.setMinSize(300, 300);

        this.labelManager = new LabelManager(this.statisticsA, this.statisticsB);
    }
}
