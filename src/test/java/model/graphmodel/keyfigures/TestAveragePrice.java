package model.graphmodel.keyfigures;

import model.datahandling.DayData;
import model.util.Date;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestAveragePrice {

    @Test
    public void averagePriceShouldBeCorrect() throws IOException {
        HashMap<Date, DayData> data = new HashMap<>();
        data.put(new Date(2020,1,1), new DayData(1,1,70,1,1));
        data.put(new Date(2020,1,2), new DayData(2,1,100,1,1));
        data.put(new Date(2020,1,3), new DayData(3,1,10,1,1));
        data.put(new Date(2020,1,4), new DayData(4,1,25,1,1));
        AveragePrice averagePrice = new AveragePrice();
        assertEquals(40, averagePrice.calculate(data));
    }
}
