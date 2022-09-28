package model.graphmanager;


import model.Date;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import model.graphmanager.algorithms.Algorithm;
import model.graphmanager.algorithms.AlgorithmFactory;
import model.graphmanager.algorithms.DailyChange;
import model.graphmanager.algorithms.DailyHighMinusLow;

import java.io.IOException;

public class Graph {

    DateHashMap<Date, Number> values;
    DateHashMap<Date, DayData> data;

    private GraphData graphData;
    private GraphComputer graphComputer;
    public Graph() {
        this.graphComputer = new GraphComputer();
        this.graphData = new GraphData();
        this.values = new DateHashMap<>();
    }

    public void update() {
        this.values = this.graphComputer.updateValues(data);
    }

    public void updateAlgorithm(Algorithm alg) {
        this.graphComputer.setAlgorithm(alg);
    }

    public void changeCurrencyValue(String currency){
        this.graphComputer.calculateCurrency(graphData.getCurrencyData(currency), values);
    }

    public static void main(String[] args) {
        try {

            Date date1 = new Date(2022, 9, 9);
            String currency = "SEK_USD";
            DateHashMap<Date, Double> data = DataHandler.getCurrencyData(currency);
            Graph graph = new Graph();
            graph.update();
            System.out.println(graph.values);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

