package model.bivariatealgorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * {@link BivariateAlgorithmCollection} is a class used to retrieve different {@link BivariateAlgorithms}.
 */
public class BivariateAlgorithmCollection {

    /**
     * A static {@link Map} used to store a collection of {@link BivariateAlgorithms}.
     */
    private static final Map<String, BivariateAlgorithms> algorithms = new HashMap<>() {{
        put("Pearson correlation", new PearsonCorrelation());
        put("Spearman correlation", new SpearmanCorrelation());
    }};

    /**
     * A method for retrieving a {@link BivariateAlgorithms} created from {@link BivariateAlgorithmCollection#algorithms}.
     *
     * @param algo is a {@link String} used to get a specific {@link BivariateAlgorithms} from {@link BivariateAlgorithmCollection#algorithms}.
     * @return A {@link BivariateAlgorithms}.
     */
    public static BivariateAlgorithms getBivariateAlgorithm(String algo) {
        return algorithms.get(algo);
    }

    /**
     * A method for retrieving the {@link String} key set for all {@link BivariateAlgorithms} in {@link BivariateAlgorithmCollection#algorithms}.
     *
     * @return A {@link Set} of {@link String} for each {@link BivariateAlgorithms} in {@link BivariateAlgorithmCollection#algorithms}.
     */
    public static Set<String> getKeySet() {
        return Set.copyOf(algorithms.keySet());
    }

}

