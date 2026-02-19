package groot;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * JavaFX application entry point that loads the main window for Groot.
 */
public class Main extends Application {
    private Groot groot = new Groot();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Groot Task Manager");
            fxmlLoader.<MainWindow>getController().setGroot(groot);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
