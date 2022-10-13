package controller;

import controller.charts.AreaChart;
import controller.charts.BarChart;
import controller.charts.DetailedChart;
import controller.charts.LineChart;
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
    }

    private void dailyChangeBoxPressed() {
        if (algorithms.contains(GraphAlgorithms.DAILYCHANGE)) {
            algorithms.remove(GraphAlgorithms.DAILYCHANGE);
        } else {
            algorithms.add(GraphAlgorithms.DAILYCHANGE);
        }
    }

    private void dailyDeviationBoxPressed() {
        if (algorithms.contains(GraphAlgorithms.DAILYHIGHMINUSLOW)) {
            algorithms.remove(GraphAlgorithms.DAILYHIGHMINUSLOW);
        } else {
            algorithms.add(GraphAlgorithms.DAILYHIGHMINUSLOW);
        }
    }

    private void linearRegressionBoxPressed() {
        if (algorithms.contains(GraphAlgorithms.LINEARREGRESSION)) {
            algorithms.remove(GraphAlgorithms.LINEARREGRESSION);
        } else {
            algorithms.add(GraphAlgorithms.LINEARREGRESSION);
        }
    }

    @Override
    protected void removeCompany(String acronym) {
        int i = activeCompanies.indexOf(acronym);
        chart.removeChartFromStock(i);
        activeCompanies.remove(i);
    }

    @Override
    protected void addCompany(String acronym) {
        activeCompanies.add(acronym);
        chart.addStockToChart(acronym, startDate, endDate);
    }


}
