package model.graphmodel.graphalgorithms;

import model.datahandling.DayData;
import model.util.Date;

import java.util.Map;

/**
 * An interface for the strategy pattern where all instances of {@link GraphAlgorithm} have a calculate method that
 * returns a {@link Map} containing {@link Number} and {@link Date} based on the data provided in the
 * {@link Map} containing {@link DayData} and for each {@link Date}.
 * 
 * @author Pontus
 * @author Carl
 */
public interface GraphAlgorithm {
    Map<Date, Number> calculate(Map<Date, DayData> data);

}
