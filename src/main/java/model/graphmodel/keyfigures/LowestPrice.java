package model.graphmodel.keyfigures;

import model.datahandling.DayData;
import model.util.Date;

import java.util.Map;

/**
 * A class implementing {@link KeyFigureAlgorithm} for finding the lowest price of an asset.
 */
public class LowestPrice implements KeyFigureAlgorithm {

    /**
     * A method that finds the lowest price during.
     * @param data is a {@link Map} containing information about the asset as a {@link DayData} for each {@link Date}.
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