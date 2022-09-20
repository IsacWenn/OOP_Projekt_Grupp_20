package model.graphmanager;

import javafx.scene.chart.XYChart;
import model.Date;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;

import java.io.IOException;
import java.util.HashMap;

public class GraphHandler {


    private DateHashMap<Date, HashMap<String, Object>> populateSeries(XYChart.Series<String, Number> series1) {
        DateHashMap<Date, HashMap<String, Object>> data = DataHandler.getCompanyData("AAPL");
        try {
            model.Date date = new Date(2012,1,1);
            Object close = null;
            for(int i = 0; i<data.size();i++) {
                boolean succeded;
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
        return data;
    }

}
