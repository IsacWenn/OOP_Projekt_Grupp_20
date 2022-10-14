package model.graphmodel.keyfigures;

import model.datahandling.DayData;
import model.util.Date;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestAverageVolume {

    @Test
    public void averageVolumeShouldBeCorrect() throws IOException {
        HashMap<Date, DayData> data = new HashMap<>();
        data.put(new Date(2020,1,1), new DayData(100,1,1,1,1));
        data.put(new Date(2020,1,2), new DayData(50,1,1,1,1));
        data.put(new Date(2020,1,3), new DayData(50,1,1,1,1));
        data.put(new Date(2020,1,4), new DayData(225,1,1,1,1));
        data.put(new Date(2020,1,5), new DayData(75,1,1,1,1));
        AverageVolume averageVolume = new AverageVolume();
        assertEquals(100, averageVolume.calculate(data));
    }

}
