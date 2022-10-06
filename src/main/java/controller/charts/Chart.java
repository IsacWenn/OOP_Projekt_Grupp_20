package controller.charts;

import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import model.graphmodel.GraphModel;
import model.graphmodel.graphalgorithms.GraphAlgorithms;
import model.util.Date;

import java.util.List;
import java.util.Map;

public abstract class Chart extends AnchorPane {
    protected GraphAlgorithms algorithm;
    protected XYChart<String, Number> chart;

    public void setAlgorithm(GraphAlgorithms algorithm) {
        this.algorithm = algorithm;
    }

    public void addStockToChart(String acronym, Date startDate, Date endDate) {
        XYChart.Series<String, Number> seriesToAdd = new XYChart.Series<>();

        GraphModel graphModel = new GraphModel(acronym, startDate, endDate);
        graphModel.updateAlgorithm(algorithm);
        graphModel.update();
        Map<Date, Number> calcData = graphModel.getValues();

        seriesToAdd.setName(acronym);
        List<Date> orderedDates;
        orderedDates = Date.sortDates(calcData.keySet());

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

    public void removeChartFromStock(int index) {
        chart.getData().remove(index);
    }

    public void clearChart() {
        chart.getData().clear();
    }
}
