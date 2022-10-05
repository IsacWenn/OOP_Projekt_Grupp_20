package model.graphmanager.algorithms;

import model.util.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

import java.util.HashMap;
import java.util.Map;

/**
 * DailyClosingPrice is class implementing the Algorithm interface that is used by {@link model.graphmanager.GraphComputer}
 * to retrieve the closing price for each date from an {@link Map} holding the information in a {@link DayData}.
 * @author Carl
 */

class DailyClosingPrice implements Algorithm {
    /**
     * A method that returns the daily closing price of an asset.
     *
     * @param data a {@link Map} containing information in a {@link DayData} with a {@link Date} as a key.
     * @return a {@link Map} of {@link Date} and {@link Number} contains the closing price for each date.
     */

    @Override
    public Map<Date, Number> calculate(Map<Date, DayData> data) {
        Map<Date, Number> calcData = new HashMap<>();
        for (Date date : data.keySet()) {
            DayData dayData = data.get(date);
            calcData.put(date, dayData.getClosed());
        }
        return calcData;
    }
}
