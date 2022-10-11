package model.bivariatealgorithms;


import model.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BivariateComputer {

    public static Double keyFigures = null;

    public static Map<String, Double> calculateKeyFigures(Map<Date, Number> series1, Map<Date, Number> series2) {

        BivariateAlgorithmCollection.init();
        Map<String, BivariateAlgorithms> algorithms = BivariateAlgorithmCollection.getGraphAlgorithms();
        Map<String, Double> calculatedValues = new HashMap<>();

        Set<Date> commonDates = series1.keySet();
        commonDates.retainAll(series2.keySet());

        for (String algorithmName: algorithms.keySet()) {
            double keyfigure = algorithms.get(algorithmName).calculateKeyFigure(series1, series2, commonDates);
            calculatedValues.put(algorithmName, keyfigure);
        }

        return calculatedValues;
    }
}
