package model.graphmodel;

import model.datahandling.DayData;
import model.graphmodel.graphalgorithms.GraphAlgorithm;
import model.graphmodel.keyfigures.KeyFigureAlgorithm;
import model.util.Date;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * GraphComputer is the class that uses the strategy pattern to change what class that implements {@link GraphAlgorithm}
 * it should use.
 *
 * @author Pontus
 * @author Carl
 * @author Isac
 * Uses:  DayData, Date, GraphAlgorithm, KeyFigureAlgorithm
 * Used by: GraphModel
 */
class GraphComputer {

    /**
     * A private {@link GraphAlgorithm} variable
     */
    private GraphAlgorithm graphAlgorithm;


    /**
     * A constructor for the GraphComputer class
     */
    GraphComputer(){
    }

    /**
     * A method that sets the current graphAlgorithm to the one called with the method.
     *
     * @param graphAlg A {@link GraphAlgorithm} that will be assigned to {@link GraphComputer#graphAlgorithm}.
     */
    void setAlgorithm(GraphAlgorithm graphAlg){
        this.graphAlgorithm = graphAlg;
    }

    /**
     * A method that converts the asset prices in a {@link Map} and replaces the old prices with the new currencies price.
     * @param from A {@link Map} containing the currency rates for the currency we want to change from.
     * @param to A {@link Map} containing the currency rates for the currency we want to change to.
     * @param data A {@link Map} containing the asset prices for each {@link Date} in a {@link DayData}.
     */
    Map<Date, DayData> calculateCurrency(Map<Date, Double> from, Map<Date, Double> to, Map<Date, DayData> data) {
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
    Map<Date, Number> calculateValues(Map<Date, DayData> data) {
        return graphAlgorithm.calculate(data);
    }

    /**
     * A method that calculates and returns a value for the given key figure.
     * @param keyFigure the {@link KeyFigureAlgorithm} that the method should calculate with.
     * @param data the {@link Map} of {@link Date} and {@link DayData} that represents the data it will calculate on.
     * @return The calculated key figures as a {@link Double}.
     */
    double calculateKeyFigure(KeyFigureAlgorithm keyFigure, Map<Date, DayData> data){return keyFigure.calculate(data);}

    /**
     * A method for retrieving data sorted after date. The data has a maximum of a given number of datapoint.
     *
     * @param numDataPoints A {@link Integer} is the maximum number of data points
     * @param orderedDates A {@link List} of {@link Date} in chronological order.
     * @param map A {@link Map} representing the data for each {@link Date} as a {@link Number}.
     * @return A {@link Map} of the data points.
     */
    Map<Date, Number> reduceDataPoints(int numDataPoints, List<Date> orderedDates, Map<Date, Number> map) {
        LinkedHashMap<Date, Number> orderedMap = new LinkedHashMap<>();
        double daysInterval = orderedDates.size(), stepAmount, dIndex = 0;
        int index;

        stepAmount = Math.max(1, (daysInterval-1)/ (numDataPoints-1));
        while (orderedMap.size() < Math.min(numDataPoints, daysInterval)) {
            index = (int) Math.round(dIndex);
            Date currentDate = orderedDates.get(index);
            Number val = map.get(currentDate);
            orderedMap.put(currentDate, val);
            dIndex += stepAmount;
        }
        return orderedMap;
    }

}