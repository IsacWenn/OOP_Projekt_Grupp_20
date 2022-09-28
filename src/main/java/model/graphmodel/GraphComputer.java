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
                                  DateHashMap<Date, Number> data) {
        for (Date date : data.keySet()) {
            double processedDouble = (double) data.get(date);
            processedDouble *= currency.get(date);
            data.put(date, processedDouble);
        }
    }

    public DateHashMap<Date, Number> updateValues(DateHashMap<Date, DayData> data) {
        return graphable.calculate(data);
    }

}
