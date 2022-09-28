package model.graphmanager;

import model.util.Date;
import model.datahandling.DateHashMap;
import model.graphmanager.algorithms.Algorithm;

public class GraphComputer {

    private Algorithm algorithm;

    public GraphComputer(Algorithm alg) {
        this.algorithm = alg;
    }

    void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public DateHashMap<Date, Number> getCalculatedData() {
        return algorithm.calculate();
    }

    public DateHashMap<Date, Number> getCalculatedData(DateHashMap<Date, Double> currency) {
        DateHashMap<Date, Number> processedData = algorithm.calculate();
        for (Date date : processedData.keySet()) {
            double processedDouble = (double) processedData.get(date);
            processedDouble *= currency.get(date);
            processedData.put(date, processedDouble);
        }
        return processedData;
    }

    DateHashMap<Date, Number> updateValues() {
        return algorithm.calculate();
    }


}
