package model.graphmanager.algorithms;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

/**
 * LinearRegression is class implementing the Algorithm interface that is used by {@link model.graphmanager.GraphComputer} to
 * calculate the linear regression of a stocks price.
 * @author Carl
 */

public class LinearRegression implements Algorithm {

    DateHashMap<Date, DayData> data;

    public LinearRegression(DateHashMap<Date, DayData> inData) {
        this.data = inData;
    }

    @Override
    public DateHashMap<Date, Number> calculate() {
        DateHashMap<Date, Number> calcData = new DateHashMap<>();
        double[] coefficients = getCoefficients();
        calcData = getLinearValues(coefficients[0], coefficients[1]);
        return calcData;
    }

    private DateHashMap<Date, Number> getLinearValues(double k, double m) {
        DateHashMap<Date, Number> returnData = new DateHashMap<>();
        for (Date date: data.keySet()) {
            double val = k * x + m;
            returnData.put(date, val);
        }
        return returnData;
    }

    private double[] getCoefficients() {
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
        double k = (sumY * sumXsq - sumX * sumXY) / denominator;
        double m = (xAxisValue * sumXY - sumX * sumY) / denominator;
        return new double[]{k, m};
    }

}
