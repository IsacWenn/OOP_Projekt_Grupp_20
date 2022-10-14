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
    protected ControllerStockListItem activeCompany;

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
        algorithms = new ArrayList<>();
        activeCompany = null;
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

    protected void removeFromChart() {
        activeCompany = null;
        chart.clearChart();
    }

    protected void addToChart(ControllerStockListItem item) {
        activeCompany = item;
        refreshStocks();
    }

    protected void refreshStocks() {
        chart.clearChart();
        if (!algorithms.isEmpty() && activeCompany != null) {
            for (GraphAlgorithms algorithm : algorithms) {
                chart.setAlgorithm(algorithm);
                chart.addStockToChart(activeCompany.getMIC(), algorithm.name(), startDate, endDate);
            }
        }
    }

    public void stockListOnClick(ControllerStockListItem item) {
        if (activeCompany != null && activeCompany == item) {
            removeFromChart();
        } else {
            if (!item.isActive()) {
                if (activeCompany != null) {
                    activeCompany.togglePressed();
                }
                activeCompany = item;
            } else {
                activeCompany = null;
            }
        }
        item.togglePressed();
        refreshStocks();
    }
}
