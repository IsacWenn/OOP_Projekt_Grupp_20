package model.graphmodel.graphalgorithms;

/**
 * GraphAlgorithmFactory is a class that acts as a creation front for the different classes that implement {@link GraphAlgorithm}
 *
 * @author Carl
 * @author Pontus
 */
public class GraphAlgorithmFactory {

    /**
     * A method that creates a {@link GraphAlgorithm}
     *
     * @param graphAlgorithms an enum that holds all the different {@link GraphAlgorithm}
     * @return an instance of a class that matches the enum
     */
    static public GraphAlgorithm create(GraphAlgorithms graphAlgorithms) {
        if (graphAlgorithms.equals(GraphAlgorithms.DAILYCHANGE))
            return new DailyChange();
        if (graphAlgorithms.equals(GraphAlgorithms.DAILYCLOSINGPRICE))
            return new DailyClosingPrice();
        if (graphAlgorithms.equals(GraphAlgorithms.DAILYHIGHMINUSLOW))
            return new DailyHighMinusLow();
        if (graphAlgorithms.equals(GraphAlgorithms.LINEARREGRESSION))
            return new LinearRegression();
        return null; // TODO MÅSTE FIXAS
    }

}
