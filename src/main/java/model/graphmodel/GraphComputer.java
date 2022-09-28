package model.graphmodel;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import model.graphmodel.graphablefunctions.DailyClosingPrice;
import model.graphmodel.graphablefunctions.Graphable;
import model.graphmodel.graphablefunctions.DailyChange;

public class GraphComputer {

    private Graphable graphable;

    public GraphComputer(){
        this.graphable = new DailyClosingPrice();
    }

    void setAlgorithm(Graphable graphable) {
        this.graphable = graphable;
    }

    public DateHashMap<Date, Number> getCalculatedData(DateHashMap<Date, DayData> data) {
        return graphable.calculate(data);
    }

    public void calculateCurrency(DateHashMap<Date, Double> currency,
                                  DateHashMap<Date, DayData> data) {
        for (Date date : data.keySet()) {
            int volume = data.get(date).getVolume();
            double open = data.get(date).getOpen() * currency.get(date);
            double close = data.get(date).getClosed() * currency.get(date);
            double high = data.get(date).getHigh() * currency.get(date);
            double low = data.get(date).getLow() * currency.get(date);
            data.put(date, new DayData(volume, open, close, high, low));
        }
    }

    public DateHashMap<Date, Number> updateValues(DateHashMap<Date, DayData> data) {
        return graphable.calculate(data);
    }

}
