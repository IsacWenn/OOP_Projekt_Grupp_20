package model.datahandling;


import model.util.Date;
import org.junit.jupiter.api.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestDataHandler {

    private static Map<Date, DayData> AAPL_Map;
    private static Date lastDate;
    private static DayData lastDateDayData;
    private static List<Date> dateList;

    @BeforeEach
    public void resetStaticVariables() throws IOException {
        AAPL_Map = DataHandler.getCompanyData("AAPL");
        lastDate = new Date(2022, 9, 13);
        lastDateDayData = new DayData(
                122656600, 159.9,
                153.84, 160.54,
                153.37
        );
        dateList = new Date(2022, 6, 1).listIntervalTo(new Date());
    }

    @Test
    public void getCompanyDataShouldReturnCorrectData() throws IOException {
        Map<Date, DayData> dataMap = DataHandler.getCompanyData("AAPL");
        List<Date> dates = Date.sortDates(dataMap.keySet());
        assertEquals(lastDate, dates.get(dates.size() - 1));
        assertEquals(lastDateDayData, dataMap.get(lastDate));
    }

    @Test
    public void getCompanyDataWithListOfDatesShouldReturnCorrectDataInterval() throws IOException {
        List<Date> dates = new Date(2022, 6, 1).listIntervalTo(new Date(2022, 9, 1));
        Map<Date, DayData> data = DataHandler.getCompanyData(dates, "AAPL");
        assertEquals(74229900, data.get(dates.get(dates.size() - 1)).getVolume());
        assertEquals(148.71, data.get(dates.get(0)).getClosed());
        assertEquals(149.8697, data.get(dates.get(7)).getHigh());
    }


    @Test
    public void retrieveClosestExchangeRateShouldReturnCorrectValue() throws IOException {
        Map<Date, Double> map = new HashMap<>(){{       // Fungerar utan problem
            put(new Date(2022, 9, 27), 5.3d);
        }};
        assertEquals(5.3d, DataHandler.retrieveClosestExchangeRate(new Date(), map));
    }
    
}
