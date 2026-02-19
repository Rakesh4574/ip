package groot;

import java.io.IOException;
import java.util.Collections;
import groot.MainWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * UI container representing a single dialogue bubble for the chat window.
 * This class centralizes loading the FXML layout and styling depending on
 * whether the message comes from the user or Groot.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Initializes the dialog box with the provided text and profile image.
     *
     * @param text The message to display inside the bubble.
     * @param img  The avatar image shown alongside the message.
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Reverses the order of the child nodes to position Groot on the left.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Applies a CSS helper class based on the type of command that generated
     * the response, so Groot can visually differentiate operations.
     *
     * @param commandType The simple name of the command class that produced the response.
     */
    private void changeDialogStyle(String commandType) {
        switch (commandType) {
            case "AddCommand":
                dialog.getStyleClass().add("add-label");
                break;
            case "MarkAsDoneCommand":
            case "UnmarkAsDoneCommand":
                dialog.getStyleClass().add("marked-label");
                break;
            case "DeleteCommand":
                dialog.getStyleClass().add("delete-label");
                break;
            default:
                break;
        }
    }

    /**
     * Creates a dialog box representing the user's input.
     *
     * @param text The user-entered text.
     * @param img  The user's avatar image.
     * @return A configured DialogBox for the user message.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates a dialog box for Groot's response and applies styling based on the command.
     *
     * @param text        The response Groot should display.
     * @param img         Groot's avatar image.
     * @param commandType The command that generated this response.
     * @return A styled DialogBox for Groot.
     */
    public static DialogBox getGrootDialog(String text, Image img, String commandType) {
        var db = new DialogBox(text, img);
        db.flip();
        db.changeDialogStyle(commandType);
        return db;
    }
}
