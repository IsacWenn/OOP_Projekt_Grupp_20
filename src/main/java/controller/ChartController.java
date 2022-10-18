package controller;

import controller.charts.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.AppModel;
import model.chartmodel.ChartModel;
import model.datahandling.DataHandler;
import model.graphmodel.GraphModel;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class ChartController extends AnchorPane {

    protected final AppModel appModel = AppModel.getInstance();
    protected AppController parentController;
    protected ChartModel chartModel;
    protected Map<String, ControllerStockListItem> stockListItemMap = new HashMap<>();
    protected ArrayList<String> favouriteCompanies;
    protected Chart chart;

    @FXML
    protected DatePicker startDatePicker;
    @FXML
    protected DatePicker endDatePicker;
    @FXML
    protected FlowPane stockPane;
    @FXML
    protected Button timeframeOneDayButton;
    @FXML
    protected Button timeframeOneWeekButton;
    @FXML
    protected Button timeframeOneMonthButton;
    @FXML
    protected Button timeframeOneYearButton;
    @FXML
    protected ComboBox<String> chartTypeComboBox;
    @FXML
    protected ComboBox<String> currencyComboBox;
    @FXML
    protected AnchorPane chartPane;

    public ChartController(AppController parentController) {
        this.parentController = parentController;
        favouriteCompanies = new ArrayList<>();
        chartModel = new ChartModel();
        loadFXML();
        initializeSettings();
        try {
            chartModel.updateStartDate(startDatePicker.getValue());
            chartModel.updateEndDate(endDatePicker.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateStockList();
    }

    abstract void loadFXML();

    protected void initializeSettings() {
        initializeStockPane();
        initializeStartDatePicker();
        initializeEndDatePicker();
        initializeChartTypeComboBox();
        initializeCurrencyComboBox();
        openLineChart();
    }

    private void initializeStartDatePicker() {
        startDatePicker.setValue(LocalDate.now().minusYears(1));

        startDatePicker.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                chartModel.updateStartDate(newValue);
                refreshChart();
            } catch (IOException e) {
                startDatePicker.setValue(oldValue);
                e.printStackTrace();
            }
        });

        startDatePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                try {
                    chartModel.updateStartDate(startDatePicker.getValue());
                    refreshChart();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeEndDatePicker() {
        endDatePicker.setValue(LocalDate.now());

        endDatePicker.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                chartModel.updateEndDate(newValue);
                refreshChart();
            } catch (IOException e) {
                endDatePicker.setValue(oldValue);
                e.printStackTrace();
            }
        });

        endDatePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                try {
                    chartModel.updateEndDate(endDatePicker.getValue());
                    refreshChart();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeChartTypeComboBox() {
        chartTypeComboBox.getItems().addAll("Area Chart", "Bar Chart", "Line Chart");
        chartTypeComboBox.getSelectionModel().select("Line Chart");
        chartTypeComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldVal, newVal) -> {
            switch (newVal) {
                case ("Area Chart") -> openAreaChart();
                case ("Bar Chart") -> openBarChart();
                case ("Line Chart") -> openLineChart();
            }
            refreshChart();
        });
    }

    private void initializeCurrencyComboBox() {
        currencyComboBox.getItems().addAll(GraphModel.getCurrencyNames());
        currencyComboBox.getSelectionModel().select(GraphModel.getCurrencyNames().get(0));
        currencyComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldVal, newVal) -> {
            chartModel.updateCurrency(newVal);
            refreshChart();
        });
    }

    protected void initializeStockPane() {
        stockPane.getChildren().clear();
        for (String MIC : DataHandler.getMICs()) {
            ControllerStockListItem listItem = new ControllerStockListItem(MIC, this, favouriteCompanies.contains(MIC));
            stockListItemMap.put(MIC, listItem);
        }
    }

    public void updateStockList() {
        stockPane.getChildren().clear();
        for (String MIC : DataHandler.getMICs()) {
            if (favouriteCompanies.contains(MIC)) {
                stockPane.getChildren().add(stockListItemMap.get(MIC));
            }
        }
        for (String MIC : DataHandler.getMICs()) {
            if (!favouriteCompanies.contains(MIC)) {
                stockPane.getChildren().add(stockListItemMap.get(MIC));
            }
        }
    }

    public void favoritize(String acronym){
        if (isCompanyFavorite(acronym)) {
            favouriteCompanies.remove(acronym);
        } else {
            favouriteCompanies.add(acronym);
        }
        updateStockList();
    }

    private boolean isCompanyFavorite(String acronym) {
        return favouriteCompanies.contains(acronym);
    }

    protected abstract void refreshChart();

    @FXML
    public void timeframeOneDay() {
        try {
            chartModel.updateTimeInterval(LocalDate.now().minusDays(1), LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshChart();
    }

    @FXML
    public void timeframeOneWeek() {
        try {
            chartModel.updateTimeInterval(LocalDate.now().minusWeeks(1), LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshChart();
    }

    @FXML
    public void timeframeOneMonth() {
        try {
            chartModel.updateTimeInterval(LocalDate.now().minusMonths(1), LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshChart();
    }

    @FXML
    public void timeframeOneYear() {
        try {
            chartModel.updateTimeInterval(LocalDate.now().minusYears(1), LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshChart();
    }

    private void openLineChart() {
        chart = new LineChart();
        chartPane.getChildren().clear();
        chartPane.getChildren().add(chart);
    }

    private void openBarChart() {
        chart = new BarChart();
        chartPane.getChildren().clear();
        chartPane.getChildren().add(chart);
    }

    private void openAreaChart() {
        chart = new AreaChart();
        chartPane.getChildren().clear();
        chartPane.getChildren().add(chart);
    }

    protected String getCurrency() {
        return currencyComboBox.getSelectionModel().getSelectedItem();
    }

    public abstract void stockListOnClick(ControllerStockListItem item);
}
