package model.graphmodel;

import model.graphmodel.graphalgorithms.GraphAlgorithms;
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
    public void graphModelCreatedWithOnlyMicShouldContainAllAvailableCompanyData() {
        GraphModel graphModel = new GraphModel("AAPL");
        assertEquals(2515, graphModel.data.size());
        assertNotNull(graphModel.data.get(date1));
    }

    @Test
    public void graphModelCreatedWithMicAndDateIntervalShouldContainDataForThatInterval() {
        GraphModel graphModel = new GraphModel("AAPL", date1, date2);
        assertEquals(21, graphModel.data.size());
    }
    @Test
    public void graphModelCreatedWithAListOfDatesShouldContainDataForThoseDates(){
        GraphModel graphModel = new GraphModel("AAPL", dateList);
        assertEquals(3, graphModel.data.size());
    }
    @Test
    public void updateMethodShouldUpdateTheValuesOfTheGraphModel(){
        GraphModel graphModel = new GraphModel("AAPL", date1, date2);
        assertEquals(135.87, graphModel.getValues().get(date1));
    }

    @Test
    public void updateAlgorithmShouldUpdateTheCurrentAlgorithm(){
        GraphModel graphModel = new GraphModel("AAPL", date2, date3);
        graphModel.updateAlgorithm(GraphAlgorithms.DAILYCHANGE);
        assertEquals(1.27, (double) graphModel.getValues().get(date2), 0.01);
    }

    @Test
    public void changeCurrencyShouldUpdateWhatCurrencyTheGraphValuesIsShownIn(){
        GraphModel graphModel = new GraphModel("AAPL", date2, date3);
        graphModel.changeCurrency(CurrencyEnum.SEK);
        //assertEquals(1569.95, (double)graphModel.getValues().get(date2), 0.1);
    }

    @Test
    public void getValuesShouldReturnTheCurrentlyCalculatedValuesInGraphModel(){
        GraphModel graphModel = new GraphModel("AAPL", date2, date3);
        //TODO
    }
}
