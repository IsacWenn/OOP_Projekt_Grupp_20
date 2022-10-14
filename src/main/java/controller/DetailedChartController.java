package controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import model.graphmodel.graphalgorithms.GraphAlgorithms;

import java.io.IOException;
import java.util.ArrayList;


public class DetailedChartController extends ChartController{

    private ArrayList<GraphAlgorithms> algorithms;

    @FXML
    private CheckBox closingPriceBox;
    @FXML
    private CheckBox dailyChangeBox;
    @FXML
    private CheckBox dailyDeviationBox;
    @FXML
    private CheckBox linearRegressionBox;

    public DetailedChartController(AppController parentController) {
        super(parentController);
        maxCompanies = 1;
        algorithms = new ArrayList<>();
        initializeCheckBoxes();
    }

    private void initializeCheckBoxes() {
        closingPriceBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                closingPriceBoxPressed();
            }
        });
        dailyChangeBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                dailyChangeBoxPressed();
            }
        });
        dailyDeviationBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                dailyDeviationBoxPressed();
            }
        });
        linearRegressionBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                linearRegressionBoxPressed();
            }
        });
    }

    @Override
    void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("../DetailedChart.fxml")));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void closingPriceBoxPressed() {
        if (algorithms.contains(GraphAlgorithms.DAILYCLOSINGPRICE)) {
            algorithms.remove(GraphAlgorithms.DAILYCLOSINGPRICE);
        } else {
            algorithms.add(GraphAlgorithms.DAILYCLOSINGPRICE);
        }
        refreshStocks();
    }

    private void dailyChangeBoxPressed() {
        if (algorithms.contains(GraphAlgorithms.DAILYCHANGE)) {
            algorithms.remove(GraphAlgorithms.DAILYCHANGE);
        } else {
            algorithms.add(GraphAlgorithms.DAILYCHANGE);
        }
        refreshStocks();
    }

    private void dailyDeviationBoxPressed() {
        if (algorithms.contains(GraphAlgorithms.DAILYHIGHMINUSLOW)) {
            algorithms.remove(GraphAlgorithms.DAILYHIGHMINUSLOW);
        } else {
            algorithms.add(GraphAlgorithms.DAILYHIGHMINUSLOW);
        }
        refreshStocks();
    }

    private void linearRegressionBoxPressed() {
        if (algorithms.contains(GraphAlgorithms.LINEARREGRESSION)) {
            algorithms.remove(GraphAlgorithms.LINEARREGRESSION);
        } else {
            algorithms.add(GraphAlgorithms.LINEARREGRESSION);
        }
        refreshStocks();
    }

    @Override
    protected void removeFromChart(String name) {
        activeCompanies.clear();
        chart.clearChart();
    }

    @Override
    protected void addToChart(String name) {
        activeCompanies.add(name);
        refreshStocks();
    }


    @Override
    protected void refreshStocks() {
        chart.clearChart();
        if (0 < algorithms.size() && 0 < activeCompanies.size()) {
            for (GraphAlgorithms algorithm : algorithms) {
                chart.setAlgorithm(algorithm);
                chart.addStockToChart(activeCompanies.get(activeCompanies.size()-1), algorithm.name(), startDate, endDate);
            }
        }
    }

    @Override
    public void stockListOnClick(String acronym) {
        ControllerStockListItem item = stockListItemMap.get(acronym);
        if (0 < activeCompanies.size() && activeCompanies.get(activeCompanies.size()-1).contains(acronym)) {
            removeFromChart(acronym);
        } else {
            if (!item.isActive()) {
                if (0 < activeCompanies.size()) {
                    stockListItemMap.get(activeCompanies.get(activeCompanies.size() - 1)).togglePressed();
                    activeCompanies.clear();
                }
                activeCompanies.add(acronym);
            } else {
                activeCompanies.clear();
            }
        }
        item.togglePressed();
        refreshStocks();
    }


}
