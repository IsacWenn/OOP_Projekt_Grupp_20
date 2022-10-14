package model.bivariatealgorithms;

import model.util.Date;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * AN interface for functions that calculates a key figure based on two {@link Map<>} containing series of {@link Number}
 * for each {@link Date}.
 */

interface BivariateAlgorithms {
    double calculateKeyFigure(Map<Date, Number> series1, Map<Date, Number> series2, Set<Date> commonDates);
}
