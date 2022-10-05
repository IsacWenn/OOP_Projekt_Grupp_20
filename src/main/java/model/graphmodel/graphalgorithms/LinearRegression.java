package model.graphmodel.graphalgorithms;

import model.util.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LinearRegression is class implementing the GraphAlgorithm interface that is used by {@link model.graphmodel.GraphComputer} to
 * calculate the linear regression of a dataset.
 * @author Carl
 */

class LinearRegression implements GraphAlgorithm {

    /**
     * A method that calculates the values of a linear equation constructed by linear regression of a {@link DateHashMap}.
     * @param data {@link DateHashMap} containing {@link Date} and {@link DayData}
     * @return the {@link Boolean} values of the linear equation corresponding to the provided data.
     */

    @Override
    public Map<Date, Number> calculate(Map<Date, DayData> data) {;
        List<Date> sortedDates = Date.sortDates(data.keySet());
        double[] coefficients = getCoefficients(data, sortedDates);
        return getLinearValues(coefficients[1], coefficients[0], sortedDates);
    }

    /**
     * A method that calculates the values of a linear equation given coefficients.
     * @param k A {@link Double} is the slope of the function.
     * @param m A {@link Double} is the y-intercept of the function.
     * @param sortedDates A {@link List} containing {@link Date} in chronological order.
     * @return A {@link Map} containing values of the linear equation over timeframe {@link ??????????}.
     */
    private Map<Date, Number> getLinearValues(double k, double m, List<Date> sortedDates) {
        Map<Date, Number> returnData = new HashMap<>();
        double xAxisValue = 1;
        for (Date date: sortedDates) {
            double val = k * xAxisValue + m;
            if (val <= 0)
                val = 0.01;
            xAxisValue++;
            returnData.put(date, val);
        }
        return returnData;
    }

    /**
     * A method that calculates the coefficients of {@link }.
     * @return an array of {@link Double}s.
     */
    private double[] getCoefficients(Map<Date, DayData> data, List<Date> sortedDates) {
        double xAxisValue = 0;
        double sumX = 0;
        double sumY = 0;
        double sumXsq = 0;
        double sumXY = 0;
        for (Date date : sortedDates) {
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
