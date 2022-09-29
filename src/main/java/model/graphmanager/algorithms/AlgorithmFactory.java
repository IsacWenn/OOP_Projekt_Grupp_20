package model.graphmanager.algorithms;

/**
 * AlgorithmFactory is a class that acts as a creation front for the different classes that implement {@link Algorithm}
 *
 * @author Carl
 * @author Pontus
 */
public class AlgorithmFactory {

    /**
     * A method that creates a {@link Algorithm}
     *
     * @param graphables an enum that holds all the different {@link Algorithm}
     * @return an instance of a class that matches the enum
     */
    static public Algorithm create(Graphables graphables) {
        if (graphables.equals(Graphables.DAILYCHANGE))
            return new DailyChange();
        if (graphables.equals(Graphables.DAILYCLOSINGPRICE))
            return new DailyClosingPrice();
        if (graphables.equals(Graphables.DAILYHIGHMINUSLOW))
            return new DailyHighMinusLow();
        if (graphables.equals(Graphables.LINEARREGRESSION))
            return new LinearRegression();
        return null;
    }

}
