package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.FlowPane;
import model.AppModel;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class AppController implements Initializable {

    private final AppModel appModel = AppModel.getInstance();
    private Map<String, ControllerStockListItem> stockListItemMap = new HashMap<String, ControllerStockListItem>();
    private ArrayList<String> activeCompanies;
    private int numDataPoints;
    private model.util.Date startDate;
    private model.util.Date endDate;

    @FXML
    private FlowPane stockPane;

    @FXML
    private LineChart<String, Number> displayedGraph;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    private void initializeVariables() {
        try {
            activeCompanies = new ArrayList<String>();
            numDataPoints = 7;
            startDate = new model.util.Date(2021, 9, 26);
            endDate = new model.util.Date();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void initializeSettings() {
        //displayedGraph.setCreateSymbols(false);
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
        model.util.Date temp = new model.util.Date(newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth());
        if (temp.isBefore(endDate)) {
            startDate = temp;
        } else throw new IOException("Invalid date");
    }

    public void updateEndDate(LocalDate newDate) throws IOException {
        model.util.Date temp = new model.util.Date(newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth());
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
        DateHashMap<model.util.Date, DayData> data = DataHandler.getCompanyData(acronym);

        seriesToAdd.setName(acronym);
        List<model.util.Date> orderedDates;
        orderedDates = model.util.Date.sortDates(data.keySet());

        double valueToAdd = 0, currentCount = 1, stepAmount;
        int slot = 0;
        double daysInterval = (startDate.listIntervalTo(endDate).size());

        if (daysInterval <= numDataPoints) {
            stepAmount = 1;
        } else {
            stepAmount = daysInterval/numDataPoints;
        }

        for (model.util.Date currentDate : orderedDates) {
            if (!dateIsWithinLimits(currentDate)) { //TODO importera mindre lista
                continue;
            }
            valueToAdd += data.get(currentDate).getClosed();
            if (currentCount <= stepAmount) {
                valueToAdd = valueToAdd / stepAmount;
                seriesToAdd.getData().add(slot, new XYChart.Data<>(currentDate.toString(), valueToAdd));
                valueToAdd = 0; currentCount = stepAmount - currentCount;
                slot++;
            } else currentCount++;
        }
        displayedGraph.getData().add(seriesToAdd);
    }

    private boolean dateIsWithinLimits(model.util.Date date) {
        return (date.isAfterOrEqual(startDate) && date.isBeforeOrEqual(endDate));
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
}









