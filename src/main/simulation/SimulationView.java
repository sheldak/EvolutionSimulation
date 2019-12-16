package simulation;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SimulationView extends VBox {
    private Canvas canvas;

    private Color color;

    private Button startButton = new Button("Start simulation");


    public SimulationView() {
        this.canvas = new Canvas(1000, 400);
        this.color = Color.RED;
        
//        this.startButton.setOnAction(event -> this.startSimulation());

        this.getChildren().addAll(this.startButton, this.canvas);
    }

    public void draw() {
        if (this.color == Color.AQUA)
            this.color = Color.RED;
        else
            this.color = Color.AQUA;

        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        gc.setFill(this.color);
        gc.fillRect(10, 10, 300, 300);
    }
}
