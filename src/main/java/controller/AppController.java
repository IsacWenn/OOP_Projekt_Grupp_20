package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import model.AppModel;
import model.Date;
import model.datahandling.DataHandler;
import model.datahandling.DayData;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AppController implements Initializable {

    private final AppModel model = AppModel.getInstance();
    private Map<String, ControllerStockListItem> stockListItemMap = new HashMap<String, ControllerStockListItem>();
    private ArrayList<String> activeCompanies;
    private int precisionAmount;
    private int startDay;
    private int startMonth;
    private int startYear;
    private Date startDate;

    @FXML
    private FlowPane stockPane;

    @FXML
    private LineChart<String, Number> displayedGraph;

    @FXML
    private Spinner<Integer> precisionSpinner;

    @FXML
    public Spinner<Integer> yearSpinner;

    @FXML
    public Spinner<Integer> monthSpinner;

    @FXML
    public Spinner<Integer> daySpinner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeVariables();
        initializeStockPane();
        initializeSpinners();
        try {
            updateDate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateStockList();
    }

    private void initializeVariables() {
        activeCompanies = new ArrayList<String>();
        precisionAmount = 7;
        startDay = 1;
        startMonth = 1;
        startYear = 2012;
    }

    private void initializeSpinners() {
        initializeDaySpinner();
        initializeMonthSpinner();
        initializeYearSpinner();
        initializePrecisionSpinner();
    }

    private void initializeYearSpinner() {
        SpinnerValueFactory<Integer> yearValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2012, 2022, 2012);
        yearSpinner.setValueFactory(yearValueFactory);
        yearSpinner.setInitialDelay(new Duration(10000));

        yearValueFactory.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            startYear = newValue;
            try {
                updateDate();
            } catch (IOException e) {
                e.printStackTrace();
            }
            refreshStocks();
        });

        yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                startYear = Integer.parseInt(yearSpinner.getEditor().getText());
                try {
                    updateDate();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                refreshStocks();
            }
        });
    }

    private void initializeMonthSpinner() {
        SpinnerValueFactory<Integer> monthValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1);
        monthSpinner.setValueFactory(monthValueFactory);
        monthSpinner.setInitialDelay(new Duration(10000));

        monthValueFactory.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                updateDate();
            } catch (IOException e) {
                e.printStackTrace();
            }
            startMonth = newValue;
            refreshStocks();
        });

        monthSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                startMonth = Integer.parseInt(monthSpinner.getEditor().getText());
                try {
                    updateDate();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                refreshStocks();
            }
        });
    }

    private void initializeDaySpinner() {
        SpinnerValueFactory<Integer> dayValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 1);
        daySpinner.setValueFactory(dayValueFactory);
        daySpinner.setInitialDelay(new Duration(10000));

        daySpinner.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            startDay = newValue;
            try {
                updateDate();
            } catch (IOException e) {
                e.printStackTrace();
            }
            refreshStocks();
        });

        daySpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                startDay = Integer.parseInt(daySpinner.getEditor().getText());
                try {
                    updateDate();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                refreshStocks();
            }
        });
    }

    private void initializePrecisionSpinner() {
        SpinnerValueFactory<Integer> precisionValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        precisionSpinner.setValueFactory(precisionValueFactory);
        precisionSpinner.setInitialDelay(new Duration(10000));

        precisionSpinner.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            precisionAmount = newValue;
            refreshStocks();
        });

        precisionSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                precisionAmount = Integer.parseInt(precisionSpinner.getEditor().getText());
                refreshStocks();
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

    public void updateDate() throws IOException {
        startDate = new Date(startYear, startMonth, startDay);
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
        Map<Date, DayData> data = DataHandler.getCompanyData(acronym);
        seriesToAdd.setName(acronym);
        try {
            Date date = new Date(2012,1,1);
            double valueToAdd = 0;
            for(int i = 0, currentCount = 1, slot = 0; i<data.size();) {
                if (data.get(date) != null) {
                    if (date.isAfter(startDate)) {
                        valueToAdd += data.get(date).getClosed();
                        if (currentCount == precisionAmount) {
                            valueToAdd = valueToAdd / precisionAmount;
                            seriesToAdd.getData().add(slot, new XYChart.Data<>(date.toString(), valueToAdd));
                            valueToAdd = 0;
                            currentCount = 1;
                            slot++;
                        } else currentCount++;
                    }
                    i++;
                } date = date.nextDate();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        displayedGraph.setCreateSymbols(false);
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
}









