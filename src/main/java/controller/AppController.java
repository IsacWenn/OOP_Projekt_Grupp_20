package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.AppModel;
import model.Date;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AppController implements Initializable {

    private final AppModel model = AppModel.getInstance();
    private Map<String, ControllerStockListItem> stockListItemMap = new HashMap<String, ControllerStockListItem>();
    private ArrayList<String> activeCompanies;
    private int precisionAmount;

    @FXML
    private FlowPane stockPane;

    @FXML
    private LineChart<String, Number> chart1;

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
        updateStockList();
    }

    private void initializeVariables() {
        activeCompanies = new ArrayList<String>();
        precisionAmount = 1;
    }

    private void initializeSpinners() {
        SpinnerValueFactory<Integer> precisionValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        precisionSpinner.setValueFactory(precisionValueFactory);

        precisionSpinner.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            precisionAmount = newValue;
            updateStockList();
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
    public void openStockView(String acronym) {
        if (checkActiveCompanies(acronym)) return;
        else activeCompanies.add(acronym);
        addStockToGraph(acronym);
    }

    private void addStockToGraph(String acronym) {
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        DateHashMap<Date, DayData> data = DataHandler.getCompanyData(acronym);
        series1.setName(acronym);
        try {
            Date date = new Date(2012,1,1);
            double valueToAdd = 0;
            for(int i = 0, currentCount = 1, slot = 0; i<data.size();) {
                if (data.get(date) != null) {
                    valueToAdd += data.get(date).getClosed();
                    if (currentCount == precisionAmount) {
                        valueToAdd = valueToAdd/precisionAmount;
                        series1.getData().add(slot, new XYChart.Data<>(date.toString(), valueToAdd));
                        valueToAdd = 0;
                        currentCount = 1;
                        slot++;
                    } else currentCount++;
                    i++;
                } date = date.nextDate();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        chart1.setCreateSymbols(false);
        chart1.getData().add(series1);
    }

    private void refreshStocks() {
        for (String activeCompany : activeCompanies) {
            chart1.getData().remove(0);
            addStockToGraph(activeCompany);
        }
    }

    private boolean checkActiveCompanies(String acronym) {
        if (activeCompanies.contains(acronym)) {
            int i = activeCompanies.indexOf(acronym);
            chart1.getData().remove(i);
            activeCompanies.remove(i);
            return true;
        }
        else return false;
    }
}









