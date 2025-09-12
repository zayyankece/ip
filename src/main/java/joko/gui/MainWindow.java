package joko.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI of the Joko application.
 * <p>
 * This class manages the interaction between the user and the Joko logic,
 * displaying dialog boxes in the GUI for both user input and Joko's responses.
 * </p>
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

    private GuiJoko guiJoko;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Zayyan.jpeg"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/Anomali.jpeg"));

    /**
     * Initializes the controller.
     * <p>
     * Sets up the GUIJoko instance and binds the scroll pane's vertical scroll
     * value to the height of the dialog container so that it scrolls automatically.
     * </p>
     */
    @FXML
    public void initialize() {
        guiJoko = new GuiJoko();
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects a {@link GuiJoko} instance into the controller.
     * <p>
     * Also adds Joko's welcome message to the dialog container when the instance is set.
     * </p>
     *
     * @param j the {@code GuiJoko} instance to set
     */
    public void setGuiJoko(GuiJoko j) {
        guiJoko = j;
        dialogContainer.getChildren().add(
                DialogBox.getJokoDialog(guiJoko.getWelcomeMessage(), dukeImage)
        );
    }

    /**
     * Handles user input from the GUI.
     * <p>
     * This method creates two dialog boxes:
     * <ul>
     *   <li>One displaying the user's input</li>
     *   <li>One displaying Joko's response</li>
     * </ul>
     * After adding the dialog boxes to the container, the user input field is cleared.
     * </p>
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = guiJoko.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getJokoDialog(response, dukeImage)
        );
        userInput.clear();
    }
}
