package model.graphmodel;


import model.Date;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import model.graphmodel.algorithms.Algorithm;

import java.io.IOException;

public class GraphModel {

    DateHashMap<Date, Number> values;
    DateHashMap<Date, DayData> data;

    private GraphData graphData;
    private GraphComputer graphComputer;
    public GraphModel() {
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
            GraphModel graphModel = new GraphModel();
            graphModel.update();
            System.out.println(graphModel.values);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

