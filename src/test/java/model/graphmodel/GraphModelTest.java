package model.graphmodel;

import model.graphmodel.graphalgorithms.GraphAlgorithmCollection;
import model.util.CurrencyCollection;
import model.util.Date;
import model.util.GraphRepresentation;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    public void graphNameShouldBeCorrect(){
        GraphModel graphModel = new GraphModel("AAPL", "Test", date2, date3);
        assertEquals("Test", graphModel.getName());
    }

    @Test
    public void theTimeIntervalShouldBeAbleToUpdateBeforeCurrencyIsSet(){
        GraphModel graphModel = new GraphModel("AAPL", "Test", date2, date3);
        graphModel.updateCurrency(CurrencyCollection.getDefaultCurrencyName());
        graphModel.updateTimeInterval(date1, date2);
        assertEquals(21, graphModel.getValues().size());
    }

    @Test
    public void graphModelCreatedWithGraphRepresentationsShouldBeCreatedCorrectly() throws IOException {
        GraphRepresentation graphRep = new GraphRepresentation(
                new Date(2022, 10, 12),
                new Date(2022, 10, 14),
                "Daily Change",
                "AMZN",
                "GBP"
        );
        GraphModel model = new GraphModel(graphRep);
        assertEquals(3, model.data.size());
        assertEquals("Amazon.com, Inc.", model.getName());
    }

    @Test
    public void graphModelShouldBeAbleToBeInitializedWithCurrencyIntervalAlgorithm(){
        String someGraphAlgorithm = GraphAlgorithmCollection.getDefaultGraphAlgorithmName();
        String someCurrency = CurrencyCollection.getDefaultCurrencyName();
        GraphModel graphModel = new GraphModel("AAPL", "test", date1, date2, someGraphAlgorithm, someCurrency);
        assertEquals(21, graphModel.data.size());
    }

    @Test
    public void graphModelCreatedWithMicAndDateIntervalShouldContainDataForThatInterval() {
        GraphModel graphModel = new GraphModel("AAPL", "Test", date1, date2);
        assertEquals(21, graphModel.data.size());
    }

    @Test
    public void updateMethodShouldUpdateTheValuesOfTheGraphModel(){
        GraphModel graphModel = new GraphModel("AAPL", "Test", date1, date2);
        assertEquals(135.87, graphModel.getValues().get(date1));
    }

    @Test
    public void updateAlgorithmShouldUpdateTheCurrentAlgorithm(){
        GraphModel graphModel = new GraphModel("AAPL", "Test", date2, date3);
        graphModel.updateAlgorithm("Daily Change");
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
        double result = graphModel.getKeyFigureValue("Standard Deviation");
        assertEquals(5.10, result, 0.5);
    }
    @Test
    public void getKeyFigureValueShouldReturnTheValueOfThatKeyFigureEvenIfCurrencyBeenChanged(){
        GraphModel graphModel = new GraphModel("AAPL", "Test", date1, date2);
        graphModel.updateCurrency("SEK");
        double result = graphModel.getKeyFigureValue("Standard Deviation");
        assertEquals(70, result, 1);
    }

    @Test
    public void numberOfKeyFigureAlgorithmsShouldBeCorrect(){
        List<String> keyFigAlgNames = GraphModel.getOrderedKeyFigureNames();
        assertEquals(5, keyFigAlgNames.size());
    }

    @Test
    public void KeyFigureAlgorithmNamesShouldBeInOrder(){
        List<String> algNames = GraphModel.getOrderedKeyFigureNames();
        List<String> orderedNames = new ArrayList<>(List.copyOf(algNames));
        Collections.sort(orderedNames);
        for (int i = 1; i < algNames.size(); i++) {
            assertEquals(orderedNames.get(i), algNames.get(i));
        }
    }

    @Test
    public void GraphAlgorithmNamesShouldBeInOrderExceptForTheFirst(){
        List<String> graphAlgNames = GraphModel.getOrderedGraphAlgorithmNames();
        graphAlgNames.remove(0);
        List<String> orderedNames = new ArrayList<>(List.copyOf(graphAlgNames));
        Collections.sort(orderedNames);
        for (int i = 1; i < graphAlgNames.size(); i++) {
            assertEquals(orderedNames.get(i), graphAlgNames.get(i));
        }
    }

    @Test
    public void defaultGraphAlgorithmShouldBeFirst(){
        List<String> graphAlgNames = GraphModel.getOrderedGraphAlgorithmNames();
        String defaultAlgo = GraphAlgorithmCollection.getDefaultGraphAlgorithmName();
        assertEquals(defaultAlgo, graphAlgNames.get(0));
    }

    @Test
    public void numberOfGraphAlgorithmsShouldBeCorrect(){
        List<String> keyFigAlgNames = GraphModel.getOrderedGraphAlgorithmNames();
        assertEquals(4, keyFigAlgNames.size());
    }

    @Test
    public void CurrencyNamesShouldBeInOrderExceptForTheFirst(){
        List<String> currencyNames = GraphModel.getOrderedCurrencyNames();
        currencyNames.remove(0);
        List<String> orderedNames = new ArrayList<>(List.copyOf(currencyNames));
        Collections.sort(orderedNames);
        for (int i = 1; i < currencyNames.size(); i++) {
            assertEquals(orderedNames.get(i), currencyNames.get(i));
        }
    }

    @Test
    public void defaultCurrencyShouldBeFirst(){
        List<String> currencyNames = GraphModel.getOrderedCurrencyNames();
        String defaultAlgo = CurrencyCollection.getDefaultCurrencyName();
        assertEquals(defaultAlgo, currencyNames.get(0));
    }

    @Test
    public void numberOfCurrenciesShouldBeCorrect(){
        List<String> currencyNames = GraphModel.getOrderedCurrencyNames();
        assertEquals(9, currencyNames.size());
    }

    @Test
    public void shouldBeAbleToOnlyRetrieveAWantedNumberOfDataPoint(){
        GraphModel graphModel = new GraphModel("AAPL", "Test name", date1, date2);
        Map<Date, Number> data = graphModel.getSortedAndReducedData(3);
        for (Date date : data.keySet()) {
            assertTrue(date.equals(date1) || date.equals(date2) || (date.getYear() == 2022) &&
                    (date.getMonth() == 7) && (date.getDay() == 6));
        }
        assertEquals(3, data.size());
    }


}
