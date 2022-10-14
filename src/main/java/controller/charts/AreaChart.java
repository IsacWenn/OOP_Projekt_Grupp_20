package controller.charts;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class AreaChart extends Chart {
    public AreaChart() {
        javafx.scene.chart.AreaChart<String, Number> areaChart = new javafx.scene.chart.AreaChart<>(new CategoryAxis(), new NumberAxis());
        areaChart.setPrefSize(872, 544);
        areaChart.setCreateSymbols(false);
        areaChart.setAnimated(false);
        this.getChildren().add(areaChart);
        chart = areaChart;
    }
}
