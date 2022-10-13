package model.bivariatealgorithms;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link BivariateAlgorithmCollection} is a static class used to retrieve a collection of {@link BivariateAlgorithms}
 */
class BivariateAlgorithmCollection {
    private static Map<String, BivariateAlgorithms> algorithms = null;

    /**
     * A method for creating the implementations of {@link BivariateAlgorithms} if they are not already created and
     * put them in the static variable {@link BivariateAlgorithmCollection#algorithms}.
     */
    static void init() {
        if (algorithms == null) {
            algorithms = new HashMap<>();
            algorithms.put("Pearson correlation", new PearsonCorrelation());
            algorithms.put("Spearman correlation", new SpearmanCorrelation());
        }
    }

    /**
     * A method for retrieving the {@link BivariateAlgorithms} created from {@link BivariateAlgorithmCollection#algorithms}.
     * @return a {@link HashMap} containing {@link String} as a key to a {@link BivariateAlgorithms}.
     */
    static Map<String, BivariateAlgorithms> getGraphAlgorithms() {
        return algorithms;
    }
}
