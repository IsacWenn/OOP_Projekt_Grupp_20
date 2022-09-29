package model.graphmanager;

import model.Date;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

public class GraphTest {

    @Test
    public void graphModelCreatedWithOnlyMicShouldContainAllAvailableCompanyData() {
        Graph graph = new Graph("AAPL");
        assertEquals(2515, graph.data.size());
    }

    @Test
    public void graphModelCreatedWithMicAndDateIntervalShouldContainDataForThatInterval() {
        try {
            Date date1 = new Date(2022, 6, 20);
            Date date2 = new Date(2022,7, 21);
            Graph graph = new Graph("AAPL", date1, date2);
            assertEquals(22, graph.data.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void graphModelCreatedWithAListOfDatesShouldContainDataForThoseDates(){
        try{
            Date date = new Date(2022, 6, 20);
            Date dateTo = new Date(2022, 7, 20);
            List<Date> dates = date.listIntervalTo(dateTo);
            Graph graph = new Graph("AAPL", dates);
            assertEquals(21, graph.data.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test void updateMethodInGraphModelShouldUpdateTheValuesOfTheGraphModel(){
        try{
            Date date1 = new Date(2022, 6, 20);
            Date date2 = new Date(2022, 7, 20);
            Graph graph = new Graph("AAPL", date1, date2);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
