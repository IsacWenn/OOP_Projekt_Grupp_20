package model.graphmodel.graphalgorithms;

import model.datahandling.DayData;
import model.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLinearRegression {
    private static Map<Date, Number> calculatedData;
    private static DayData dayData1;
    private static DayData dayData2;
    private static DayData dayData3;
    private static DayData dayData4;
    private static DayData dayData5;
    private static Date date1;
    private static Date date2;
    private static Date date3;
    private static Date date4;
    private static Date date5;
    private GraphAlgorithm linearRegression;
    Map<Date, DayData> data;

    @BeforeEach
    public void setVariables() throws IOException {
        data = new HashMap<Date, DayData>();

        dayData1 = new DayData(100, 100, 10, 1000, 1);
        dayData2 = new DayData(100, 100, 9, 1000, 1);
        dayData3 = new DayData(100, 100, 12, 1000, 1);

        dayData4 = new DayData(100,100,200,1000,1);
        dayData5 = new DayData(100,100,2,1000,1);

        date1 = new Date(2020, 1, 1);
        date2 = date1.nextDate();
        date3 = date2.nextDate();
        date4 = date3.nextDate();
        date5 = date4.nextDate();

        linearRegression = GraphAlgorithmFactory.create(GraphAlgorithms.LINEARREGRESSION);
    }

    @Test
    public void regressionFunctionShouldBeFlat() {
        data.put(date1, dayData1);
        data.put(date2, dayData2);
        data.put(date3, dayData3);
        data.put(date4, dayData2);
        data.put(date5, dayData1);

        calculatedData = linearRegression.calculate(data);
        assertEquals(10.0, calculatedData.get(date3));
    }

    @Test
    public void pricesShouldNotBeNegative() {
        data.put(date1, dayData5);
        data.put(date2, dayData5);
        data.put(date3, dayData4);

        calculatedData = linearRegression.calculate(data);
        assertEquals(0.01, calculatedData.get(date1));
    }
}
