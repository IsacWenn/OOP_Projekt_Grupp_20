package model.datahandling;


import model.util.Date;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestDataHandler {

    private static Date lastDate;
    private static DayData lastDateDayData;

    private static final String currencyConversionName = "USD_TO_SEK";

    @BeforeEach
    public void resetStaticVariables() throws IOException {
        lastDate = new Date(2022, 9, 13);
        lastDateDayData = new DayData(
                122656600, 159.9,
                153.84, 160.54,
                153.37
        );
    }

    @Test
    public void getCompanyDataShouldReturnCorrectData() throws IOException {
        Map<Date, DayData> dataMap = DataHandler.getCompanyData("AAPL");
        List<Date> dates = Date.sortDates(dataMap.keySet());
        assertTrue(dataMap.containsKey(lastDate));
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
        Map<Date, Double> data = DataHandler.getCurrencyData(currencyConversionName);
        assertEquals(10.65633, data.get(new Date(2022, 8, 24)));
        assertEquals(6.66128, data.get(new Date(2010, 11, 1)));
        assertEquals(3268, data.size());
    }

    @Test
    public void getCurrencyDataWithInvalidPathShouldReturnMapWithCurrentDateAndZero() {
        Map<Date, Double> data = DataHandler.getCurrencyData("FOO_BAR.csv");
        assertEquals(0, data.get(new Date()));
    }

    @Test
    public void getLatestDayDataShouldReturnTheLatestAvailableDayData() throws IOException {
        Map<Date, DayData> data = DataHandler.getLatestDayData("AAPL");
        assertEquals(1, data.size());
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
        assertEquals("USD", DataHandler.getCompanyTradingCurrency("AMZN"));
    }

    @Test
    public void getExpandedCurrencyDataShouldReturnMapWithCorrespondingEntriesForNonExistentDates() throws IOException {
        Map<Date, Double> map = DataHandler.getExpandedCurrencyData(currencyConversionName);
        assertEquals(10.81552, map.get(new Date(2022, 9, 5)));
    }

    @Test
    public void getExpandedCurrencyDataWithInvalidPathShouldReturnMapWithOnlyCurrentDateAndAZero() {
        Map<Date, Double> map = DataHandler.getExpandedCurrencyData("hej");
        assertEquals(0d, map.get(new Date()));
    }

    @Test
    public void getCurrencyDataWithSetShouldReturnCurrencyDataForThatInterval() throws IOException {
        Set<Date> set = new HashSet<>(){{
            add(new Date(2022, 9, 1));
            add(new Date(2022, 9, 15));
            add(new Date(1950, 1, 1));
        }};
        Map<Date, Double> map = DataHandler.getCurrencyData(set, currencyConversionName);
        System.out.println(map);
        assertEquals(10.69856, map.get(new Date(2022, 9, 15)));
        assertEquals(0d, map.get(new Date(1950, 1, 1)));
    }

    @Test
    public void getCurrencyDataWithInvalidPathShouldReturnMapOfCurrentDateWithZero() {
        List<Date> newList = new ArrayList<>(){{ add(new Date()); }};
        Map<Date, Double> map = DataHandler.getCurrencyData(newList, "hej");
        assertEquals(0d, map.get(new Date()));
    }

    @Test
    public void getExpandedCurrencyDataWithListOfDatesShouldReturnValuesForThoseDates() throws IOException {
        List<Date> dateList = new Date(2022, 9, 1).listIntervalTo(new Date());
        Map<Date, Double> map = DataHandler.getExpandedCurrencyData(dateList, currencyConversionName);
        assertEquals(11.19329, map.get(new Date()));
        assertFalse(map.containsKey(new Date(2022, 8, 30)));
    }

    @Test
    public void getExpandedCurrencyDataWithSetShouldReturnValuesForThoseDates() throws IOException {
        Set<Date> dateSet = new HashSet<>(new Date(2022, 9, 1).listIntervalTo(new Date()));
        Map<Date, Double> map = DataHandler.getExpandedCurrencyData(dateSet, currencyConversionName);
        assertEquals(11.19329, map.get(new Date()));
        assertFalse(map.containsKey(new Date(2022, 8, 30)));
    }

    @Test
    public void getCompanyDataWithListAndInvalidMICShouldReturnHashMapWithCurrentDateAndZeroedDayData() {
        Map<Date, DayData> map = DataHandler.getCompanyData(new ArrayList<>(), "d");
        DayData zeroed = new DayData(0, 0, 0, 0, 0);
        assertEquals(zeroed.hashCode(), map.get(new Date()).hashCode());
    }

    @Test
    public void getCompanyNameShouldReturnTheSpecifiedCompanysName() {
        String name = DataHandler.getCompanyName("MSFT");
        assertEquals("Microsoft Corporation", name);
    }

    @Test
    public void getCompanyNameWithInvalidMICShouldReturnEmptyString() {
        assertEquals("", DataHandler.getCompanyName("hej"));
    }
}
