package view.charts;

import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import model.graphmodel.GraphModel;
import model.util.Date;

import java.util.ArrayList;
import java.util.Map;

/**
 * Class which represents a chart.
 */
public abstract class Chart extends AnchorPane {

    protected XYChart<String, Number> chart;

    /**
     * Clears and redraws the chart.
     * @param graphModels the {@link ArrayList} of {@link GraphModel}s to be drawn upon the chart.
     *
     */
    public void refresh(ArrayList<GraphModel> graphModels) {
        this.clear();
        for (GraphModel graphModel: graphModels) {
            add(graphModel);
        }
    }

    /**
     * Add the {@link GraphModel} to the chart
     * @param graphModel the {@link GraphModel} to be added.
     */
    public void add(GraphModel graphModel) {
        XYChart.Series<String, Number> chartSeries = new XYChart.Series<>();
        Map<Date, Number> map = graphModel.getSortedAndReducedData(300);
        int i = 0;
        for (Map.Entry<Date, Number> entry : map.entrySet()) {
            chartSeries.getData().add(i, new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
            i++;
        }
        chartSeries.setName(graphModel.getName());
        chart.getData().add(chartSeries);
    }

    /**
     * Removes the given index from the chart.
     * @param i the index of the {@link GraphModel} to be removed.
     */
    public void remove(int i) {
        chart.getData().remove(i);
    }

    /**
     * Clears the chart.
     */
    public void clear() {
        chart.getData().clear();
    }
}
