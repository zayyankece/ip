package joko.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private GuiJoko guiJoko = new GuiJoko();

    @Override
    public void start(Stage stage) {
        try {
            var resource = Main.class.getResource("/view/MainWindow.fxml");
            assert resource != null : "MainWindow.fxml not found in /view/";

            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            AnchorPane ap = fxmlLoader.load();
            assert ap != null : "FXML root should not be null after loading.";

            Scene scene = new Scene(ap);
            stage.setScene(scene);

            MainWindow controller = fxmlLoader.getController();
            assert controller != null : "Controller should not be null after FXML load.";

            assert guiJoko != null : "GuiJoko should be initialized before injection.";
            controller.setGuiJoko(guiJoko);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
