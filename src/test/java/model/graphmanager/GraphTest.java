package model.graphmanager;

import model.graphmanager.algorithms.Graphables;
import model.util.Date;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphTest {

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
        Graph graph = new Graph("AAPL");
        assertEquals(2515, graph.data.size());
        assertNotNull(graph.data.get(date1));
    }

    @Test
    public void graphModelCreatedWithMicAndDateIntervalShouldContainDataForThatInterval() {
        Graph graph = new Graph("AAPL", date1, date2);
        assertEquals(21, graph.data.size());
    }
    @Test
    public void graphModelCreatedWithAListOfDatesShouldContainDataForThoseDates(){
        Graph graph = new Graph("AAPL", dateList);
        assertEquals(3, graph.data.size());
    }
    @Test
    public void updateMethodShouldUpdateTheValuesOfTheGraphModel(){
        Graph graph = new Graph("AAPL", date1, date2);
        graph.update();
        assertEquals(135.87, graph.getValues().get(date1));
    }

    @Test
    public void updateAlgorithmShouldUpdateTheCurrentAlgorithm(){
        Graph graph = new Graph("AAPL", date2, date3);
        graph.updateAlgorithm(Graphables.DAILYCHANGE);
        graph.update();
        assertEquals(1.27, (double)graph.getValues().get(date2), 0.01);
    }

    @Test
    public void changeCurrencyShouldUpdateWhatCurrencyTheGraphValuesIsShownIn(){
        Graph graph = new Graph("AAPL", date2, date3);
        graph.changeCurrency("SEK_USD.csv");
        graph.update();
        //assertEquals(1569.95, (double)graph.getValues().get(date2), 0.1); TODO
    }

    @Test
    public void getValuesShouldReturnTheCurrentlyCalculatedValuesInGraphModel(){
        Graph graph = new Graph("AAPL", date2, date3);
        //TODO
    }
}
