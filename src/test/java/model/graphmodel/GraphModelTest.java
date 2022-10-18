package model.graphmodel;

import model.graphmodel.graphalgorithms.GraphAlgorithms;
import model.graphmodel.keyfigures.KeyFigureCollection;
import model.util.CurrencyEnum;
import model.util.Date;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphModelTest {

    private static Date date1;
    private static Date date2;
    private static Date date3;

    private static List<Date> dateList;

    @BeforeEach
    public void createTestVariables() throws IOException {
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
    public void getValuesShouldReturnTheCurrentlyCalculatedValuesInGraphModel(){
        GraphModel graphModel = new GraphModel("AAPL", "Test", date2, date3);
        assertEquals(2, graphModel.getValues().size());

    }
    @Test
    public void graphModelCreatedWithOnlyMicShouldContainAllAvailableCompanyData() {
        GraphModel graphModel = new GraphModel("AAPL", "Test");
        assertEquals(2515, graphModel.data.size());
        assertNotNull(graphModel.data.get(date1));
    }

    @Test
    public void graphModelCreatedWithMicAndDateIntervalShouldContainDataForThatInterval() {
        GraphModel graphModel = new GraphModel("AAPL", "Test", date1, date2);
        assertEquals(21, graphModel.data.size());
    }
    @Test
    public void graphModelCreatedWithAListOfDatesShouldContainDataForThoseDates(){
        GraphModel graphModel = new GraphModel("AAPL", "Test", dateList);
        assertEquals(3, graphModel.data.size());
    }
    @Test
    public void updateMethodShouldUpdateTheValuesOfTheGraphModel(){
        GraphModel graphModel = new GraphModel("AAPL", "Test", date1, date2);
        assertEquals(135.87, graphModel.getValues().get(date1));
    }

    @Test
    public void updateAlgorithmShouldUpdateTheCurrentAlgorithm(){
        GraphModel graphModel = new GraphModel("AAPL", "Test", date2, date3);
        graphModel.updateAlgorithm("Daily change");
        assertEquals(1.27, (double) graphModel.getValues().get(date2), 0.01);
    }

    @Test
    public void changeCurrencyShouldUpdateWhatCurrencyTheGraphValuesIsShownIn(){
        GraphModel graphModel = new GraphModel("AAPL", "Test", date2, date3);
        graphModel.updateCurrency("SEK");
        assertEquals(1569.65, (double)graphModel.getValues().get(date2), 0.1);
    }

    @Test
    public void updateTimeIntervalShouldUpdateTheIntervalOfTheDataInGraphModel(){
        GraphModel graphModel = new GraphModel("AAPL", "Test", date2, date3);
        graphModel.updateTimeInterval(date1, date3);
        assertEquals(22, graphModel.data.size());
    }

    @Test
    public void getKeyFigureValueShouldReturnTheValueOfThatKeyFigureForTheCurrentData(){
        GraphModel graphModel = new GraphModel("AAPL", "Test", date1, date2);
        String result = graphModel.getKeyFigureValue("Volatility");
        assertEquals("5.10", result);
    }
    @Test
    public void getKeyFigureValueShouldReturnTheValueOfThatKeyFigureEvenIfCurrencyBeenChanged(){
        GraphModel graphModel = new GraphModel("AAPL", "Test", date1, date2);
        graphModel.updateCurrency("SEK");
        String result = graphModel.getKeyFigureValue("Volatility");
        //assertEquals(5.1, result, 0.05);
    }
}
