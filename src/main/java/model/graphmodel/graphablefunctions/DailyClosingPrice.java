package model.graphmodel.graphablefunctions;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

public class DailyClosingPrice implements Graphable {
    /**
     * A method that returns the daily closing price of an asset.
     *
     * @return {@link DateHashMap}
     */

    @Override
    public DateHashMap<Date, Number> calculate(DateHashMap<Date, DayData> data) {
        DateHashMap<Date, Number> calcData = new DateHashMap<>();
        for (Date date : data.keySet()) {
            DayData dayData = data.get(date);
            calcData.put(date, dayData.getClosed());
        }
        return calcData;
    }
}