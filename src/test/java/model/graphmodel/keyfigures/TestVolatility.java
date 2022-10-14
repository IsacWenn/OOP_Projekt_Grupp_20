package model.graphmodel.keyfigures;

import model.datahandling.DayData;
import model.util.Date;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestVolatility {
    @Test
    public void volumeShouldBeZero() throws IOException {
        HashMap<Date, DayData> data = new HashMap<>();
        data.put(new Date(2020,1,1), new DayData(1,1,100,1,1));
        data.put(new Date(2020,1,2), new DayData(1,1,100,1,1));
        data.put(new Date(2020,1,3), new DayData(1,1,100,1,1));
        data.put(new Date(2020,1,4), new DayData(1,1,100,1,1));
        data.put(new Date(2020,1,5), new DayData(1,1,100,1,1));
        Volatility volatility = new Volatility();
        assertEquals(0, volatility.calculate(data));
    }

    @Test
    public void volumeShouldNotBeZero() throws IOException {
        HashMap<Date, DayData> data = new HashMap<>();
        data.put(new Date(2020,1,1), new DayData(1,1,100,1,1));
        data.put(new Date(2020,1,2), new DayData(1,1,100,1,1));
        data.put(new Date(2020,1,3), new DayData(1,1,1,1,1));
        data.put(new Date(2020,1,4), new DayData(1,1,100,1,1));
        data.put(new Date(2020,1,5), new DayData(1,1,100,1,1));
        Volatility volatility = new Volatility();
        assertNotEquals(0, volatility.calculate(data));
    }
}
