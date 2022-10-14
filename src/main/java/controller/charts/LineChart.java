package controller.charts;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import model.graphmodel.graphalgorithms.GraphAlgorithms;

public class LineChart extends Chart {
    public LineChart(GraphAlgorithms algorithm) {
        this.algorithm = algorithm;
        javafx.scene.chart.LineChart<String, Number> lineChart = new javafx.scene.chart.LineChart<>(new CategoryAxis(), new NumberAxis());
        lineChart.setPrefSize(872, 544);
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);
        this.getChildren().add(lineChart);
        chart = lineChart;
    }
}
