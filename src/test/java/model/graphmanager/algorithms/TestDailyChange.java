package model.graphmanager.algorithms;

import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import model.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestDailyChange {

    private static Map<Date, DayData> data;
    private static Map<Date, Number> calculatedData;
    private static DayData dayData1;
    private static DayData dayData2;
    private static Date date1;
    private static Date date2;
    private static DailyChange dailyChange;

    @BeforeEach
    public void setVariables() throws IOException {
        data = new HashMap<Date, DayData>();
        dayData1 = new DayData(100,100,200,1000,50);
        dayData2 = new DayData(8,4,2,10,1);
        date1 = new Date(2020, 1, 1);
        date2 = new Date(2020, 1, 2);
        data.put(date1,dayData1);
        data.put(date2,dayData2);
        dailyChange = new DailyChange();
        calculatedData = dailyChange.calculate(data);
    }

    @Test
    public void returnDataShouldBeExpressedInCorrectPercentages() {
        assertEquals(100.0, calculatedData.get(date1));
        assertEquals(-50.0,calculatedData.get(date2));
    }

}
