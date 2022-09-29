package model.graphmanager.algorithms;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

/**
 * DailyClosingPrice is class implementing the Algorithm interface that is used by {@link model.graphmanager.GraphComputer}
 * to retrieve the closing price for each date from an {@link DateHashMap} holding the information in a {@link DayData}.
 * @author Carl
 */

class DailyClosingPrice implements Algorithm {
    /**
     * A method that returns the daily closing price of an asset.
     *
     * @param data a {@link DateHashMap} containing information in a {@link DayData} with a {@link Date} as a key.
     * @return a {@link DateHashMap} of {@link Date} and {@link Number} contains the closing price for each date.
     */

    @Override
    public DateHashMap<Date, Number> calculate(DateHashMap<Date, DayData> data) {
        DateHashMap<Date, Number> calcData = new DateHashMap<>();
        for (Date date : data.keySet()) {
            DayData dayData = data.get(date);
            calcData.put(date, dayData.getClosed());
        }
        return calcData;
    }
}
