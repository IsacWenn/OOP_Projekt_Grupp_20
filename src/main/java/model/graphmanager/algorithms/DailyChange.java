package model.graphmanager.algorithms;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

public class DailyChange {

    DateHashMap<Date, DayData> data;

    public DailyChange(DateHashMap<Date, DayData> inData) {
        this.data = inData;
    }
    @Override
    public DateHashMap<Date, Number> calculate() {
        DateHashMap<Date, Number> calcData = new DateHashMap<>();
        for (Date date : data.keySet()) {
            DayData dayData = data.get(date);
            double open = dayData.getOpen();
            double closed = dayData.getClosed();
            calcData.put(date, closed / open);
        }
        return calcData;
    }
}
