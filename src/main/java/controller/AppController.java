package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import model.AppModel;
import model.util.Date;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    private final AppModel model = AppModel.getInstance();
    @FXML private LineChart<String, Number> chart1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Stonks");
        populateSeries(series1, "AAPL");
        chart1.setCreateSymbols(false);
        chart1.getData().add(series1);
    }

    private void populateSeries(XYChart.Series<String, Number> series, String mic) {
        DateHashMap<Date, DayData> data = DataHandler.getCompanyData(mic);
        try {
            Date date = new Date(2012,1,1);
            Object close = null;
            for(int i = 0; i<data.size();i++) {
                boolean succeded;
                do {
                    succeded = true;
                    date = date.nextDate();
                    try {
                        close = data.get(date).getClosed();
                    } catch (NullPointerException e) {
                        succeded = false;
                    }
                } while (!succeded);
                float closeValue = (float) close;
                series.getData().add(i, new XYChart.Data<>(date.toString(), closeValue));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}









