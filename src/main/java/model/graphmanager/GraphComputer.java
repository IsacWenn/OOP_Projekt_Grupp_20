package model.graphmanager;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import model.graphmanager.algorithms.*;

/**
 * GraphComputer is the class that uses the strategy pattern to change what class that implements {@link Algorithm}
 * it should use
 *
 * @author Pontus
 * @author Carl
 * @author Isac
 */
public class GraphComputer {

    /**
     * A private {@link Algorithm} variable
     */
    private Algorithm algorithm;

    /**
     * A constructor for the GraphComputer class that sets {@link GraphComputer#algorithm} to the default algorithm.
     */
    public GraphComputer(){
        this.algorithm = GraphableFactory.create(Graphables.DAILYCLOSINGPRICE);
    }

    /**
     * A method that sets the current algorithm to the one called with the method
     *
     * @param graphableENUM A {@link Graphables} that represent a {@link Algorithm} in {@link GraphableFactory}.
     */
    void setAlgorithm(Graphables graphableENUM) {
        this.algorithm = GraphableFactory.create(graphableENUM);
    }

    /**
     * A method that converts the asset prices in a {@link DateHashMap} and replaces the old prices with the new currencies price.
     * @param currency A {@link DateHashMap} containing the currency rates for each {@link Date}.
     * @param data A {@link DateHashMap} containing the asset prices for each {@link Date} in a {@link DayData}.
     */
    public void calculateCurrency(DateHashMap<Date, Double> currency,
                                  DateHashMap<Date, DayData> data) {
        for (Date date : data.keySet()) {
            int volume = data.get(date).getVolume();
            double open = data.get(date).getOpen() * currency.get(date);
            double close = data.get(date).getClosed() * currency.get(date);
            double high = data.get(date).getHigh() * currency.get(date);
            double low = data.get(date).getLow() * currency.get(date);
            data.put(date, new DayData(volume, open, close, high, low));
        }
    }

    /**
     * A method that calculates the incoming data with the algorithm in {@link GraphComputer#algorithm}.
     * @param data A {@link DateHashMap} containing data in a {@link DayData} for each {@link Date}.
     * @return A {@link DateHashMap} containing a {@link Number} for each {@link Date}.
     */
    public DateHashMap<Date, Number> updateValues(DateHashMap<Date, DayData> data) {
        return algorithm.calculate(data);
    }

}
