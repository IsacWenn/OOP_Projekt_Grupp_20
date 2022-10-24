package model.graphmodel.keyfigures;

import model.datahandling.DayData;
import model.util.Date;

import java.util.Map;

/**
 * A class implementing {@link KeyFigureAlgorithm} for computing the average trading volume for an asset.
 */
class AverageVolume implements KeyFigureAlgorithm {

    /**
     * A method doing the calculation for the average trading volume.
     *
     * @param data Is a {@link Map} representing the data for each {@link Date} as a {@link DayData}.
     * @return a {@link Double} representing the average trading volume.
     */
    @Override
    public Double calculate(Map<Date, DayData> data) {
        double sumOfVolume = 0;
        for (Date date: data.keySet())
            sumOfVolume += data.get(date).getVolume();
        return sumOfVolume / data.size();
    }
}
