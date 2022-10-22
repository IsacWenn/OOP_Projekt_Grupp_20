package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * A class that represents the view in the application. Loads the main FXML-file and displays it.
 */
public class AppView extends Application{

    /**
     * An implementation of the start from javaFX.Application method for our project.
     *
     * @param stage a {@link Stage} to display our FXML upon.
     * @throws Exception any unhandled exceptions arising from the method.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../MainView.fxml")));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * A method used by our Main class in the project in order to launch the application. Simply passes the method call
     * to {@link Application#launch}.
     *
     * @param args a {@link String[]} containing the optional start alternatives from the command-line.
     */
    public void startup(String[] args) {
        launch(args);
    }
}
