package model.bivariatealgorithms;


import model.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A class for computing the key figures from all {@link BivariateAlgorithms} retrieved from {@link BivariateAlgorithmCollection}.
 */

public class BivariateComputer {

    /**
     * A class variable containing the key figures from the last time the {@link BivariateComputer#calculateKeyFigures(Map, Map)} was calculated.
     */
    public static Map<String, Double> keyFigures = null;

    /**
     * A method for computing the key figures given two series of data.
     * @param series1 a {@link Map} containing {@link Date} connected to a {@link Number}.
     * @param series2 a {@link Map} containing {@link Date} connected to a {@link Number}.
     */
    public static void calculateKeyFigures(Map<Date, Number> series1, Map<Date, Number> series2) {

        BivariateAlgorithmCollection.init();
        Map<String, BivariateAlgorithms> algorithms = BivariateAlgorithmCollection.getGraphAlgorithms();
        Map<String, Double> calculatedValues = new HashMap<>();

        Set<Date> commonDates = series1.keySet();
        commonDates.retainAll(series2.keySet());

        for (String algorithmName: algorithms.keySet()) {
            double keyfigure = algorithms.get(algorithmName).calculateKeyFigure(series1, series2, commonDates);
            calculatedValues.put(algorithmName, keyfigure);
        }

        keyFigures = calculatedValues;
    }

    /**
     * A getter for retrieving the {@link BivariateComputer#keyFigures}.
     * @return A {@link Map} containing the key figures as a {@link Double} and the name of the key figure as a {@link String}.
     */
    public static Map<String, Double> getKeyFigures() {
        return keyFigures;
    }
}
