package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.AppModel;

import java.net.URL;
import java.util.*;

public class AppController implements Initializable {

    private final AppModel appModel = AppModel.getInstance();

    @FXML
    private TabPane tabsPane;

    @FXML
    private Button newLineGraph;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeVariables();
    }

    private void initializeVariables() {
    }

    public void newLineGraph() {
        GraphController newGraph = new LineGraphController(this);
        Tab newTab = new Tab("Line Graph", newGraph);
        tabsPane.getTabs().add(newTab);
        tabsPane.getSelectionModel().select(newTab);
    }
}









