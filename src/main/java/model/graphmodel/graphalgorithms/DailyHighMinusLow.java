package model.graphmodel.graphalgorithms;

import model.util.Date;
import model.datahandling.DayData;

import java.util.HashMap;
import java.util.Map;

/**
 * DailyHighMinusLow is class implementing the GraphAlgorithm interface that is used by {@link model.graphmodel.GraphComputer} to
 * perform calculations of the daily difference between the highest and lowest price of an asset.
 *
 * @author Isac
 */

class DailyHighMinusLow implements GraphAlgorithm {

    /**
     * A method that calculates the difference between the highest and lowest price of a given asset expressed in percentages.
     * @return {@link Map}
     */
    @Override
    public Map<Date, Number> calculate(Map<Date, DayData> data) {
        Map<Date, Number> calcData = new HashMap<>();
        for (Date date : data.keySet()) {
            DayData dayData = data.get(date);
            double high = dayData.getHigh();
            double low = dayData.getLow();
            calcData.put(date, high - low);
        }
        return calcData;
    }
}