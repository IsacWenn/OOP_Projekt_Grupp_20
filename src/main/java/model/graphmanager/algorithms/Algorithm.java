package model.graphmanager.algorithms;

import model.datahandling.DayData;
import model.util.Date;
import model.datahandling.DateHashMap;

import java.util.Map;

/**
 * An interface for the strategy pattern where all instances of {@link Algorithm} have a calculate method that
 * returns a {@link DateHashMap} containing {@link Number} and {@link Date} based on the data provided in the
 * {@link DateHashMap} containing {@link DayData} and for each {@link Date}.
 * 
 * @author Pontus
 * @author Carl
 */
public interface Algorithm {
    Map<Date, Number> calculate(Map<Date, DayData> data);

}
