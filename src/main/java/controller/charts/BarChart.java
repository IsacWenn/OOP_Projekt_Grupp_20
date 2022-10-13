package controller.charts;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import model.graphmodel.graphalgorithms.GraphAlgorithms;

public class BarChart extends Chart {
    public BarChart(GraphAlgorithms algorithm) {
        this.algorithm = algorithm;
        javafx.scene.chart.BarChart<String, Number> barChart = new javafx.scene.chart.BarChart<>(new CategoryAxis(), new NumberAxis());
        barChart.setPrefSize(872, 544);
        barChart.setAnimated(false);
        this.getChildren().add(barChart);
        chart = barChart;
    }
}
