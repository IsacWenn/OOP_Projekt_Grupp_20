package model.graphmodel.graphalgorithms;

import model.util.Date;
import model.datahandling.DayData;

import java.util.HashMap;
import java.util.Map;

/**
 * DailyChange is class implementing the GraphAlgorithm interface that is used by {@link model.graphmodel.GraphComputer} to
 * perform calculations of the daily change of a given asset expressed in percentages.
 *
 * @author Carl
 */

class DailyChange implements GraphAlgorithm {

    /**
     * A method that calculates the daily change of a given asset expressed in percentages.
     * @return {@link Map}.
    */
    @Override
    public Map<Date, Number> calculate(Map<Date, DayData> data) {
        Map<Date, Number> calcData = new HashMap<>();
        for (Date date : data.keySet()) {
            DayData dayData = data.get(date);
            double open = dayData.getOpen();
            double closed = dayData.getClosed();
            double result = 100 * (closed - open) / open;
            calcData.put(date, result);
        }
        return calcData;
    }
}
