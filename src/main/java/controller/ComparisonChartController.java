package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import model.datahandling.DataHandler;
import model.graphmodel.graphalgorithms.GraphAlgorithms;

import java.io.IOException;
import java.util.ArrayList;

public class ComparisonChartController extends ChartController {
    @FXML
    protected ComboBox<String> algorithmComboBox;
    protected ArrayList<ControllerStockListItem> activeCompanies;

    public ComparisonChartController(AppController parentController){
        super(parentController);
        activeCompanies = new ArrayList<>();
        initializeAlgorithmComboBox();
    }

    @Override
    void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("../LineChart.fxml")));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void initializeAlgorithmComboBox() {
        algorithmComboBox.getItems().addAll("Closing Price", "Daily Change", "Daily Deviation", "Linear Regression");
        algorithmComboBox.getSelectionModel().select("Closing Price");
        algorithmComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldVal, newVal) -> {
            switch (newVal) {
                case ("Closing Price") -> algorithm = GraphAlgorithms.DAILYCLOSINGPRICE;
                case ("Daily Change") -> algorithm = GraphAlgorithms.DAILYCHANGE;
                case ("Daily Deviation") -> algorithm = GraphAlgorithms.DAILYHIGHMINUSLOW;
                case ("Linear Regression") -> algorithm = GraphAlgorithms.LINEARREGRESSION;
            }
            chart.setAlgorithm(algorithm);
            refreshStocks();
        });
    }

    @Override
    public void stockListOnClick(ControllerStockListItem item) {
        if (item.isActive()) {
            removeFromChart(item);
        } else {
            addToChart(item);
        }
        item.togglePressed();
    }

    protected void removeFromChart(ControllerStockListItem item) {
        int i = activeCompanies.indexOf(item);
        chart.removeChartFromStock(i);
        activeCompanies.remove(i);
    }

    protected void addToChart(ControllerStockListItem item) {
        activeCompanies.add(item);
        chart.addStockToChart(item.getMIC(), item.getName(), startDate, endDate);
    }

    protected void refreshStocks() {
        chart.clearChart();
        for (ControllerStockListItem company : activeCompanies) {
            chart.addStockToChart(company.getMIC(), company.getMIC(), startDate, endDate);
        }
    }
}
