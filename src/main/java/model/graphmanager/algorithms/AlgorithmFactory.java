package model.graphmanager.algorithms;

import model.util.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

public class AlgorithmFactory {

    public static DailyChange createDailyChange(DateHashMap<Date, DayData> data) {
        return new DailyChange(data);
    }

}
