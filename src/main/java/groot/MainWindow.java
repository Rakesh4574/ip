package groot;

import groot.DialogBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main JavaFX window that orchestrates the dialog flow.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Groot groot;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image grootImage = new Image(this.getClass().getResourceAsStream("/images/DaGroot.png"));

    /**
     * Sets up bindings when the window is initially displayed.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Associates the Groot backend with this controller and shows the welcome message.
     *
     * @param g The Groot instance that handles command execution.
     */
    public void setGroot(Groot g) {
        groot = g;
        dialogContainer.getChildren().addAll(
                DialogBox.getGrootDialog(groot.getWelcomeMessage(), grootImage, "Welcome")
        );
    }

    /**
     * Handles user input by passing it to Groot and echoing both sides of the conversation.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = groot.getResponse(input);
        String commandType = groot.getCommandType();

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getGrootDialog(response, grootImage, commandType)
        );
        userInput.clear();

        if (input.equalsIgnoreCase("bye")) {
            javafx.application.Platform.exit();
        }
    }
}
