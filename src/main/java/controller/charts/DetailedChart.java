package controller.charts;

import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import model.graphmodel.GraphModel;
import model.graphmodel.graphalgorithms.GraphAlgorithms;
import model.util.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailedChart extends AnchorPane {

    private Chart chart;
    ArrayList<GraphAlgorithms> algorithms;

    public void addStockToChart(String acronym, Date startDate, Date endDate) {
        for (GraphAlgorithms algorithm : algorithms) {
            chart.setAlgorithm(algorithm);
            chart.addStockToChart(algorithm.name(), startDate, endDate);
        }
    }

    public void removeChartFromStock(int index) {
        chart.chart.getData().remove(index);
    }

    public void clearChart() {
        chart.chart.getData().clear();
    }
}
