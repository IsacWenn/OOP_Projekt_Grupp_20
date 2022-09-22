package controller;

import javafx.fxml.FXMLLoader;
import model.AppModel;

import java.io.IOException;

public class GraphController {
    private AppModel model;

    public GraphController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(""));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }


    }
}
