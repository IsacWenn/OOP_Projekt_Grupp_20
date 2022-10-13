package model.graphmodel;

import model.datahandling.DayData;
import model.util.Date;

import java.util.Map;

/**
 * A class implementing {@link KeyFigureAlgorithm} for finding the highest price of an asset.
 */
public class HighestPrice implements KeyFigureAlgorithm {

    /**
     * A method that finds the highest price during.
     * @param data is a {@link Map} containing information about the asset as a {@link DayData} for each {@link Date}.
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
