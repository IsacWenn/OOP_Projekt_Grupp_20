package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import model.AppModel;

public class AppController {

    private final AppModel appModel = AppModel.getInstance();

    @FXML
    private TabPane tabsPane;
    @FXML
    private AnchorPane mainView;
    @FXML
    private AnchorPane registerView;
    @FXML
    private AnchorPane loginView;

    /**
     * Generates a new {@link ComparisonChartController} as a tab in the AppController GUI.
     */
    @FXML
    private void newComparisonChart() {
        ChartController newChart = new ComparisonChartController(this);
        newTab(newChart, "Comparison Chart");
    }

    /**
     * Generates a new {@link DetailedChartController} as a tab in the AppController GUI.
     */
    @FXML
    private void newDetailedChart() {
        ChartController newChart = new DetailedChartController(this);
        newTab(newChart, "Detailed Chart");
    }

    /**
     * Generates a new {@link CorrelationChartController} as a tab in the AppController GUI.
     */
    @FXML
    private void newCorrelationChart() {
        ChartController newChart = new CorrelationChartController(this);
        newTab(newChart, "Correlation Chart");
    }

    /**
     * Creates a new tab in the AppController GUI.
     * @param content the node which is added as a tab.
     * @param name the name of the tab.
     */
    private void newTab(Node content, String name) {
        Tab newTab = new Tab(name, content);
        tabsPane.getTabs().add(newTab);
        tabsPane.getSelectionModel().select(newTab);
    }

    @FXML
    private void mainView() {
        mainView.toFront();
    }

    @FXML
    private void loginView() {
        loginView.toFront();
    }

    @FXML
    private void registerView() {
        registerView.toFront();
    }

    @FXML
    private void mouseTrap(Event event) {
        event.consume();
    }
}









