package model.graphmodel;

import model.datahandling.DayData;
import model.graphmodel.graphalgorithms.GraphAlgorithm;
import model.graphmodel.graphalgorithms.GraphAlgorithmFactory;
import model.graphmodel.graphalgorithms.GraphAlgorithms;
import model.graphmodel.keyfigures.KeyFigureAlgorithm;
import model.graphmodel.keyfigures.KeyFigureCollection;
import model.util.Date;

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
    GraphComputer(){
        this.graphAlgorithm = GraphAlgorithmFactory.create(GraphAlgorithms.DAILYCLOSINGPRICE);
    }

    /**
     * A method that sets the current graphAlgorithm to the one called with the method
     *
     * @param graphableENUM A {@link GraphAlgorithms} that represent a {@link GraphAlgorithm} in {@link GraphAlgorithmFactory}.
     */
    @Deprecated
    void setAlgorithm(GraphAlgorithms graphableENUM) {
        this.graphAlgorithm = GraphAlgorithmFactory.create(graphableENUM);
    }
    void setAlgorithm(GraphAlgorithm graphAlg){
        this.graphAlgorithm = graphAlg;
    }

    /**
     * A method that converts the asset prices in a {@link Map} and replaces the old prices with the new currencies price.
     * @param from A {@link Map} containing the currency rates for the currency we want to change from
     * @param to A {@link Map} containing the currency rates for the currency we want to change to
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
     * A method that calls all the {@link KeyFigureAlgorithm} that exists in {@link KeyFigureCollection} to calculate
     * @param keyFigures
     * @param data
     * @return
     */
    Map<String, Double> calculateKeyFigure(Map<String, KeyFigureAlgorithm> keyFigures, Map<Date, DayData> data){
        Map<String, Double> calculatedKeyFigures = new HashMap<>();
        for (Map.Entry<String,KeyFigureAlgorithm> entry : keyFigures.entrySet()){
            double calculatedData = entry.getValue().calculate(data);
            calculatedKeyFigures.put(entry.getKey(), calculatedData);
        }
        return  calculatedKeyFigures;
    }

}
