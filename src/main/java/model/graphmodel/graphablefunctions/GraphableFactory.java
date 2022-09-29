package model.graphmodel.graphablefunctions;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

/**
 * GraphableFactory is a class that acts as a creation front for the different classes that implement {@link Graphable}
 *
 * @author Carl
 * @author Pontus
 */
public class GraphableFactory {

    /**
     * A method that creates a {@link Graphable}
     *
     * @param graphables an enum that holds all the different {@link Graphable}
     * @return an instance of a class that matches the enum
     */
    static public Graphable create(Graphables graphables) {
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
