package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.AppModel;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import model.util.Date;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class LineGraphController extends AnchorPane {

    private final AppModel appModel = AppModel.getInstance();
    private Map<String, ControllerStockListItem> stockListItemMap = new HashMap<String, ControllerStockListItem>();
    private ArrayList<String> activeCompanies;
    private Date startDate;
    private Date endDate;
    private AppController parentController;

    @FXML
    private FlowPane stockPane;

    @FXML
    private LineChart<String, Number> displayedGraph;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    LineGraphController (AppController parentController) {
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

    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("../LineGraph.fxml")));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void initializeVariables() {
        try {
            activeCompanies = new ArrayList<String>();
            startDate = new Date(2021, 9, 26);
            endDate = new Date();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void initializeSettings() {
        displayedGraph.setCreateSymbols(false);
        initializeStartDatePicker();
        initializeEndDatePicker();
    }

    private void initializeStartDatePicker() {
        startDatePicker.setValue(LocalDate.of(2021, 9, 26));

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
        endDatePicker.setValue(LocalDate.of(2022, 9, 26));

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

    private void initializeStockPane() {
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

    private void addStockToGraph(String acronym) {
        XYChart.Series<String, Number> seriesToAdd = new XYChart.Series<>();
        DateHashMap<Date, DayData> data = DataHandler.getCompanyData(startDate, endDate, acronym);

        seriesToAdd.setName(acronym);
        List<Date> orderedDates;
        orderedDates = Date.sortDates(data.keySet());

        double daysInterval = orderedDates.size(), stepAmount, dIndex = 0;
        int index, slot = 0, numDataPoints = 300;

        stepAmount = Math.max(1, daysInterval/numDataPoints);

        while (seriesToAdd.getData().size() < Math.min(numDataPoints, daysInterval)) {
            index = (int) Math.round(dIndex);
            Date currentDate = orderedDates.get(index);
            double val = data.get(currentDate).getClosed();
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

    private boolean isCompanyActive(String acronym) {
        return activeCompanies.contains(acronym);
    }

    public void closeTab() {
        this.closeTab();
    }
}









