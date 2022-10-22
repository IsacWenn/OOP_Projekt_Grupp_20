package model.bivariatealgorithms;

import model.util.Date;

import java.util.Map;
import java.util.Set;

/**
 * A class used for calculating the Pearson correlation.
 */

class PearsonCorrelation implements BivariateAlgorithms {

    /**
     * A method for calculating the Pearson correlation coefficient between two data sets.
     *
     * @param series1 a {@link Map} containing a {@link Date} and a {@link Number} representing one data set.
     * @param series2 a {@link Map} containing a {@link Date} and a {@link Number} representing one data set.
     * @return a {@link Double} that is the calculated correlation coefficient.
     */
    @Override
    public double calculateKeyFigure(Map<Date, Number> series1, Map<Date, Number> series2, Set<Date> commonDates) {
        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumXX = 0;
        double sumYY = 0;
        int n = series1.size();

        for (Date date : commonDates) {
            double x = series1.get(date).doubleValue();
            double y = series2.get(date).doubleValue();
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

