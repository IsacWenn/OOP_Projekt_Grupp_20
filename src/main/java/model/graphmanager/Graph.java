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
import java.util.List;

public class Graph {

    DateHashMap<Date, Number> values;
    DateHashMap<Date, DayData> data;
    private GraphData graphData;
    private GraphComputer graphComputer;

    public Graph(String mic){
        init();
        this.data = graphData.getCompanyData(mic);
    }

    public Graph(String mic, Date from, Date to) {
        init();
        this.data = graphData.getCompanyData(mic, from, to);
    }

    public Graph(String mic, List<Date> dates){
        init();
        this.data = graphData.getCompanyData(mic ,dates);
    }

    private void init() {
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
            String mic = "MSFT";
            Graph graph = new Graph(mic, date1, new Date());
            graph.update();
            System.out.println(graph.values);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

