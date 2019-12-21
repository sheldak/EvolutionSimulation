// used libraries: openjfx 11.0.2
//                 json simple 1.1

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import simulation.Simulation;
import simulation.SimulationView;

import java.util.concurrent.TimeUnit;

public class Main extends Application{
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Evolution Simulator");

        SimulationView simulationView = new SimulationView();
        Scene scene = new Scene(simulationView, 1100, 400);

        scene.addEventFilter(MouseEvent.MOUSE_PRESSED,
                mouseEvent -> simulationView.handleClick(mouseEvent.getX(), mouseEvent.getY()));

        Simulation simulation = new Simulation(simulationView);

        Thread thread = new Thread(() -> {
            Runnable runnable = simulation::simulate;
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(30);
                } catch (InterruptedException ex) {
                    System.out.println("View thread sleep problem");
                }

                if (simulation.getSimulationActive()) {
                    Platform.runLater(runnable);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}

