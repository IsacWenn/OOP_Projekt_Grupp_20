package controller;

import controller.charts.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.AppModel;
import model.datahandling.DataHandler;
import model.graphmodel.graphalgorithms.GraphAlgorithms;
import model.util.Date;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class ChartController extends AnchorPane {

    protected final AppModel appModel = AppModel.getInstance();
    protected AppController parentController;
    protected Map<String, ControllerStockListItem> stockListItemMap = new HashMap<>();
    protected ArrayList<String> activeCompanies;
    protected ArrayList<String> favouriteCompanies;
    protected Date startDate;
    protected Date endDate;
    protected int maxCompanies = 0;
    protected Chart chart;
    protected GraphAlgorithms algorithm;

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
    protected AnchorPane chartPane;

    public ChartController(AppController parentController) {
        this.parentController = parentController;
        favouriteCompanies = new ArrayList<>();
        loadFXML();
        initializeVariables();
        initializeSettings();
        try {
            updateStartDate(startDatePicker.getValue());
            updateEndDate(endDatePicker.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateStockList();
    }

    abstract void loadFXML();

    protected void initializeVariables() {
        try {
            activeCompanies = new ArrayList<String>();
            startDate = new Date(2021, 9, 26);
            endDate = new Date();
            algorithm = GraphAlgorithms.DAILYCLOSINGPRICE;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void initializeSettings() {
        initializeStockPane();
        initializeStartDatePicker();
        initializeEndDatePicker();
        initializeChartTypeComboBox();
        openLineChart();
    }

    private void initializeStartDatePicker() {
        startDatePicker.setValue(LocalDate.now().minusYears(1));

        startDatePicker.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                updateStartDate(newValue);
                refreshStocks();
            } catch (IOException e) {
                startDatePicker.setValue(oldValue);
                e.printStackTrace();
            }
        });

        startDatePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                try {
                    updateStartDate(startDatePicker.getValue());
                    refreshStocks();
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
                updateEndDate(newValue);
                refreshStocks();
            } catch (IOException e) {
                endDatePicker.setValue(oldValue);
                e.printStackTrace();
            }
        });

        endDatePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                try {
                    updateEndDate(endDatePicker.getValue());
                    refreshStocks();
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
            refreshStocks();
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

    public void updateStartDate(LocalDate newDate) throws IOException {
        Date temp = new Date(newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth());
        if (temp.isBefore(endDate)) {
            startDate = temp;
        } else throw new IOException("Invalid date");
    }

    public void updateEndDate(LocalDate newDate) throws IOException {
        Date temp = new Date(newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth());
        if (temp.isAfter(startDate)) {
            endDate = temp;
        } else throw new IOException("Invalid date");
    }

    @FXML
    public void timeframeOneDay() {
        try {
            updateStartDate(LocalDate.now().minusDays(1));
            updateEndDate(LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshStocks();
    }

    @FXML
    public void timeframeOneWeek() {
        try {
            updateStartDate(LocalDate.now().minusWeeks(1));
            updateEndDate(LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshStocks();
    }

    @FXML
    public void timeframeOneMonth() {
        try {
            updateStartDate(LocalDate.now().minusMonths(1));
            updateEndDate(LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshStocks();
    }

    @FXML
    public void timeframeOneYear() {
        try {
            updateStartDate(LocalDate.now().minusYears(1));
            updateEndDate(LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshStocks();
    }

    private void openLineChart() {
        chart = new LineChart(algorithm);
        chartPane.getChildren().clear();
        chartPane.getChildren().add(chart);
    }

    private void openBarChart() {
        chart = new BarChart(algorithm);
        chartPane.getChildren().clear();
        chartPane.getChildren().add(chart);
    }

    private void openAreaChart() {
        chart = new AreaChart(algorithm);
        chartPane.getChildren().clear();
        chartPane.getChildren().add(chart);
    }

    public abstract void stockListOnClick(String acronym);

    protected void removeFromChart(String name) {
        int i = activeCompanies.indexOf(name);
        chart.removeChartFromStock(i);
        activeCompanies.remove(i);
    }

    protected void addToChart(String name) {
        activeCompanies.add(name);
        chart.addStockToChart(name, name, startDate, endDate);
    }

    protected void refreshStocks() {
        chart.clearChart();
        for (String activeCompany : activeCompanies) {
            chart.addStockToChart(activeCompany, activeCompany, startDate, endDate);
        }
    }

    protected boolean withinCompanyLimit(){
        if (maxCompanies == 0) {
            return true;
        } else return activeCompanies.size() < maxCompanies;
    }

    boolean isCompanyActive(String acronym) {
        return activeCompanies.contains(acronym);
    }
}
