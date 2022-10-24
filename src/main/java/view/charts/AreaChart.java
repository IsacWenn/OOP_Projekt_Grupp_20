package view.charts;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

/**
 * Class which represents an area chart.
 */
public class AreaChart extends Chart {
    /**
     * Creates a new area chart.
     */
    public AreaChart() {
        javafx.scene.chart.AreaChart<String, Number> areaChart = new javafx.scene.chart.AreaChart<>(new CategoryAxis(), new NumberAxis());
        areaChart.setPrefSize(872, 544);
        areaChart.setCreateSymbols(false);
        areaChart.setAnimated(false);
        this.getChildren().add(areaChart);
        chart = areaChart;
    }
}
