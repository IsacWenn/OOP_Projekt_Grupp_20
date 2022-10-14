package controller.charts;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class LineChart extends Chart {
    public LineChart() {
        javafx.scene.chart.LineChart<String, Number> lineChart = new javafx.scene.chart.LineChart<>(new CategoryAxis(), new NumberAxis());
        lineChart.setPrefSize(872, 544);
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);
        this.getChildren().add(lineChart);
        chart = lineChart;
    }
}
