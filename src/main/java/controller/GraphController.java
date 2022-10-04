package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.AppModel;
import model.datahandling.DataHandler;
import model.util.Date;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class GraphController extends AnchorPane {
    protected final AppModel appModel = AppModel.getInstance();
    protected AppController parentController;
    @FXML
    protected DatePicker startDatePicker;
    @FXML
    protected DatePicker endDatePicker;
    protected Map<String, ControllerStockListItem> stockListItemMap = new HashMap<String, ControllerStockListItem>();
    protected ArrayList<String> activeCompanies;
    protected Date startDate;
    protected Date endDate;
    protected int maxCompanies = 0;
    @FXML
    protected FlowPane stockPane;
    @FXML
    protected LineChart<String, Number> displayedGraph;

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

    protected abstract void addStockToGraph(String acronym);

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
