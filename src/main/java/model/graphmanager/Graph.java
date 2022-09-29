package model.graphmanager;


import model.util.Date;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;
import model.graphmanager.algorithms.Algorithm;
import model.graphmanager.algorithms.AlgorithmFactory;
import model.graphmanager.algorithms.DailyChange;

import java.io.IOException;

public class Graph {

    DateHashMap<Date, Number> values;

    private GraphData graphData;
    private GraphComputer graphComputer;
    protected Graph(Algorithm alg) {
        this.graphComputer = new GraphComputer(alg);
        this.graphData = new GraphData();
        this.values = new DateHashMap<>();
    }

    public void update() {
        this.values = this.graphComputer.updateValues();
    }

    public void updateAlgorithm(Algorithm alg) {
        this.graphComputer.setAlgorithm(alg);
    }

    public static void main(String[] args) {
        try {

            Date date1 = new Date(2022, 9, 9);
            String mic = "MSFT";
            DateHashMap data = DataHandler.getCompanyData(date1, new Date(), mic);
            // Graph graph = new Graph(new Volatility(data));
            // graph.values = graph.graphData.getCompanyData(mic, date1, new Date());

            Graph graph = new Graph(new DailyChange(data));


            graph.updateAlgorithm(
                    AlgorithmFactory.createDailyChange(
                            graph.graphData.getCompanyData(mic, date1, new Date())
                    )
            );

            graph.update();
            System.out.println(graph.values);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

