package model.graphmanager;


import model.datahandling.DayData;
import model.util.Date;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GraphDataTest {

    private static Date date1;
    private static Date date2;
    private static Date date3;

    private static List<Date> dateList;

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

    }

    @Test
    public void getCompanyDataWithOnlyMicShouldReturnAllAvailableDataForThatCompany(){
        Map<Date, DayData> data = graphData.getCompanyData("AAPL");
        assertEquals(2515, data.size());
        assertNotNull(data.get(date1));
    }

    @Test
    public void getCompanyDataWithMicAndIntervalShouldReturnDataForThatInterval(){
        Map<Date, DayData> data = graphData.getCompanyData("AAPL", date1, date2);
        assertEquals(21, data.size());
    }

    @Test
    public void getCompanyDataWithMicAndListOfDatesShouldReturnDataForThoseDates(){
        Map<Date, DayData> data = graphData.getCompanyData("AAPL", dateList);
        assertEquals(3, data.size());
    }

    @Test
    public void getCurrencyDataCalledWithACurrencyShouldReturnTheExchangeForThatCurrency(){

    }
}
