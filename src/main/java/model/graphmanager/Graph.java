package model.graphmanager;

import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class Graph {
    private ArrayList<XYChart.Series<String, Number>> seriesList;
    private GraphTypes graphType;

    public Graph(ArrayList<XYChart.Series<String, Number>> seriesList, GraphTypes graphType){
        this.seriesList = seriesList;
        this.graphType = graphType;
    }

}

