package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.AppModel;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import model.graphmanager.algorithms.Algorithm;
import model.util.Date;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GraphController extends AnchorPane {
    protected final AppModel appModel = AppModel.getInstance();
    protected AppController parentController;
    protected Algorithm algorithm;
    protected Map<String, ControllerStockListItem> stockListItemMap = new HashMap<String, ControllerStockListItem>();
    protected ArrayList<String> activeCompanies;
    protected Date startDate;
    protected Date endDate;
    protected int maxCompanies = 0;

    @FXML
    protected DatePicker startDatePicker;

    @FXML
    protected DatePicker endDatePicker;

    @FXML
    protected FlowPane stockPane;

    @FXML
    protected LineChart<String, Number> displayedGraph;

    @FXML
    private Button timeframeOneDayButton;

    @FXML
    private Button timeframeOneWeekButton;

    @FXML
    private Button timeframeOneMonthButton;

    @FXML
    private Button timeframeOneYearButton;

    public GraphController(AppController parentController) {
        this.parentController = parentController;
        loadFXML();
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
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("../LineGraph.fxml")));
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
        displayedGraph.setCreateSymbols(false);
        initializeStartDatePicker();
        initializeEndDatePicker();
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

    public void openStockView(String acronym) {
        if (isCompanyActive(acronym)) {
            removeActiveCompany(acronym);
        } else {
            activeCompanies.add(acronym);
            addStockToGraph(acronym);
        }
    }

    private void removeActiveCompany(String acronym) {
        int i = activeCompanies.indexOf(acronym);
        displayedGraph.getData().remove(i);
        activeCompanies.remove(i);
    }

    protected void addStockToGraph(String acronym) {
        XYChart.Series<String, Number> seriesToAdd = new XYChart.Series<>();
        DateHashMap<Date, DayData> data = DataHandler.getCompanyData(startDate, endDate, acronym);

        DateHashMap<Date, Number> calcData = this.algorithm.calculate(data);

        seriesToAdd.setName(acronym);
        List<Date> orderedDates;
        orderedDates = Date.sortDates(calcData.keySet());

        double daysInterval = orderedDates.size(), stepAmount, dIndex = 0;
        int index, slot = 0, numDataPoints = 300;

        stepAmount = Math.max(1, daysInterval/numDataPoints);

        while (seriesToAdd.getData().size() < Math.min(numDataPoints, daysInterval)) {
            index = (int) Math.round(dIndex);
            Date currentDate = orderedDates.get(index);
            Number val = calcData.get(currentDate);
            seriesToAdd.getData().add(slot, new XYChart.Data<>(currentDate.toString(), val));
            dIndex += stepAmount; slot++;
        }
        displayedGraph.getData().add(seriesToAdd);
    }

    private void refreshStocks() {
        displayedGraph.getData().clear();
        for (String activeCompany : activeCompanies) {
            addStockToGraph(activeCompany);
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
