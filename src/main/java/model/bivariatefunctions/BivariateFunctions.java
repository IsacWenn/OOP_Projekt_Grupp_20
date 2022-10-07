package model.bivariatefunctions;

import model.util.Date;

import java.util.Map;

/**
 * AN interface for functions that calculates a key figure based on two {@link Map<>} containing series of {@link Number}
 * for each {@link Date}.
 */

public interface BivariateFunctions {
    double calculateKeyFigure(Map<Date, Number> series1, Map<Date, Number> series2);
}
