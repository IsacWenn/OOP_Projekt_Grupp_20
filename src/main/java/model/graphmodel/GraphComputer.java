package model.graphmodel;

import model.datahandling.DataHandler;
import model.datahandling.DayData;
import model.util.Date;
import model.datahandling.DateHashMap;
import model.graphmodel.graphalgorithms.*;

import java.io.IOException;
import java.util.Map;

/**
 * GraphComputer is the class that uses the strategy pattern to change what class that implements {@link GraphAlgorithm}
 * it should use
 *
 * @author Pontus
 * @author Carl
 * @author Isac
 */
public class GraphComputer {

    /**
     * A private {@link GraphAlgorithm} variable
     */
    private GraphAlgorithm graphAlgorithm;

    /**
     * A constructor for the GraphComputer class that sets {@link GraphComputer#graphAlgorithm} to the default graphAlgorithm.
     */
    public GraphComputer(){
        this.graphAlgorithm = GraphAlgorithmFactory.create(GraphAlgorithms.DAILYCLOSINGPRICE);
    }

    /**
     * A method that sets the current graphAlgorithm to the one called with the method
     *
     * @param graphableENUM A {@link GraphAlgorithms} that represent a {@link GraphAlgorithm} in {@link GraphAlgorithmFactory}.
     */
    void setAlgorithm(GraphAlgorithms graphableENUM) {
        this.graphAlgorithm = GraphAlgorithmFactory.create(graphableENUM);
    }

    /**
     * A method that converts the asset prices in a {@link Map} and replaces the old prices with the new currencies price.
     * @param currency A {@link Map} containing the currency rates for each {@link Date}.
     * @param data A {@link Map} containing the asset prices for each {@link Date} in a {@link DayData}.
     */
    public void calculateCurrency(Map<Date, Double> currency,
                                  Map<Date, DayData> data) {
        for (Date date : data.keySet()) {
            try {
                double rate = DataHandler.retrieveClosestExchangeRate(date, currency);
                int volume = data.get(date).getVolume();
                double open = data.get(date).getOpen() * rate;
                double close = data.get(date).getClosed() * rate;
                double high = data.get(date).getHigh() * rate;
                double low = data.get(date).getLow() * rate;
                data.put(date, new DayData(volume, open, close, high, low));
            } catch (IOException e) {
                System.out.println(e); // TODO behandla exceptions
            }
            
        }
    }

    /**
     * A method that calculates the incoming data with the graphAlgorithm in {@link GraphComputer#graphAlgorithm}.
     * @param data A {@link DateHashMap} containing data in a {@link DayData} for each {@link Date}.
     * @return A {@link DateHashMap} containing a {@link Number} for each {@link Date}.
     */
    public Map<Date, Number> updateValues(Map<Date, DayData> data) {
        return graphAlgorithm.calculate(data);
    }

}
