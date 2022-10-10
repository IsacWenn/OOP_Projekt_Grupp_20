package model.graphmodel.graphalgorithms;

import java.util.HashMap;
import java.util.Map;

public class GraphAlgorithmCollection {

    private static Map<String, GraphAlgorithm> algorithms = null;

    private GraphAlgorithmCollection() {
    }

    public static void init() {
        if (algorithms == null) {
            algorithms = new HashMap<>();
            algorithms.put("Daily change", new DailyChange());
            algorithms.put("Daily closing price", new DailyClosingPrice());
            algorithms.put("Daily high minus low", new DailyHighMinusLow());
            algorithms.put("Linear regression", new LinearRegression());
        }
    }

    public static Map<String, GraphAlgorithm> getGraphAlgorithms() {
        return algorithms;
    }
}