package model.graphmodel.graphablefunctions;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

public class GraphableFactory {

    static Graphable createGraphable(Graphables graphables) {
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
