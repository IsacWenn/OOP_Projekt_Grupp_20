package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    private Button newClosingPriceGraphButton;

    @FXML
    private Button newDailyChangeGraphButton;

    @FXML
    private Button newLinearRegressionGraphButton;

    @FXML
    private Button newHighMinusLowGraphButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeVariables();
    }

    private void initializeVariables() {
    }

    public void newClosingPriceGraph() {
        GraphController newGraph = new ClosingPriceGraphController(this);
        newTab(newGraph, "Closing Price");
    }

    public void newDailyChangeGraph() {
        GraphController newGraph = new DailyChangeGraphController(this);
        newTab(newGraph, "Daily Change");
    }

    public void newLinearRegressionGraph() {
        GraphController newGraph = new LinearRegressionGraphController(this);
        newTab(newGraph, "Linear Regression");
    }

    public void newHighMinusLowGraph() {
        GraphController newGraph = new HighMinusLowGraphController(this);
        newTab(newGraph, "High Minus Low");
    }

    private void newTab(Node content, String name) {
        Tab newTab = new Tab(name, content);
        tabsPane.getTabs().add(newTab);
        tabsPane.getSelectionModel().select(newTab);
    }
}









