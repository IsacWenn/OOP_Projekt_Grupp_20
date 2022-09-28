package model.graphmodel.graphablefunctions;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

public class GraphableFactory {
    
    public static DailyChange createDailyChange(DateHashMap<Date, DayData> data) {
        return new DailyChange();
    }

}
