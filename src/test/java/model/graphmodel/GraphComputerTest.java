package model.graphmodel;

import model.datahandling.DayData;
import model.graphmodel.graphalgorithms.GraphAlgorithmCollection;
import model.graphmodel.keyfigures.KeyFigureCollection;
import model.graphmodel.keyfigures.Volatility;
import model.util.Date;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphComputerTest {

    private static Date date1;
    private static Date date2;
    private static Date date3;

    Map<Date, DayData> data = new HashMap<>();

    Map<Date, Double> fromCurrency = new HashMap<>();

    Map<Date,Double> toCurrency = new HashMap<>();

    static GraphComputer graphComputer;

    @BeforeEach
    public void createTestVariables() throws IOException {
        date1 = new Date(2022, 6, 21);
        date2 = new Date(2022, 7, 20);
        date3 = new Date(2022, 7, 21);

        DayData dayData1 = new DayData(100, 1.5,2.5,3.5,4.5);
        DayData dayData2 = new DayData(100, 2.5,3.5,4.5,5.5);
        DayData dayData3 = new DayData(100, 3.5,4.5,5.5,6.5);
        double currency1 = 10;
        double currency = 1;

        data.put(date1, dayData1);
        data.put(date2, dayData2);
        data.put(date3, dayData3);

        fromCurrency.put(date1, currency); fromCurrency.put(date2, currency); fromCurrency.put(date3, currency);
        toCurrency.put(date1, currency1); toCurrency.put(date2, currency1); toCurrency.put(date3, currency1);

        graphComputer = new GraphComputer();
        graphComputer.setAlgorithm(GraphAlgorithmCollection.getGraphAlgorithm("Daily closing price"));
    }

    @Test
    public void calculateCurrencyShouldConvertTheCurrencyForTheRelatedValues(){
        Map<Date, DayData> adjustedMap;
        adjustedMap = graphComputer.calculateCurrency(fromCurrency, toCurrency, data);
        assertEquals(100, adjustedMap.get(date1).getVolume());
        assertEquals(15, adjustedMap.get(date1).getOpen());
        assertEquals(25, adjustedMap.get(date1).getClosed());
        assertEquals(35, adjustedMap.get(date1).getHigh());
        assertEquals(45, adjustedMap.get(date1).getLow());
    }

    @Test
    public void updateValuesShouldCalculateTheDataWithTheCurrentAlgorithm(){
        Map<Date, Number> calcData = graphComputer.calculateValues(data);
        assertEquals(2.5, calcData.get(date1).doubleValue());
    }

    @Test
    public void calculateKeyFigureShouldReturnTheValueForThatKeyFigureWithTheDataReceived(){
        double result = graphComputer.calculateKeyFigure(KeyFigureCollection.getKeyFigure("Volatility"), data);
        assertEquals(0.8, result, 0.05);
    }


}
