package model.graphmanager.algorithms;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

public interface Algorithm {
    DateHashMap<Date, Number> calculate(DateHashMap<Date,DayData> data);

}
