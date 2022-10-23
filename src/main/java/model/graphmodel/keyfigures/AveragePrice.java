package model.graphmodel.keyfigures;

import model.datahandling.DayData;
import model.util.Date;

import java.util.Map;

/**
 * A class implementing {@link KeyFigureAlgorithm} for computing the average price of an asset traded during the period of the provided data.
 */
class AveragePrice implements KeyFigureAlgorithm {

    /**
     * A method doing the calculation for the average price of a stock.
     * @param data is a {@link Map} containing information about the asset as a {@link DayData} for each {@link Date}.
     * @return a {@link Double} representing the average price of the asset.
     */
    @Override
    public Double calculate(Map<Date, DayData> data) {
        double sumOfVolumeInNumberOfStocks = 0;
        double sumOfVolumeInMoney = 0;
        for (Date date : data.keySet()) {
            DayData dayData = data.get(date);
            sumOfVolumeInNumberOfStocks += dayData.getVolume();
            sumOfVolumeInMoney += dayData.getVolume() * dayData.getClosed();
        }
        return sumOfVolumeInMoney / sumOfVolumeInNumberOfStocks;
    }
}
