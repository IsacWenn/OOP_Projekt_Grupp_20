package model.datahandling;

import org.junit.jupiter.api.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestDayData {

    private static DayData testDayData;

    @BeforeEach
    public void resetDayTestVariables() {
        testDayData = new DayData(100, 10d, 12d, 14d, 8d);
    }

    @Test
    public void constructingAnInstanceUsingNumbersShouldReturnValidObject() {
        assertDoesNotThrow(() -> { new DayData(100, 10d, 12d, 14d, 8d); });
    }

    @Test
    public void constructingAnInstanceUsingHashMapShouldReturnValidObject() {
        assertDoesNotThrow(() -> {
            HashMap<String, Object> map = new HashMap<>(){{
                put("volume", 100);
                put("open", 10d);
                put("closed", 12d);
                put("high", 14d);
                put("low", 8d);
            }};
            new DayData(map);
        });
    }

    @Test
    public void getVolumeShouldReturnVolume() {
        assertEquals(100, testDayData.getVolume());
    }

    @Test
    public void getHighShouldReturnHigh() {
        assertEquals(14, testDayData.getHigh());
    }

    @Test
    public void getLowShouldReturnLow() {
        assertEquals(8, testDayData.getLow());
    }

    @Test
    public void getOpenShouldReturnOpen() {
        assertEquals(10, testDayData.getOpen());
    }

    @Test
    public void getClosedShouldReturnClosed() {
        assertEquals(12, testDayData.getClosed());
    }

    @Test
    public void toStringShouldCreateCorrectStringRepresentationOfDayData() {
        assertEquals(
                "DayData{volume=100, open=10.0, closed=12.0, high=14.0, low=8.0}",
                testDayData.toString()
        );
    }

    @Test
    public void hashCodeForInstancesRepresentingTheSameDateShouldBeEqual() {
        DayData data1 = new DayData(5, 6, 7, 8, 9);
        DayData data2 = new DayData(5, 6, 7, 8, 9);
        assertEquals(data1.hashCode(), data2.hashCode());
    }

    @Test
    public void hashCodeForInstancesNotRepresentingTheSameDateShouldNotBeEqual() {
        DayData data1 = new DayData(1, 2, 3, 4, 5);
        DayData data2 = new DayData(2, 3, 4, 5, 6);
        assertNotEquals(data1.hashCode(), data2.hashCode());
    }
}
