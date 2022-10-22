package model.graphmodel;


import model.datahandling.DayData;
import model.util.Date;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GraphModelDataTest {

    private static Date date1;
    private static Date date2;
    private static Date date3;

    private static List<Date> dateList;

    Map<Date, DayData> data;

    static GraphData graphData;

    @BeforeEach
    public void create_Variables() throws IOException {
        graphData = new GraphData();

        date1 = new Date(2022, 6, 21);
        date2 = new Date(2022, 7, 20);
        date3 = new Date(2022, 7, 21);

        dateList = new ArrayList<>(){{
            add(date1);
            add(date2);
            add(date3);
        }};

        data = new HashMap<>();


    }

    @Test
    public void getCompanyDataWithOnlyMicShouldReturnAllAvailableDataForThatCompany(){
        data = graphData.getCompanyData("AAPL");
        assertEquals(2515, data.size());
        assertNotNull(data.get(date1));
    }

    @Test
    public void getCompanyDataWithMicAndIntervalShouldReturnDataForThatInterval(){
        data = graphData.getCompanyData("AAPL", date1, date2);
        assertEquals(21, data.size());
    }

    @Test
    public void getCompanyDataWithMicAndListOfDatesShouldReturnDataForThoseDates(){
        data = graphData.getCompanyData("AAPL", dateList);
        assertEquals(3, data.size());
    }

    @Test
    public void getCurrencyDataCalledWithACurrencyShouldReturnTheExchangeRateForThatCurrency(){
        data = graphData.getCompanyData("AAPL", date1, date2);
        Map<Date, Double> currencyData = graphData.getCurrencyData("SEK", data.keySet());
        assertEquals(21, currencyData.size());
        assertEquals(10.1, currencyData.get(date1), 0.02);
    }

    @Test
    public void getCompanyCurrencyShouldReturnTheDefaultCurrencyForThatCompany(){
        assertEquals("USD", graphData.getCompanyCurrency("AAPL"));
    }

    @Test
    public void getNativeCurrencyShouldReturnTheExchangeRateForTheDefaultCurrencyForThatCompany(){
        data = graphData.getCompanyData("AAPL", date1, date2);
        Map<Date, Double> currencyData = graphData.getNativeCurrencyData("AAPL", data.keySet());
        assertEquals(21, currencyData.size());

    }

}
