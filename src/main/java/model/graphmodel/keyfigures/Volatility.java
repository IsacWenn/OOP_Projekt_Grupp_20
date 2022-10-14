package model.graphmodel.keyfigures;

import model.datahandling.DayData;
import model.util.Date;

import java.util.Map;

public class Volatility implements KeyFigureAlgorithm {

    @Override
    public Double calculate(Map<Date, DayData> data) {
        double mean = 0;
        double standardDeviation = 0;
        for(Date date : data.keySet()){
            DayData dayData = data.get(date);
            mean+= dayData.getClosed();
        }
        mean /= data.size();
        for(Date date : data.keySet()){
            DayData dayData = data.get(date);
            standardDeviation += Math.pow(dayData.getClosed()-mean, 2);
        }
        return Math.sqrt(standardDeviation/data.size());
    }
}
