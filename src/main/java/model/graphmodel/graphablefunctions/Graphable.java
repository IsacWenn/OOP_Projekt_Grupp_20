package model.graphmodel.graphablefunctions;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

public interface Graphable {
    DateHashMap<Date, Number> calculate(DateHashMap<Date,DayData> data);

}
