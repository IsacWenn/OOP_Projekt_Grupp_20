package model.bivariatealgorithms;

import model.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBivariateComputer {

    public static Map<Date, Number> series1;
    public static Map<Date, Number> series2;

    public static Date date1;
    public static Date date2;
    public static Date date3;

    @BeforeEach
    public void createHashMapsAndDates() throws IOException {

        date1 = new Date(2020,1,1);
        date2 = new Date(2020,1,2);
        date3 = new Date(2020,1,3);

        series1 = new HashMap<>();
        series2 = new HashMap<>();

        series1.put(date1, 0d);
        series1.put(date2, 10d);
        series1.put(date3, 20d);

        series2.put(date1, 20d);
        series2.put(date2, 10d);
        series2.put(date3, 0d);
    }

    @Test
    public void pearsonCorrelationShouldBeNegativeOne() {
        Map<String, Double> resultMap = new HashMap<>();
        double pearsonCoefficient = BivariateComputer.calculateKeyFigures("Pearson correlation", series1, series2);
        assertEquals(-1d, pearsonCoefficient);
    }

}
