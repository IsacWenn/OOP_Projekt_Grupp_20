package controller;

import javafx.scene.chart.XYChart;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import model.util.Date;

import java.util.*;

public class LineGraphController extends GraphController {

    LineGraphController (AppController parentController) {
        super(parentController);
    }

    @Override
    protected void addStockToGraph(String acronym) {
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
}









