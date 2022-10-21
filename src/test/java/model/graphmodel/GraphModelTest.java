package model.graphmodel;

import model.graphmodel.graphalgorithms.GraphAlgorithmCollection;
import model.util.CurrencyCollection;
import model.util.Date;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    public void graphNameShouldBeCorrect(){
        GraphModel graphModel = new GraphModel("AAPL", "Test", date2, date3);
        assertNotEquals("Test", graphModel.getName());
    }

    @Test
    public void theTimeIntervalShouldBeAbleToUpdateBeforeCurrencyIsSet(){
        GraphModel graphModel = new GraphModel("AAPL", "Test", date2, date3);
        graphModel.updateCurrency(CurrencyCollection.getDefaultCurrencyName());
        graphModel.updateTimeInterval(date1, date2);
        assertEquals(21, graphModel.getValues().size());
    }

    @Test
    public void graphModelCreatedWithOnlyMicShouldContainAllAvailableCompanyData() {
        GraphModel graphModel = new GraphModel("AAPL", "Test");

        assertEquals(2515,graphModel.data.size(), 10);
        assertNotNull(graphModel.data.get(date1));
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
        List<String> keyFigAlgNames = GraphModel.getKeyFigureNames();
        assertEquals(5, keyFigAlgNames.size());
    }

    @Test
    public void KeyFigureAlgorithmNamesShouldBeInOrder(){
        List<String> algNames = GraphModel.getKeyFigureNames();
        List<String> orderedNames = new ArrayList<>(List.copyOf(algNames));
        Collections.sort(orderedNames);
        for (int i = 1; i < algNames.size(); i++) {
            assertEquals(orderedNames.get(i), algNames.get(i));
        }
    }

    @Test
    public void GraphAlgorithmNamesShouldBeInOrderExceptForTheFirst(){
        List<String> graphAlgNames = GraphModel.getGraphAlgorithmNames();
        graphAlgNames.remove(0);
        List<String> orderedNames = new ArrayList<>(List.copyOf(graphAlgNames));
        Collections.sort(orderedNames);
        for (int i = 1; i < graphAlgNames.size(); i++) {
            assertEquals(orderedNames.get(i), graphAlgNames.get(i));
        }
    }

    @Test
    public void defaultGraphAlgorithmShouldBeFirst(){
        List<String> graphAlgNames = GraphModel.getGraphAlgorithmNames();
        String defaultAlgo = GraphAlgorithmCollection.getDefaultGraphAlgorithmName();
        assertEquals(defaultAlgo, graphAlgNames.get(0));
    }

    @Test
    public void numberOfGraphAlgorithmsShouldBeCorrect(){
        List<String> keyFigAlgNames = GraphModel.getGraphAlgorithmNames();
        assertEquals(4, keyFigAlgNames.size());
    }

    @Test
    public void CurrencyNamesShouldBeInOrderExceptForTheFirst(){
        List<String> currencyNames = GraphModel.getCurrencyNames();
        currencyNames.remove(0);
        List<String> orderedNames = new ArrayList<>(List.copyOf(currencyNames));
        Collections.sort(orderedNames);
        for (int i = 1; i < currencyNames.size(); i++) {
            assertEquals(orderedNames.get(i), currencyNames.get(i));
        }
    }

    @Test
    public void defaultCurrencyShouldBeFirst(){
        List<String> currencyNames = GraphModel.getCurrencyNames();
        String defaultAlgo = CurrencyCollection.getDefaultCurrencyName();
        assertEquals(defaultAlgo, currencyNames.get(0));
    }

    @Test
    public void numberOfCurrenciesShouldBeCorrect(){
        List<String> currencyNames = GraphModel.getCurrencyNames();
        assertEquals(9, currencyNames.size());
    }

}
