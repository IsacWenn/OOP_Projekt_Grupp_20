package model.bivariatealgorithms;

import model.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSpearmanCorrelation {

    public static Map<Date, Number> series1;
    public static Map<Date, Number> series2;

    public static Date date1;
    public static Date date2;
    public static Date date3;
    public static Date date4;
    public static Date date5;

    @BeforeEach
    public void createHashMapsAndDates() throws IOException {

        date1 = new Date(2020,1,1);
        date2 = new Date(2020,1,2);
        date3 = new Date(2020,1,3);
        date4 = new Date(2020,1,3);
        date5 = new Date(2020,1,3);

        series1 = new HashMap<>();
        series2 = new HashMap<>();

        series1.put(date1, 2);
        series1.put(date2, 4);
        series1.put(date3, 16);
        series1.put(date4, 256);
        series1.put(date5, 65536);

        series2.put(date1, 5);
        series2.put(date2, 4);
        series2.put(date3, 3);
        series2.put(date4, 2);
        series2.put(date5, 1);
    }

    @Test
    public void rankCorrelationShouldBeNegative() {
        SpearmanCorrelation spearmanCorrelation = new SpearmanCorrelation();
        double correlation = spearmanCorrelation.calculateKeyFigure(series1, series2, series1.keySet());
        assertEquals(-1d, correlation);
    }
}
