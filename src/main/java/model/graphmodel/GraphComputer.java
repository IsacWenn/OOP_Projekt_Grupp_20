package model.graphmodel;

import model.datahandling.DayData;
import model.graphmodel.graphalgorithms.GraphAlgorithm;
import model.graphmodel.graphalgorithms.GraphAlgorithmFactory;
import model.graphmodel.graphalgorithms.GraphAlgorithms;
import model.util.Date;
import model.graphalgorithms.*;

import java.util.HashMap;
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
     * @param from A {@link Map} containing the currency rates for the currency we want to change from
     * @param to A {@link Map} containing the currency rates for the currency we want to change to
     * @param data A {@link Map} containing the asset prices for each {@link Date} in a {@link DayData}.
     */
    public Map<Date, DayData> calculateCurrency(Map<Date, Double> from, Map<Date, Double> to,
                                                Map<Date, DayData> data) {

        Map<Date, DayData> adjustedMap = new HashMap<>();
        for (Date date : data.keySet()) {
            double fromRate = from.get(date);
            double toRate = to.get(date);
            double combinedRate = toRate / fromRate;

            int volume = data.get(date).getVolume();
            double open = data.get(date).getOpen() * combinedRate;
            double close = data.get(date).getClosed() * combinedRate;
            double high = data.get(date).getHigh() * combinedRate;
            double low = data.get(date).getLow() * combinedRate;
            adjustedMap.put(date, new DayData(volume, open, close, high, low));
        }
        return adjustedMap;
    }

    /**
     * A method that calculates the incoming data with the graphAlgorithm in {@link GraphComputer#graphAlgorithm}.
     * @param data A {@link Map} containing data in a {@link DayData} for each {@link Date}.
     * @return A {@link Map} containing a {@link Number} for each {@link Date}.
     */
    public Map<Date, Number> updateValues(Map<Date, DayData> data) {
        return graphAlgorithm.calculate(data);
    }

}
