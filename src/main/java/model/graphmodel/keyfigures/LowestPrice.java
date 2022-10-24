package model.graphmodel.keyfigures;

import model.datahandling.DayData;
import model.util.Date;

import java.util.Map;

/**
 * A class implementing {@link KeyFigureAlgorithm} for calculating the lowest price of an asset.
 * @author Carl
 * Uses DayData, Date, and Map.
 * Used by KeyFigureCollection
 */
class LowestPrice implements KeyFigureAlgorithm {

    /**
     * A method that finds the lowest price during.
     *
     * @param data Is a {@link Map} representing the data for each {@link Date} as a {@link DayData}.
     * @return a {@link Double} representing the lowest price of the asset.
     */
    @Override
    public Double calculate(Map<Date, DayData> data) {
        Double lowestPrice = null;
        for (Date date : data.keySet()) {
            double priceOfTheDay = data.get(date).getClosed();
            if ((lowestPrice == null) || (priceOfTheDay < lowestPrice))
                lowestPrice = priceOfTheDay;
        }
        return lowestPrice;
    }
}
