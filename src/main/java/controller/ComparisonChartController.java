package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import model.graphmodel.graphalgorithms.GraphAlgorithms;

import java.io.IOException;

public class ComparisonChartController extends ChartController {
    @FXML
    protected ComboBox<String> algorithmComboBox;

    public ComparisonChartController(AppController parentController){
        super(parentController);
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
    public void stockListOnClick(String acronym) {
        ControllerStockListItem item = stockListItemMap.get(acronym);
        if (withinCompanyLimit() || item.isActive()) {
            if (isCompanyActive(acronym)) {
                removeFromChart(acronym);
            } else {
                addToChart(acronym);
            }
            item.togglePressed();
        }
    }

}
