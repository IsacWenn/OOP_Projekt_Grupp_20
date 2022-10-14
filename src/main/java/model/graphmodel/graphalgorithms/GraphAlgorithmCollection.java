package model.graphmodel.graphalgorithms;



import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A class used for retrieving different {@link GraphAlgorithm}.
 */
public class GraphAlgorithmCollection {

    /**
     * A static {@link Map} used to store a collection of {@link GraphAlgorithm}.
     */
    private static Map<String, GraphAlgorithm> algorithms = null;


    /**
     * A method for creating the implementations of {@link GraphAlgorithm} if they are not already created and
     *
     * put them in the static variable {@link GraphAlgorithmCollection#algorithms}.
     */
    public static void init() {
        if (algorithms == null) {
            algorithms = new HashMap<>();
            algorithms.put("Daily Change", new DailyChange());
            algorithms.put("Closing Price", new DailyClosingPrice());
            algorithms.put("Daily Deviation", new DailyHighMinusLow());
            algorithms.put("Linear Regression", new LinearRegression());
        }
    }

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
    public static Set<String> getKeySet(){return Set.copyOf(algorithms.keySet());}
}