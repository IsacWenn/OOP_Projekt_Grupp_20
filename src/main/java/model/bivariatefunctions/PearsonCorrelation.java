package model.bivariatefunctions;

import model.bivariatefunctions.BivariateFunctions;
import model.util.Date;

import java.util.Map;

public class PearsonCorrelation implements BivariateFunctions {

    @Override
    public double calculateKeyFigure(Map<Date, Number> series1, Map<Date, Number> series2) {
        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumXX = 0;
        double sumYY = 0;
        int n = series1.size();

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
