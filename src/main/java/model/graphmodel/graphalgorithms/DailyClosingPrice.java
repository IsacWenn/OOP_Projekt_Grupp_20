package model.graphmodel.graphalgorithms;

import model.util.Date;
import model.datahandling.DayData;

import java.util.HashMap;
import java.util.Map;

/**
 * DailyClosingPrice is class implementing the GraphAlgorithm interface that is used by GraphComputer to retrieve the
 * closing price for each date from an {@link Map} holding the information in a {@link DayData}.
 * @author Carl
 * Uses Date, DayData, Hashmap, Map.
 * Used by GraphAlgorithmCollection.
 */

class DailyClosingPrice implements GraphAlgorithm {
    /**
     * A method that returns the daily closing price of an asset.
     *
     * @param data Is a {@link Map} representing the data for each {@link Date} as a {@link DayData}.
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
