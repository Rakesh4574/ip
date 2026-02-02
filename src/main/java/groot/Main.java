package groot;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("I am Groot!");
        Scene scene = new Scene(helloWorld, 400, 200);

        stage.setScene(scene);
        stage.setTitle("Groot Assistant");
        stage.show();
    }
}
