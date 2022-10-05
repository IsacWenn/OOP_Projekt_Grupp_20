package controller;

import controller.charts.LineChart;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.Chart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.AppModel;
import model.datahandling.DataHandler;
import model.graphmanager.algorithms.Algorithm;
import model.util.Date;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class ChartController extends AnchorPane {
    protected final AppModel appModel = AppModel.getInstance();
    protected AppController parentController;
    protected Algorithm algorithm;
    protected Map<String, ControllerStockListItem> stockListItemMap = new HashMap<String, ControllerStockListItem>();
    protected ArrayList<String> activeCompanies;
    protected Date startDate;
    protected Date endDate;
    protected int maxCompanies = 0;
    protected LineChart chart;

    @FXML
    protected DatePicker startDatePicker;

    @FXML
    protected DatePicker endDatePicker;

    @FXML
    protected FlowPane stockPane;

    @FXML
    private Button timeframeOneDayButton;

    @FXML
    private Button timeframeOneWeekButton;

    @FXML
    private Button timeframeOneMonthButton;

    @FXML
    private Button timeframeOneYearButton;

    @FXML
    private ComboBox<String> chartTypeComboBox;

    @FXML
    private ComboBox<String> algorithmComboBox;

    @FXML
    private AnchorPane chartPane;

    public ChartController(AppController parentController) {
        this.parentController = parentController;
        loadFXML();
        lineChart();
        initializeVariables();
        initializeStockPane();
        initializeSettings();
        try {
            updateStartDate(startDatePicker.getValue());
            updateEndDate(endDatePicker.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateStockList();
    }

    protected void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("../LineChart.fxml")));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    protected void initializeVariables() {
        try {
            activeCompanies = new ArrayList<String>();
            startDate = new Date(2021, 9, 26);
            endDate = new Date();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void initializeSettings() {
        initializeStartDatePicker();
        initializeEndDatePicker();
        initializeChartTypeComboBox();
        initializeAlgorithmComboBox();
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
            System.out.println(newVal);
        });
    }

    private void initializeAlgorithmComboBox() {
        algorithmComboBox.getItems().addAll("Closing Price", "Daily Change", "Daily Deviation", "Linear Regression");
        algorithmComboBox.getSelectionModel().select("Closing Price");
        algorithmComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldVal, newVal) -> {
            System.out.println(newVal);
        });
    }

    protected void initializeStockPane() {
        stockPane.getChildren().clear();
        for (String MIC : DataHandler.getMICs()) {
            ControllerStockListItem listItem = new ControllerStockListItem(MIC, this);
            stockListItemMap.put(MIC, listItem);
        }
    }

    public void updateStockList() {
        stockPane.getChildren().clear();
        for (String MIC : DataHandler.getMICs()) {
            stockPane.getChildren().add(stockListItemMap.get(MIC));
        }
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

    public void timeframeOneDay() {
        try {
            updateStartDate(LocalDate.now().minusDays(1));
            updateEndDate(LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshStocks();
    }

    public void timeframeOneWeek() {
        try {
            updateStartDate(LocalDate.now().minusWeeks(1));
            updateEndDate(LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshStocks();
    }

    public void timeframeOneMonth() {
        try {
            updateStartDate(LocalDate.now().minusMonths(1));
            updateEndDate(LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshStocks();
    }

    public void timeframeOneYear() {
        try {
            updateStartDate(LocalDate.now().minusYears(1));
            updateEndDate(LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshStocks();
    }

    private void lineChart() {
        chart = new LineChart();
        chartPane.getChildren().clear();
        chartPane.getChildren().add(chart);
    }

    public void openStockView(String acronym) {
        if (isCompanyActive(acronym)) {
            removeActiveCompany(acronym);
        } else {
            activeCompanies.add(acronym);
            chart.addStockToChart(acronym, startDate, endDate);
        }
    }

    private void removeActiveCompany(String acronym) {
        int i = activeCompanies.indexOf(acronym);
        chart.removeChartFromStock(i);
        activeCompanies.remove(i);
    }

    private void refreshStocks() {
        chart.clearChart();
        for (String activeCompany : activeCompanies) {
            chart.addStockToChart(activeCompany, startDate, endDate);
        }
    }

    protected boolean withinCompanyLimit(){
        if (maxCompanies == 0) {
            return true;
        } else return activeCompanies.size() < maxCompanies;
    }

    private boolean isCompanyActive(String acronym) {
        return activeCompanies.contains(acronym);
    }
}
