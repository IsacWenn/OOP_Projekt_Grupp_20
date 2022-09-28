package model.graphmanager;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import model.graphmanager.algorithms.Algorithm;
import model.graphmanager.algorithms.DailyChange;

public class GraphComputer {

    private Algorithm algorithm;

    public GraphComputer(){
        this.algorithm = new DailyChange();
    }

    public GraphComputer(Algorithm alg) {
        this.algorithm = alg;
    }

    void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public DateHashMap<Date, Number> getCalculatedData(DateHashMap<Date, DayData> data) {
        return algorithm.calculate(data);
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
        return algorithm.calculate(data);
    }

}
