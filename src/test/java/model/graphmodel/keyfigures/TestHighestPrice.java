package model.graphmodel.keyfigures;

import model.datahandling.DayData;
import model.util.Date;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestHighestPrice {

    @Test
    public void highestPriceShouldBeCorrect() throws IOException {
    HashMap<Date, DayData> data = new HashMap<>();
    data.put(new Date(2020,1,1), new DayData(1,1,18,1,1));
    data.put(new Date(2020,1,2), new DayData(1,1,73,1,1));
    data.put(new Date(2020,1,3), new DayData(1,1,104,1,1));
    data.put(new Date(2020,1,4), new DayData(1,1,999,1,1));
    data.put(new Date(2020,1,5), new DayData(1,1,4,1,1));
    data.put(new Date(2020,1,6), new DayData(1,1,1000,1,1));
    data.put(new Date(2020,1,7), new DayData(1,1,2,1,1));
    data.put(new Date(2020,1,8), new DayData(1,1,120,1,1));
    data.put(new Date(2020,1,9), new DayData(1,1,6,1,1));
    data.put(new Date(2020,1,10), new DayData(1,1,43,1,1));
    HighestPrice highestPrice = new HighestPrice();
    assertEquals(1000, highestPrice.calculate(data));
    }
}
