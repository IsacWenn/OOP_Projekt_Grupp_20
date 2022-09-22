package model.graphmanager;


import model.Date;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;
import model.graphmanager.algorithms.Algorithm;
import model.graphmanager.algorithms.DailyHighMinusLow;

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
        this.values = this.graphComputer.getCalculatedData(graphData.getCompanyData("AAPL"));
    }

    public void update(DateHashMap<Date, Number> currencyConversionMap) {
        this.values = this.graphComputer.getCalculatedData(graphData.getCompanyData("AAPL"),
                DataHandler.getCurrencyData("SEK_USD.csv"));
    }

    public static void main(String[] args) {
        try {

            Date date1 = new Date(2022, 9, 9);
            String mic = "MSFT";
            DateHashMap data = DataHandler.getCompanyData(date1, new Date(), mic);
            // Graph graph = new Graph(new Volatility(data));
            // graph.values = graph.graphData.getCompanyData(mic, date1, new Date());

            Algorithm alg = new DailyHighMinusLow(data);

            System.out.println(alg.calculate(data));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

