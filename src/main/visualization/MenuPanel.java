package visualization;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.json.simple.JSONObject;
import simulation.Simulation;
import utilities.JSONWriter;
import utilities.Statistics;

public class MenuPanel extends VBox {
    private MenuState menuState;
    private LabelManager labelManager;

    private Simulation simulation;

    private Statistics statisticsA;
    private Statistics statisticsB;

    private Button pauseButton;
    private Button dominantGenomeButton;
    private TextField textField;
    private Button followHistoryButton;
    private Button getStatisticsButton;
    private Label label;

    private Font bigFont = new Font("Arial", 30);
    private Font smallFont = new Font("Arial", 18);
    private Font labelFont = new Font("Arial", 13);

    public MenuPanel() {

    }

    public void configureMenu() {
        this.configurePauseButton();
        this.configureDominantGenomeButton();
        this.configureTextField();
        this.configureFollowHistoryButton();
        this.configureGetStatisticsButton();
        this.configureLabel();

        this.menuState = MenuState.STATISTICS;

        getChildren().addAll(this.pauseButton, this.dominantGenomeButton,
                this.textField, this.followHistoryButton, this.getStatisticsButton, this.label);

        setSpacing(7);
    }

    public void passSimulationAndStatistics(Simulation simulation, Statistics statisticsA, Statistics statisticsB) {
        this.simulation = simulation;
        this.statisticsA = statisticsA;
        this.statisticsB = statisticsB;
    }

    public void updateLabel() {
        this.labelManager.updateLabel(this.menuState);
        this.label.setText(this.labelManager.getText());
    }

    public MenuState getMenuState() {
        return this.menuState;
    }

    private void configurePauseButton() { // TODO get rid of redundancy
        this.pauseButton = new Button("Pause");
        this.pauseButton.setFont(this.bigFont);
        this.pauseButton.setOnAction(event -> {
            if (this.simulation.getSimulationActive())
                this.pauseButton.setText("Resume");
            else
                this.pauseButton.setText("Pause");

            this.simulation.changeState();
        });
        this.pauseButton.setMinSize(300,50);
    }

    private void configureTextField() {
        this.textField = new TextField();
        this.textField.setMinSize(300, 20);
        this.textField.setPromptText("Enter day");
    }

    private void configureDominantGenomeButton() {
        this.dominantGenomeButton = new Button("Dominant genomes");
        this.dominantGenomeButton.setFont(this.smallFont);
        this.dominantGenomeButton.setOnAction(event -> {
            if (this.menuState != MenuState.GENOME)
                this.menuState = MenuState.GENOME;
            else
                this.menuState = MenuState.STATISTICS;
        });
        this.dominantGenomeButton.setMinSize(300, 30);
    }

    private void configureFollowHistoryButton() {
        this.followHistoryButton = new Button("Follow history");
        this.followHistoryButton.setFont(this.smallFont);
        this.followHistoryButton.setOnAction(event -> {

        });
        this.followHistoryButton.setMinSize(300, 30);
    }

    private void configureGetStatisticsButton() {
        this.getStatisticsButton = new Button("Get statistics");
        this.getStatisticsButton.setFont(this.smallFont);
        this.getStatisticsButton.setOnAction(event -> {
            this.statisticsA.writeStatistics();
            this.statisticsB.writeStatistics();
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
