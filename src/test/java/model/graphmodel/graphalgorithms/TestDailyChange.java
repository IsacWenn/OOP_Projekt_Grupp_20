package model.graphmodel.graphalgorithms;

import model.datahandling.DayData;
import model.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestDailyChange {

    private static Map<Date, Number> calculatedData;
    private static Date date1;
    private static Date date2;

    @BeforeEach
    public void setVariables() throws IOException {
        Map<Date, DayData> data = new HashMap<Date, DayData>();
        DayData dayData1 = new DayData(100, 100, 200, 1000, 50);
        DayData dayData2 = new DayData(8, 4, 2, 10, 1);
        date1 = new Date(2020, 1, 1);
        date2 = new Date(2020, 1, 2);
        data.put(date1, dayData1);
        data.put(date2, dayData2);
        GraphAlgorithm dailyChange = GraphAlgorithmCollection.getGraphAlgorithm("Daily Change");
        calculatedData = dailyChange.calculate(data);
    }

    @Test
    public void returnDataShouldBeExpressedInCorrectPercentages() {
        assertEquals(100.0, calculatedData.get(date1));
        assertEquals(-50.0,calculatedData.get(date2));
    }

}
