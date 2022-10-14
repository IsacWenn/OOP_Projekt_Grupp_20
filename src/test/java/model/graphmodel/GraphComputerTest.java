package model.graphmodel;

import model.datahandling.DataHandler;
import model.datahandling.DayData;
import model.util.Date;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphComputerTest {

    private static Date date1;
    private static Date date2;
    private static Date date3;

    Map<Date, DayData> data = new HashMap<>();

    Map<Date, Double> fromCurrency = new HashMap<>();

    Map<Date,Double> toCurrency = new HashMap<>();

    Map<Date, DayData> adjustedMap;

    static GraphComputer graphComputer;
    @BeforeEach
    public void createTestVariables() throws IOException {
        date1 = new Date(2022, 6, 21);
        date2 = new Date(2022, 7, 20);
        date3 = new Date(2022, 7, 21);

        DayData dayData = new DayData(100, 1.5,2.5,3.5,4.5);
        double currency1 = 10;
        double currency = 1;

        data.put(date1, dayData);
        data.put(date2, dayData);
        data.put(date3, dayData);

        fromCurrency.put(date1, currency); fromCurrency.put(date2, currency); fromCurrency.put(date3, currency);
        toCurrency.put(date1, currency1); toCurrency.put(date2, currency1); toCurrency.put(date3, currency1);

        graphComputer = new GraphComputer();

        adjustedMap = new HashMap<>();
    }

    @Test
    public void calculateCurrencyShouldConvertTheCurrencyForTheRelatedValues(){
        adjustedMap = graphComputer.calculateCurrency(fromCurrency, toCurrency, data);
        assertEquals(100, adjustedMap.get(date1).getVolume());
        assertEquals(15, adjustedMap.get(date1).getOpen());
        assertEquals(25, adjustedMap.get(date1).getClosed());
        assertEquals(35, adjustedMap.get(date1).getHigh());
        assertEquals(45, adjustedMap.get(date1).getLow());
    }

    @Test
    public void updateValuesShouldCalculateTheDataWithTheCurrentAlgorithm(){

    }

    @Test
    public void calculateKeyFigureShould(){

    }


}
