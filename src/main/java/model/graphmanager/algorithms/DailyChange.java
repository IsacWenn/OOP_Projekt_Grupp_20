package model.graphmanager.algorithms;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

import java.io.IOException;

/**
 * DailyChange is class implementing the Algorithm interface that is used by {@link model.graphmanager.GraphComputer} to
 * perform calculations of the daily change of a given asset expressed in percentages.
 *
 * @author Carl
 */

public class DailyChange implements Algorithm{

    /**
     * A method that calculates the daily change of a given asset expressed in percentages.
     * @return {@link DateHashMap}
    */
    @Override
    public DateHashMap<Date, Number> calculate() {
        DateHashMap<Date, Number> calcData = new DateHashMap<>();
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
