package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import model.AppModel;
import model.Date;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;


import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    private final AppModel model = AppModel.getInstance();
    @FXML private LineChart<String, Number> chart1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Stonks");
        DateHashMap<Date, HashMap<String, Object>> data = DataHandler.getCompanyData("AAPL");
        try {

            Date date = new Date(2012,1,1);
            Object close = null;
            for(int i = 0; i<data.size();i++) {
                boolean succeded = true;
                do {
                    succeded = true;
                    date = date.nextDate();
                    try {
                        close = data.get(date).get("close");
                    } catch (NullPointerException e) {
                        succeded = false;
                    }
                } while (!succeded);


                float closeValue = (float) close;

                series1.getData().add(i, new XYChart.Data<>(date.toString(), closeValue));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        chart1.setCreateSymbols(false);
        chart1.getData().add(series1);
    }
}









