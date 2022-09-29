package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import model.AppModel;
import model.Date;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import model.graphmanager.Graph;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class AppController implements Initializable {
    private final AppModel model = AppModel.getInstance();
    @FXML private LineChart<String, Number> chart1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series1.setName("Stock");
        series2.setName("LinReg");
        populateSeries(series1, "AAPL");

        chart1.setCreateSymbols(false);
        //chart1.getData().add(series1);
        chart1.getData().add(series2);
    }

    private void populateSeries(XYChart.Series<String, Number> series, String mic) {
        DateHashMap<Date, DayData> data = DataHandler.getCompanyData(mic);
        try {
            Date date1 = new Date(2020,1,1);
            Date date2 = new Date(2020,3,1);

            Graph graph = new Graph("TSLA", date1, date2);
            graph.changeCurrency("SEK_USD.csv");
            graph.update();

            DateHashMap<Date, Number> values = graph.getValues();

            List<Date> sortedDates = Date.sortDates((Set<Date>) values.keySet());

            int index = 0;
            for (Date date : sortedDates) {
                Number yVal = values.get(date);
                series.getData().add(index, new XYChart.Data<>(date.toString(), yVal));
                index++;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}









