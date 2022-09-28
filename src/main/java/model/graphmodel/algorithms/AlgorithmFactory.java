package model.graphmodel.algorithms;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

public class AlgorithmFactory {
    
    public static DailyChange createDailyChange(DateHashMap<Date, DayData> data) {
        return new DailyChange();
    }

}
