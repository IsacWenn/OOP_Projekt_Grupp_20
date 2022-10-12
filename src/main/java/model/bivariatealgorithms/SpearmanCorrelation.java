package model.bivariatealgorithms;

import model.util.Date;

import java.util.Map;
import java.util.Set;

public class SpearmanCorrelation implements BivariateAlgorithms {

    /**
     * A method for calculating the Pearson correlation coefficient between two data sets.
     * @param series1 a {@link Map} containing a {@link Date} and a {@link Number} representing one data set.
     * @param series2 a {@link Map} containing a {@link Date} and a {@link Number} representing one data set.
     * @return a {@link Double} that is the calculated correlation coefficient.
     */
    @Override
    public double calculateKeyFigure(Map<Date, Number> series1, Map<Date, Number> series2, Set<Date> commonDates) {

        return 2;

    }
}
