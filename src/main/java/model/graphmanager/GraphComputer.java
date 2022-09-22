package model.graphmanager;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import model.graphmanager.algorithms.Algorithm;

public class GraphComputer {

    private Algorithm algorithm;

    public GraphComputer(Algorithm alg) {
        this.algorithm = alg;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public DateHashMap<Date, Number> getCalculatedData(DateHashMap<Date, DayData> data) {
        return algorithm.calculate(data);
    }

    public DateHashMap<Date, Number> getCalculatedData(DateHashMap<Date, DayData> data,
                                                       DateHashMap<Date, Double> currency) {
        DateHashMap<Date, Number> processedData = algorithm.calculate(data);
        for (Date date : data.keySet()) {
            double processedDouble = (double) processedData.get(date);
            processedDouble *= currency.get(date);
            processedData.put(date, processedDouble);
        }
        return processedData;
    }

}
