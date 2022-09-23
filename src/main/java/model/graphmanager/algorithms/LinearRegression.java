package model.graphmanager.algorithms;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

public class LinearRegression implements Algorithm {

    DateHashMap<Date, DayData> data;

    public LinearRegression(DateHashMap<Date, DayData> inData) {
        this.data = inData;
    }

    @Override
    public DateHashMap<Date, Number> calculate() {
        DateHashMap<Date, Number> calcData = new DateHashMap<>();
        double xAxisValue = 0;
        double sumX = 0;
        double sumY = 0;
        double sumXsq = 0;
        double sumXY = 0;
        for (Date date : data.keySet()) {
            xAxisValue++;
            DayData dayData = data.get(date);
            double yAxisValue = dayData.getClosed();
            sumX += xAxisValue;
            sumY += yAxisValue;
            sumXsq += Math.pow(xAxisValue, 2);
            sumXY += xAxisValue * yAxisValue;

        }
        double denominator = xAxisValue * sumXsq - Math.pow(sumX, 2);
        double a = (sumY * sumXsq - sumX * sumXY) / denominator;
        double b = (xAxisValue * sumXY - sumX * sumY) / denominator;

        //calcData.put(date, result);
        return calcData;
    }

}
