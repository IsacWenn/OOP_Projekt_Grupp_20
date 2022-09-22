package model.graphmanager.algorithms;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

public class DailyHighMinusLow implements Algorithm{

    DateHashMap<Date, DayData> data;

    public DailyHighMinusLow(DateHashMap<Date, DayData> inData) {
        this.data = inData;
    }
    @Override
    public DateHashMap<Date, Number> calculate() {
        DateHashMap<Date, Number> calcData = new DateHashMap<>();
        for (Date date : data.keySet()) {
            DayData dayData = data.get(date);
            double high = dayData.getHigh();
            double low = dayData.getLow();
            calcData.put(date, high - low);
        }
        return calcData;
    }
}
