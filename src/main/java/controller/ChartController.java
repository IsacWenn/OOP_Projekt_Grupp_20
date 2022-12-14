package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.datahandling.DataHandler;
import model.graphmodel.GraphModel;
import model.util.Date;
import view.charts.AreaChart;
import view.charts.BarChart;
import view.charts.Chart;
import view.charts.LineChart;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract JavaFX-dependent class which acts as a user interface between the user and different charts and graphs.
 *
 * Uses:    Chart, AreaChart, BarChart, LineChart, Date, GraphModel, DataHandler,
 *          ControllerStockListItem
 *
 * Used by: AppController, ComparisonChartController, CorrelationChartController
 *
 * @author Johan
 * @author Dennis
 */
public abstract class ChartController extends AnchorPane {

    protected AppController parentController;
    protected Map<String, ControllerStockListItem> stockListItemMap = new HashMap<>();
    protected List<GraphModel> graphModels;
    protected List<String> favoriteCompanies;
    protected Chart chart;

    protected Date startDate;
    protected Date endDate;

    @FXML
    protected Label saveLabel;
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
    public ChartController(AppController parentController, List<String> favoriteCompanies) {
        initialize(parentController, favoriteCompanies);
        updateStockList();
    }

    private void initialize(AppController parentController, List<String> favoriteCompanies) {
        this.parentController = parentController;
        this.favoriteCompanies = favoriteCompanies;
        graphModels = new ArrayList<>();
        loadFXML();
        initializeSettings();
    }

    public void updateFavoritesList(List<String> favouriteCompanies){
        this.favoriteCompanies = favouriteCompanies;
        updateStockList();
    }

    /**
     * Loads the FXML for the {@link ChartController}.
     */
    protected abstract void loadFXML();

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
                startDate = new Date(newValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (GraphModel graphModel : graphModels) {
                graphModel.updateTimeInterval(startDate, endDate);
            }
            refreshChart();
        });

        startDatePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                try {
                    startDate = new Date(startDatePicker.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (GraphModel graphModel : graphModels) {
                    graphModel.updateTimeInterval(startDate, endDate );
                }
                refreshChart();
            }
        });
        try {
            startDate = new Date(startDatePicker.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the DatePicker FXML element for the endDatePicker.
     */
    private void initializeEndDatePicker() {
        endDatePicker.setValue(LocalDate.now());

        endDatePicker.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                endDate = new Date(newValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (GraphModel graphModel : graphModels) {
                graphModel.updateTimeInterval(startDate, endDate );
            }
            refreshChart();
        });

        endDatePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                try {
                    endDate = new Date(endDatePicker.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (GraphModel graphModel : graphModels) {
                    graphModel.updateTimeInterval(startDate, endDate);
                }
                refreshChart();
            }
        });
        try {
            endDate = new Date(endDatePicker.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes and fills the chart type selector {@link ComboBox}.
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
     * Initializes and fills the currency selector {@link ComboBox}.
     */
    private void initializeCurrencyComboBox() {
        currencyComboBox.getItems().addAll(GraphModel.getOrderedCurrencyNames());
        currencyComboBox.getSelectionModel().select(GraphModel.getOrderedCurrencyNames().get(0));
        currencyComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldVal, newVal) -> {
            for (GraphModel graphModel : graphModels) {
                graphModel.updateCurrency(newVal);
            }
            refreshChart();
        });
    }

    /**
     * Creates a {@link ControllerStockListItem} for each company and adds them to a FlowPane.
     */
    protected void initializeStockPane() {
        stockPane.getChildren().clear();
        for (String MIC : DataHandler.getMICs()) {
            ControllerStockListItem listItem = new ControllerStockListItem(MIC, this,
                    parentController, favoriteCompanies.contains(MIC));
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
            if (favoriteCompanies.contains(MIC)) {
                stockListItemMap.get(MIC).setFavorite();
                stockPane.getChildren().add(stockListItemMap.get(MIC));
            }
        }
        for (String MIC : DataHandler.getMICs()) {
            if (!favoriteCompanies.contains(MIC)) {
                stockListItemMap.get(MIC).removeFavorite();
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
            favoriteCompanies.remove(acronym);
        } else {
            favoriteCompanies.add(acronym);
        }
    }

    /**
     * Method which checks if a certain stock is a favorite.
     * @param acronym MIC of the stock which is to be checked.
     * @return whether the stock is a favorite or not.
     */
    private boolean isCompanyFavorite(String acronym) {
        return favoriteCompanies.contains(acronym);
    }

    /**
     * Redraws the chart.
     */
    protected abstract void refreshChart();

    /**
     * Sets the timeframe for which the chart is generated for to the past day.
     */
    @FXML
    public void timeframeOneDay() {
        try {
            LocalDate start = LocalDate.now().minusDays(1);
            updateDatePickers(start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the timeframe for which the chart is generated for to the past week.
     */
    @FXML
    public void timeframeOneWeek() {
        try {
            LocalDate start = LocalDate.now().minusWeeks(1);
            updateDatePickers(start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the timeframe for which the chart is generated for to the past month.
     */
    @FXML
    public void timeframeOneMonth() {
        try {
            LocalDate start = LocalDate.now().minusMonths(1);
            updateDatePickers(start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the timeframe for which the chart is generated for to the past year.
     */
    @FXML
    public void timeframeOneYear() {
        try {
            LocalDate start = LocalDate.now().minusYears(1);
            updateDatePickers(start);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateDatePickers(LocalDate start) throws IOException {
        startDatePicker.setValue(start);
        startDate = new Date(start);
        LocalDate end = LocalDate.now();
        endDatePicker.setValue(end);
        endDate = new Date(end);
        for (GraphModel graphModel : graphModels) {
            graphModel.updateTimeInterval(startDate, endDate);
        }
        refreshChart();
    }

    /**
     * Changes the displayed chart to a line chart.
     */
    private void openLineChart() {
        chart = new LineChart();
        chartPane.getChildren().clear();
        chartPane.getChildren().add(chart);
    }

    /**
     * Changes the displayed chart to a bar chart.
     */
    private void openBarChart() {
        chart = new BarChart();
        chartPane.getChildren().clear();
        chartPane.getChildren().add(chart);
    }

    /**
     * Changes the displayed chart to an area chart.
     */
    private void openAreaChart() {
        chart = new AreaChart();
        chartPane.getChildren().clear();
        chartPane.getChildren().add(chart);
    }

    /**
     * @return the current currency selected by the currencyComboBox.
     */
    protected String getCurrency() {
        return currencyComboBox.getSelectionModel().getSelectedItem();
    }

    /**
     * Method which dictates what happens when a {@link ControllerStockListItem} is clicked.
     * @param item the {@link ControllerStockListItem} clicked upon.
     */
    public abstract void stockListOnClick(ControllerStockListItem item);

    /**
     * Saves the Graph.
     */
    @FXML
    abstract protected void saveGraph();
}
