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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeVariables();
    }

    private void initializeVariables() {
    }

    public void newComparisonChart() {
        ChartController newChart = new ComparisonChartController(this);
        newTab(newChart, "Comparison Chart");
    }

    public void newDetailedChart() {
        ChartController newChart = new DetailedChartController(this);
        newTab(newChart, "Detailed Chart");
    }

    public void newCorrelationChart() {
        ChartController newChart = new ComparisonChartController(this);
        newTab(newChart, "Correlation Chart");
    }

    private void newTab(Node content, String name) {
        Tab newTab = new Tab(name, content);
        tabsPane.getTabs().add(newTab);
        tabsPane.getSelectionModel().select(newTab);
    }

}









