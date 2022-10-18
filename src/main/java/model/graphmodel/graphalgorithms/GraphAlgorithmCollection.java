package model.graphmodel.graphalgorithms;



import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A class used for retrieving different {@link GraphAlgorithm}.
 */
public class GraphAlgorithmCollection {

    /**
     * A static {@link String} containing the name of the {@link GraphAlgorithm} that should be selected as default.
     */
    private static final String defaultGraphAlgorithmName = "Closing Price";

    /**
     * A static {@link Map} used to store a collection of {@link GraphAlgorithm}.
     */
    private static final Map<String, GraphAlgorithm> algorithms = new HashMap<>() {{
        put(defaultGraphAlgorithmName, new DailyClosingPrice());
        put("Daily Change", new DailyChange());
        put("Daily Deviation", new DailyHighMinusLow());
        put("Linear regression", new LinearRegression());
    }};

    /**
     * A method for retrieving a {@link GraphAlgorithm} created from {@link GraphAlgorithmCollection#algorithms}.
     *
     * @param graphAlg is a {@link String} used to get a specific implementation of from {@link GraphAlgorithmCollection#algorithms}.
     * @return A {@link GraphAlgorithm}.
     */
    public static GraphAlgorithm getGraphAlgorithm(String graphAlg) {
        return algorithms.get(graphAlg);
    }

    /**
     * A method for retrieving the {@link String} key set for all {@link GraphAlgorithm} in {@link GraphAlgorithmCollection#algorithms}.
     *
     * @return A {@link Set} of {@link String} for each {@link GraphAlgorithm} in {@link GraphAlgorithmCollection#algorithms}.
     */
    public static Set<String> getGraphAlgorithmNames() {
        return Set.copyOf(algorithms.keySet());
    }

    public static String getDefaultGraphAlgorithmName() {
        return defaultGraphAlgorithmName;
    }
}
