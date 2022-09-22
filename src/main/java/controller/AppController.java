package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
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
    private ArrayList<String> activeCompanies;
    @FXML
    FlowPane stockPane;
    private Map<String, ControllerStockListItem> stockListItemMap = new HashMap<String, ControllerStockListItem>();
    @FXML private LineChart<String, Number> chart1;
    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        activeCompanies = new ArrayList<String>();
        initializeStockPane();
        updateStockList();
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
            Object close = null;
            for(int i = 0; i<data.size(); i++) {
                boolean go;
                do {
                    go = true;
                    date = date.nextDate();
                    try {
                        close = data.get(date).getClosed();
                    } catch (NullPointerException e) {
                        go = false;
                    }
                } while (!go);
                series1.getData().add(i, new XYChart.Data<>(date.toString(), (double)close));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        chart1.setCreateSymbols(false);
        chart1.getData().add(series1);
    }

    private boolean checkActiveCompanies(String acronym) {
        if (activeCompanies.contains(acronym)) {
            int index = activeCompanies.indexOf(acronym);
            chart1.getData().remove(index);
            activeCompanies.remove(index);
            return true;
        }
        else return false;
    }
}









