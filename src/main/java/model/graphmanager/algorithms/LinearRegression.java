package model.graphmanager.algorithms;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

import java.util.List;
import java.util.Set;

/**
 * LinearRegression is class implementing the Algorithm interface that is used by {@link model.graphmanager.GraphComputer} to
 * calculate the linear regression of an assets price.
 * @author Carl
 */

public class LinearRegression implements Algorithm {

    /**
     * The private {@link DateHashMap} that holds the data of a {@link DayData} connected by the key of a {@link Date}.
     */
    private DateHashMap<Date, DayData> data;

    /**
     * The private {@link List} that holds the ordered dates provided as arguments for the constructor.
     */
    private List<Date> listOfKeys;

    /**
     * A constructor for class LinearRegression.
     *
     * @param inData a {@link DateHashMap} for the stock market data containing prices and their respective dates.
     */
    public LinearRegression(DateHashMap<Date, DayData> inData) {
        this.data = inData;
        this.listOfKeys = Date.sortDatesQ(data.keySet());
    }

    /**
     * A method that calculates the values of a linear equation constructed by linear regression of a {@link DateHashMap}.
     *
     * @return the {@link Boolean} values of the linear equation corresponding to the provided data.
     */

    @Override
    public DateHashMap<Date, Number> calculate() {
        DateHashMap<Date, Number> calcData;
        double[] coefficients = getCoefficients();
        calcData = getLinearValues(coefficients[0], coefficients[1]);
        return calcData;
    }
    
    private DateHashMap<Date, Number> getLinearValues(double k, double m) {
        DateHashMap<Date, Number> returnData = new DateHashMap<>();
        double xAxisValue = 1;
        for (Date date: listOfKeys) {
            double val = k * xAxisValue + m;
            xAxisValue++;
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
        for (Date date : listOfKeys) {
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
