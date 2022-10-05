package controller.charts;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.graphmodel.GraphModel;
import model.graphmodel.graphalgorithms.GraphAlgorithms;
import model.util.Date;

import java.util.List;
import java.util.Map;

public class LineChart extends javafx.scene.chart.LineChart<String, Number> {
    private GraphAlgorithms algorithm;

    public LineChart() {
        super(new CategoryAxis(), new NumberAxis());
        this.setPrefSize(872, 544);
        this.setCreateSymbols(false);
        this.setAnimated(false);
        algorithm = GraphAlgorithms.DAILYCLOSINGPRICE;
    }

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
        this.getData().add(seriesToAdd);
    }

    public void removeChartFromStock(int index) {
        this.getData().remove(index);
    }

    public void clearChart() {
        this.getData().clear();
    }
}
