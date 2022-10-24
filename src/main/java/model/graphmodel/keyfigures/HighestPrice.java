package model.graphmodel.keyfigures;

import model.datahandling.DayData;
import model.util.Date;

import java.util.Map;

/**
 * A class implementing {@link KeyFigureAlgorithm} for calculation the highest price of an asset.
 */
class HighestPrice implements KeyFigureAlgorithm {

    /**
     * A method that finds the highest price during.
     *
     * @param data Is a {@link Map} representing the data for each {@link Date} as a {@link DayData}.
     * @return a {@link Double} representing the highest price of the asset.
     */
    @Override
    public Double calculate(Map<Date, DayData> data) {
        Double highestPrice = null;
        for (Date date : data.keySet()) {
            double priceOfTheDay = data.get(date).getClosed();
            if ((highestPrice == null) || (priceOfTheDay > highestPrice))
                highestPrice = priceOfTheDay;
        }
        return highestPrice;
    }
}
