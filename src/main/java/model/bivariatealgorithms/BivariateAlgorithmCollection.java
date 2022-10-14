package model.bivariatealgorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * {@link BivariateAlgorithmCollection} is a class used to retrieve different {@link BivariateAlgorithms}
 */
class BivariateAlgorithmCollection {

    /**
     * A static {@link Map} used to store a collection of {@link BivariateAlgorithms}.
     */
    private static Map<String, BivariateAlgorithms> algorithms = null;

    /**
     * A method for creating the implementations of {@link BivariateAlgorithms} if they are not already created and
     *
     * put them in the static variable {@link BivariateAlgorithmCollection#algorithms}.
     */
    public static void init() {
        if (algorithms == null) {
            algorithms = new HashMap<>();
            algorithms.put("Pearson correlation", new PearsonCorrelation());
            algorithms.put("Spearman correlation", new SpearmanCorrelation());
        }
    }

    /**
     * A method for retrieving a {@link BivariateAlgorithms} created from {@link BivariateAlgorithmCollection#algorithms}.
     *
     * @param algo is a {@link String} used to get a specific {@link BivariateAlgorithms} from {@link BivariateAlgorithmCollection#algorithms}.
     * @return A {@link BivariateAlgorithms}.
     */
    public static BivariateAlgorithms getGraphAlgorithms(String algo) {
        return algorithms.get(algo);
    }

    /**
     * A method for retrieving the {@link String} key set for all {@link BivariateAlgorithms} in {@link BivariateAlgorithmCollection#algorithms}.
     *
     * @return A {@link Set} of {@link String} for each {@link BivariateAlgorithms} in {@link BivariateAlgorithmCollection#algorithms}.
     */
    public static Set<String> getKeySet() {
        return algorithms.keySet();
    }

}
