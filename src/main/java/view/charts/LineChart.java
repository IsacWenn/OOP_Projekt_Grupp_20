package view.charts;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

/**
 * Class which represents a line chart.
 *
 * Uses:        -
 *
 * Used by:     ChartController, ComparisonChartController,
 *              CorrelationChartController, DetailedChartController
 */
public class LineChart extends Chart {

    /**
     * Creates a new line chart.
     */
    public LineChart() {
        javafx.scene.chart.LineChart<String, Number> lineChart = new javafx.scene.chart.LineChart<>(new CategoryAxis(), new NumberAxis());
        lineChart.setPrefSize(872, 544);
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);
        this.getChildren().add(lineChart);
        chart = lineChart;
    }
}
