package model.graphmanager.algorithms;

import model.util.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

/**
 * DailyHighMinusLow is class implementing the Algorithm interface that is used by {@link model.graphmanager.GraphComputer} to
 * perform calculations of the daily difference between the highest and lowest price of an asset.
 *
 * @author Isac
 */

public class DailyHighMinusLow implements Algorithm{

    /**
     * The private {@link DateHashMap} that holds the stock market data of a {@link DayData} connected by the key of a {@link Date}.
     */
    private DateHashMap<Date, DayData> data;

    /**
     * A constructor for class DailyHighMinusLow.
     *
     * @param inData a {@link DateHashMap} for the stock market data containing prices and their respective dates.
     */
    public DailyHighMinusLow(DateHashMap<Date, DayData> inData) {
        this.data = inData;
    }

    /**
     * A method that calculates the difference between the highest and lowest price of a given asset expressed in percentages.
     * @return {@link DateHashMap}
     */
    @Override
    public DateHashMap<Date, Number> calculate() {
        DateHashMap<Date, Number> calcData = new DateHashMap<>();
        for (Date date : data.keySet()) {
            DayData dayData = data.get(date);
            double high = dayData.getHigh();
            double low = dayData.getLow();
            calcData.put(date, high - low);
        }
        return calcData;
    }
}
