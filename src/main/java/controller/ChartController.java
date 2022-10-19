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

    /**
     * Creates a new ChartController and initializes all variables.
     * @param parentController the AppController which the {@link ChartController} is an element within.
     */
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

    /**
     * Loads the FXML for the {@link ChartController}.
     */
    abstract void loadFXML();

    /**
     * Initializes FXML elements.
     */
    protected void initializeSettings() {
        initializeStockPane();
        initializeStartDatePicker();
        initializeEndDatePicker();
        initializeChartTypeComboBox();
        initializeCurrencyComboBox();
        openLineChart();
    }

    /**
     * Initializes the DatePicker FXML element for the startDatePicker.
     */
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

    /**
     * Initializes the DatePicker FXML element for the endDatePicker.
     */
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

    /**
     * Initializes and fills the ComboBox FXML element chartTypeComboBox.
     */
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

    /**
     * Initializes and fills the ComboBox FXML element currencyComboBox.
     */
    private void initializeCurrencyComboBox() {
        currencyComboBox.getItems().addAll(GraphModel.getCurrencyNames());
        currencyComboBox.getSelectionModel().select(GraphModel.getCurrencyNames().get(0));
        currencyComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldVal, newVal) -> {
            chartModel.updateCurrency(newVal);
            refreshChart();
        });
    }

    /**
     * Creates a {@link ControllerStockListItem} for each company and adds them to a FlowPane.
     */
    protected void initializeStockPane() {
        stockPane.getChildren().clear();
        for (String MIC : DataHandler.getMICs()) {
            ControllerStockListItem listItem = new ControllerStockListItem(MIC, this, favouriteCompanies.contains(MIC));
            stockListItemMap.put(MIC, listItem);
        }
    }

    /**
     * Refreshes the order of the FlowPane containing the {@link ControllerStockListItem} based on whether they are
     * set as favorite or not.
     */
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

    /**
     * Adds an element to the list of favorite stocks or removes it if element already is set as favorite.
     * @param acronym The MIC of the stock which is to be added to or removed from favorites.
     */
    public void favoritize(String acronym){
        if (isCompanyFavorite(acronym)) {
            favouriteCompanies.remove(acronym);
        } else {
            favouriteCompanies.add(acronym);
        }
        updateStockList();
    }

    /**
     * Method which checks if a certain stock is a favorite.
     * @param acronym MIC of the stock which is to be checked.
     * @return whether the stock is a favorite or not.
     */
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
