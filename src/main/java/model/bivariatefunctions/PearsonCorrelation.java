package model.bivariatefunctions;

import model.bivariatefunctions.BivariateFunctions;
import model.util.Date;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A class used for calculating the Pearson correlation.
 */

public class PearsonCorrelation implements BivariateFunctions {

    /**
     * A method for calculating the Pearson correlation coefficient between two data sets.
     * @param series1 a {@link Map} containing a {@link Date} and a {@link Number} representing one data set.
     * @param series2 a {@link Map} containing a {@link Date} and a {@link Number} representing one data set.
     * @return a {@link Double} that is the calculated correlation coefficient.
     */
    @Override
    public double calculateKeyFigure(Map<Date, Number> series1, Map<Date, Number> series2) {
        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumXX = 0;
        double sumYY = 0;
        int n = series1.size();

        series1.keySet().retainAll(series2.keySet());

        for (Date date : series1.keySet()) {
            double x = (double) series1.get(date);
            double y = (double) series2.get(date);
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumXX += x * x;
            sumYY += y * y;
        }

        return (n * sumXY - sumX * sumY) /
                Math.sqrt((n * sumXX - Math.pow(sumX, 2)) * (n * sumYY - Math.pow(sumY, 2)) );
    }

}

