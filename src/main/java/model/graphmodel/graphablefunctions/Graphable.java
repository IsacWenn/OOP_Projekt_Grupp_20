package model.graphmodel.graphablefunctions;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

/**
 * An interface for the strategy pattern where all instances of {@link Graphable} have a calculate method that
 * returns a {@link DateHashMap} containing {@link Number} and {@link Date} based on the data provided in the
 * {@link DateHashMap} containing {@link DayData} and for each {@link Date}.
 * 
 * @author Pontus
 * @author Carl
 */
public interface Graphable {
    DateHashMap<Date, Number> calculate(DateHashMap<Date,DayData> data);

}
