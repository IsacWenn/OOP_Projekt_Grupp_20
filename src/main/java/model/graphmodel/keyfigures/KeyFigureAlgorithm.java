package model.graphmodel.keyfigures;

import model.datahandling.DayData;
import model.util.Date;

import java.util.Map;

/**
 * An interface for different financial key figures. All classes which calculates a key figure has a calculate function
 * which is given data as a {@link Map} containing {@link DayData} for each {@link Date} and returns the key figure as a {@link Double}.
 * @author Pontus.
 * Used by KeyFigureCollection, GraphComputer, AveragePrice, AverageVolume, HighestPrice, LowestPrice, StandardDeviation.
 */
public interface KeyFigureAlgorithm {
    Double calculate(Map<Date, DayData> data);
}
