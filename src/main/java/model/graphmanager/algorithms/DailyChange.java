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
     * The private {@link DateHashMap} that holds the data of a {@link DayData} connected by the key of a {@link Date}.
     */
    private DateHashMap<Date, DayData> data;


    /**
     * A constructor for class DailyChange.
     *
     * @param inData a {@link DateHashMap} for the stock market data containing prices and their respective dates.
     */
    public DailyChange(DateHashMap<Date, DayData> inData) {
        this.data = inData;
    }

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
