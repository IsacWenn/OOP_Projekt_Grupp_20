package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class AppView extends Application implements Observable{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void updateView() {
        update();
    }

    private void update() {
        System.out.println("Notify fungerar");
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../MainView.fxml")));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void startup() {
        launch();
    }
}
