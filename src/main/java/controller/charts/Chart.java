package controller.charts;

import com.sun.javafx.scene.GroupHelper;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import model.graphmodel.GraphModel;
import model.graphmodel.graphalgorithms.GraphAlgorithms;
import model.util.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Chart extends AnchorPane {

    protected XYChart<String, Number> chart;

    public void refreshChart(ArrayList<GraphModel> graphModels) {
        clearChart();
        for (GraphModel graphModel: graphModels) {
            showStockOnChart(graphModel);
        }
    }

    public void showStockOnChart(GraphModel graphModel) {
        XYChart.Series<String, Number> seriesToAdd = new XYChart.Series<>();
        Map<Date, Number> calcData = graphModel.getValues();

        seriesToAdd.setName(graphModel.getName());
        List<Date> orderedDates = Date.sortDates(calcData.keySet());

        double daysInterval = orderedDates.size(), stepAmount, dIndex = 0;
        int index, slot = 0, numDataPoints = 300;

        stepAmount = Math.max(1, daysInterval/numDataPoints);

        while (seriesToAdd.getData().size() < Math.min(numDataPoints, daysInterval)) {
            index = (int) Math.round(dIndex);
            Date currentDate = orderedDates.get(index);
            Number val = calcData.get(currentDate);
            seriesToAdd.getData().add(slot, new XYChart.Data<>(currentDate.toString(), val));
            dIndex += stepAmount; slot++;
        }
        chart.getData().add(seriesToAdd);
    }

    public void removeFromChart(int i) {
        chart.getData().remove(i);
    }

    public void clearChart() {
        chart.getData().clear();
    }
}
