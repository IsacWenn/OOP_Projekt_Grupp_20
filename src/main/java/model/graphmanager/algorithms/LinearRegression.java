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
     * A method that calculates the values of a linear equation constructed by linear regression of a {@link DateHashMap}.
     *
     * @return the {@link Boolean} values of the linear equation corresponding to the provided data.
     */

    @Override
    public DateHashMap<Date, Number> calculate(DateHashMap<Date, DayData> data) {
        DateHashMap<Date, Number> calcData;
        double[] coefficients = getCoefficients();
        calcData = getLinearValues(coefficients[0], coefficients[1]);
        return calcData;
    }

    /**
     * A method that calculates the values of a linear equation given coefficients.
     * @param k the {@link Double} is the slope of the function.
     * @param m the {@link Double} is the y-intercept of the function.
     * @return the {@link Boolean} values of the linear equation over timeframe {@link LinearRegression#listOfKeys}.
     */
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

    /**
     * A method that calculates the coefficients of {@link LinearRegression#listOfKeys}.
     * @return an array of {@link Double}s.
     */
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
