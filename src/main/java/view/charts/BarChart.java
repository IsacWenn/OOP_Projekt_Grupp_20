package view.charts;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

/**
 * Class which represents a bar chart.
 */
public class BarChart extends Chart {
    /**
     * Creates a new bar chart.
     */
    public BarChart() {
        javafx.scene.chart.BarChart<String, Number> barChart = new javafx.scene.chart.BarChart<>(new CategoryAxis(), new NumberAxis());
        barChart.setPrefSize(872, 544);
        barChart.setAnimated(false);
        this.getChildren().add(barChart);
        chart = barChart;
    }
}
