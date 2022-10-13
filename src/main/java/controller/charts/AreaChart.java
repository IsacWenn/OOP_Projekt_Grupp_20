package controller.charts;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import model.graphmodel.graphalgorithms.GraphAlgorithms;

public class AreaChart extends Chart {
    public AreaChart(GraphAlgorithms algorithm) {
        this.algorithm = algorithm;
        javafx.scene.chart.AreaChart<String, Number> areaChart = new javafx.scene.chart.AreaChart<>(new CategoryAxis(), new NumberAxis());
        areaChart.setPrefSize(872, 544);
        areaChart.setCreateSymbols(false);
        areaChart.setAnimated(false);
        this.getChildren().add(areaChart);
        chart = areaChart;
    }
}
