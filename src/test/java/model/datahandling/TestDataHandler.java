package model.datahandling;


import model.util.CurrencyEnum;
import model.util.Date;
import org.junit.jupiter.api.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
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
    public void getCompanyDataWithInvalidMicShouldReturnZeroedDayDataWithCurrentDate() {
        Map<Date, DayData> data = DataHandler.getCompanyData("HEJ");
        assertEquals(0, data.get(new Date()).getHigh());
        assertEquals(0, data.get(new Date()).getLow());
        assertEquals(0, data.get(new Date()).getOpen());
        assertEquals(0, data.get(new Date()).getLow());
        assertEquals(0, data.get(new Date()).getVolume());
    }

    @Test
    public void getCompanyDataWithListContainingDatesWithoutDataShouldReturnEmptyMap() {
        List<Date> dateList = new ArrayList<>(){{ add(new Date()); }};
        Map<Date, DayData> data = DataHandler.getCompanyData(dateList, "AAPL");
        assertTrue(data.isEmpty());
    }

    @Test
    public void getCompanyDataWithIntervalOfDatesShouldReturnCorrectDataInterval() throws IOException {
        Date date1 = new Date(2022, 6, 1);
        Date date2 = new Date(2022, 9, 1);
        Map<Date, DayData> data = DataHandler.getCompanyData(date1, date2, "AAPL");
        assertEquals(74229900, data.get(date2).getVolume());
        assertEquals(148.71, data.get(date1).getClosed());
    }

    @Test
    public void getCurrencyDataShouldReturnCorrectData() throws IOException {
        Map<Date, Double> data = DataHandler.getCurrencyData("SEK_USD.csv");
        assertEquals(0.0938, data.get(new Date(2022, 8, 24)));
        assertEquals(0.1245, data.get(new Date(1999, 1, 4)));
        assertEquals(6054, data.size());
    }

    @Test
    public void getCurrencyDataWithInvalidPathShouldReturnMapWithCurrentDateAndZero() {
        Map<Date, Double> data = DataHandler.getCurrencyData("FOO_BAR.csv");
        assertEquals(0, data.get(new Date()));
    }

    @Test
    public void getLatestDayDataShouldReturnTheLatestAvailableDayData() throws IOException {
        Map<Date, DayData> data = DataHandler.getLatestDayData("AAPL");
        DayData dayData = data.get(new Date(2022, 9, 13));
        assertEquals(1, data.size());
        assertEquals(122656600, dayData.getVolume());
        assertEquals(160.54, dayData.getHigh());
    }

    @Test
    public void getMICsShouldReturnListOfMICs() {
        List<String> mics = DataHandler.getMICs();
        assertTrue(mics.contains("AAPL"));
        assertTrue(mics.contains("TSLA"));
        assertTrue(mics.contains("AMZN"));
        assertTrue(mics.contains("MSFT"));
    }

    @Test
    public void getNamesShouldReturnListOfCompanyNames() {
        List<String> names = DataHandler.getCompanyNames();
        assertTrue(names.contains("Qualcomm Incorporated"));
    }


    @Test
    public void retrieveClosestExchangeRateShouldReturnCorrectValue() throws IOException {
        Map<Date, Double> map = new HashMap<>(){{       // Fungerar utan problem
            put(new Date(2022, 9, 27), 5.3d);
        }};
        assertEquals(5.3d, DataHandler.retrieveClosestExchangeRate(new Date(), map));
    }

    @Test
    public void getCompanyTradingCurrencyShouldReturnCorrectCurrencyEnum() {
        assertEquals(CurrencyEnum.USD, DataHandler.getCompanyTradingCurrency("AMZN"));
    }
    
}
