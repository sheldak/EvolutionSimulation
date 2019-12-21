import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import simulation.Simulation;
import simulation.SimulationView;

import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class Main extends Application{
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Evolution Simulator");

        SimulationView simulationView = new SimulationView();
        Scene scene = new Scene(simulationView, 1100, 400);

        Simulation simulation = new Simulation(simulationView);

        Thread thread = new Thread(() -> {
            Runnable runnable = simulation::simulate;
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(30);
                } catch (InterruptedException ignore) {} // TODO improve exception

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

