package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.AppModel;

import java.net.URL;
import java.util.*;

public class AppController {

    private final AppModel appModel = AppModel.getInstance();

    @FXML
    private TabPane tabsPane;

    /**
     * Generates a new {@link ComparisonChartController} as a tab in the AppController GUI.
     */
    public void newComparisonChart() {
        ChartController newChart = new ComparisonChartController(this);
        newTab(newChart, "Comparison Chart");
    }

    /**
     * Generates a new {@link DetailedChartController} as a tab in the AppController GUI.
     */
    public void newDetailedChart() {
        ChartController newChart = new DetailedChartController(this);
        newTab(newChart, "Detailed Chart");
    }

    /**
     * Generates a new {@link CorrelationChartController} as a tab in the AppController GUI.
     */
    public void newCorrelationChart() {
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

}









