package model.graphmodel.graphalgorithms;

import java.util.*;

/**
 * A class used for retrieving different {@link GraphAlgorithm}.
 * @author Carl
 * Uses DailyClosingPrice, DailyChange, DailyHighMinusLow, LinearRegression, GraphAlgorithm.
 * Used by GraphModel.
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
        put("Linear Regression", new LinearRegression());
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
     * A method for retrieving a {@link List} of names of {@link GraphAlgorithmCollection#algorithms} in alphabetic order
     * except the {@link GraphAlgorithmCollection#defaultGraphAlgorithmName} that should be the first element in the list.
     *
     * @return A {@link List} of names as {@link String} for each {@link GraphAlgorithm} in {@link GraphAlgorithmCollection#algorithms}.
     */
    public static List<String> getGraphAlgorithmNames() {
        List<String> returnList = new ArrayList<>();
        List<String> namesInOrder = new ArrayList<>(Set.copyOf(algorithms.keySet()));
        Collections.sort(namesInOrder);

        returnList.add(defaultGraphAlgorithmName);
        for (String algoName : namesInOrder) {
            if (!Objects.equals(algoName, defaultGraphAlgorithmName))
                returnList.add(algoName);
        }
        return returnList;
    }

    /**
     * A method for retrieving the name of the algorithm that should be selected as default.
     *
     * @return A {@link String} representing a {@link GraphAlgorithm}.
     */
    public static String getDefaultGraphAlgorithmName() {
        return defaultGraphAlgorithmName;
    }
}
