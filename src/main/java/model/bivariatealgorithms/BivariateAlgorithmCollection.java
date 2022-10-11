package model.bivariatealgorithms;

import model.graphmodel.graphalgorithms.*;

import java.util.HashMap;
import java.util.Map;

class BivariateAlgorithmCollection {
    private static Map<String, BivariateAlgorithms> algorithms = null;

    private BivariateAlgorithmCollection() {
    }

    static void init() {
        if (algorithms == null) {
            algorithms = new HashMap<>();
            algorithms.put("Pearson correlation coefficient", new PearsonCorrelation());
        }
    }

    static Map<String, BivariateAlgorithms> getGraphAlgorithms() {
        return algorithms;
    }
}
