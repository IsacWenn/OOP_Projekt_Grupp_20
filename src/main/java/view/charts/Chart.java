package view.charts;

import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import model.graphmodel.GraphModel;

import java.util.ArrayList;

public abstract class Chart extends AnchorPane {

    protected XYChart<String, Number> chart;

    public void refresh(ArrayList<GraphModel> graphModels) {
        this.clear();
        for (GraphModel graphModel: graphModels) {
            add(graphModel);
        }
    }

    public void add(GraphModel graphModel) {
        XYChart.Series<String, Number> seriesToAdd = graphModel.getChartSeries(300);
        chart.getData().add(seriesToAdd);
    }

    public void remove(int i) {
        chart.getData().remove(i);
    }

    public void clear() {
        chart.getData().clear();
    }
}
