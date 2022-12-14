package model.graphmodel.keyfigures;

import model.datahandling.DayData;
import model.util.Date;

import java.util.Map;

/**
 * A class implementing {@link KeyFigureAlgorithm} calculation the standard deviation of an asset´s price.
 * @author Pontus
 * Uses DayData, Date, and Map.
 * Used by KeyFigureCollection
 */
class StandardDeviation implements KeyFigureAlgorithm {

    /**
     * A method that calculation the standard deviation.
     *
     * @param data Is a {@link Map} representing the data for each {@link Date} as a {@link DayData}.
     * @return a {@link Double} representing the standard deviation of an asset´s price.
     */
    @Override
    public Double calculate(Map<Date, DayData> data) {
        double mean = 0;
        double standardDeviation = 0;
        for(Date date : data.keySet()){
            DayData dayData = data.get(date);
            mean+= dayData.getClosed();
        }
        mean /= data.size();
        for(Date date : data.keySet()){
            DayData dayData = data.get(date);
            standardDeviation += Math.pow(dayData.getClosed()-mean, 2);
        }
        return Math.sqrt(standardDeviation/data.size());
    }
}
