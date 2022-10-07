package model.bivariatefunctions;

import model.util.Date;

import java.util.Map;

public interface BivariateFunctions {
    double calculateKeyFigure(Map<Date, Number> series1, Map<Date, Number> series2);
}
