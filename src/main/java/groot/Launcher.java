package groot;

import javafx.application.Application;

/**
 * Simple launcher class required by JavaFX to start the GUI.
 */
public class Launcher {
    /**
     * Delegates to JavaFX's Application launcher so Main can be initialized.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
