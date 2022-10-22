package model.bivariatealgorithms;


import model.util.Date;

import java.util.*;

/**
 * A class for computing the key figures from all {@link BivariateAlgorithms} retrieved from {@link BivariateAlgorithmCollection}.
 */

public class BivariateComputer {

    /**
     * A method for computing the key figures given two series of data.
     * @param series1 a {@link Map} containing {@link Date} connected to a {@link Number}.
     * @param series2 a {@link Map} containing {@link Date} connected to a {@link Number}.
     */
    public static double calculateKeyFigures(String bivariateAlgorithmName, Map<Date, Number> series1, Map<Date, Number> series2) {
        Set<Date> commonDates = series1.keySet();
        commonDates.retainAll(series2.keySet());

        BivariateAlgorithms algo = BivariateAlgorithmCollection.getBivariateAlgorithm(bivariateAlgorithmName);
        return algo.calculateKeyFigure(series1, series2, commonDates);
    }

    /**
     * A getter for retrieving the {@link List} containing the names of all available {@link BivariateAlgorithms} as {@link String}.
     *
     * @return A {@link List} containing the names of different implementations of {@link BivariateAlgorithms} as {@link String}.
     * The names are ordered in alphabetic order in the {@link List}.
     */
    public static List<String> getBivariateAlgorithmNames() {
        List<String> algoKeys = new ArrayList<>(BivariateAlgorithmCollection.getKeySet());
        Collections.sort(algoKeys);
        return algoKeys;
    }
}
