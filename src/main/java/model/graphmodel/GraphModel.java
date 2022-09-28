package model.graphmodel;


import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import model.graphmodel.graphablefunctions.Graphable;

import java.io.IOException;
import java.util.List;

public class GraphModel {

    DateHashMap<Date, Number> values;
    DateHashMap<Date, DayData> data;
    private GraphData graphData;
    private GraphComputer graphComputer;

    public GraphModel(String mic){
        init();
        this.data = graphData.getCompanyData(mic);
    }

    public GraphModel(String mic, Date from, Date to) {
        init();
        this.data = graphData.getCompanyData(mic, from, to);
    }

    public GraphModel(String mic, List<Date> dates){
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

    public void updateAlgorithm(Graphable alg) {
        this.graphComputer.setAlgorithm(alg);
    }

    public void changeCurrencyValue(String currency){
        this.graphComputer.calculateCurrency(graphData.getCurrencyData(currency), values);
    }

    public DateHashMap<Date, Number> getValues() {  // TODO Should we have this getter?
        return this.values;
    }

    public static void main(String[] args) {
        try {

            Date date1 = new Date(2022, 9, 9);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

