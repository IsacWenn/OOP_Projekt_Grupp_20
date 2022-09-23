package model.graphmanager.algorithms;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

import java.util.List;

public class AlgorithmFactory {

    public static DailyChange createDailyChange(DateHashMap<Date, DayData> data) {
        return new DailyChange(data);
    }

}
