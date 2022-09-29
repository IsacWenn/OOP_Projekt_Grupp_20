package model.graphmodel;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import model.graphmodel.graphablefunctions.*;

/**
 * GraphComputer is the class that uses the strategy pattern to change what class that implements {@link Graphable}
 * it should use
 *
 * @author Pontus
 * @author Carl
 * @author Isac
 */
public class GraphComputer {

    /**
     * A private {@link Graphable} variable
     */
    private Graphable graphable;

    /**
     * A constructor for the GraphComputer class that sets {@link GraphComputer#graphable} to the default graphable.
     */
    public GraphComputer(){
        this.graphable = GraphableFactory.create(Graphables.DAILYCLOSINGPRICE);
    }

    /**
     * A method that sets the current graphable to the one called with the method
     *
     * @param graphableENUM A {@link Graphables} that represent a {@link Graphable} in {@link GraphableFactory}.
     */
    void setAlgorithm(Graphables graphableENUM) {
        this.graphable = GraphableFactory.createGraphable(graphableENUM);
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
     * A method that calculates the incoming data with the graphable in {@link GraphComputer#graphable}.
     * @param data A {@link DateHashMap} containing data in a {@link DayData} for each {@link Date}.
     * @return A {@link DateHashMap} containing a {@link Number} for each {@link Date}.
     */
    public DateHashMap<Date, Number> updateValues(DateHashMap<Date, DayData> data) {
        return graphable.calculate(data);
    }

}
