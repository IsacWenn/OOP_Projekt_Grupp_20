package model.graphmodel;

import model.Date;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

public class GraphModelTest {

    @Test
    public void graphModelCreatedWithOnlyMicShouldContainAllAvailableCompanyData() {
        GraphModel graphModel = new GraphModel("AAPL");
        assertEquals(2515, graphModel.data.size());
    }

    @Test
    public void graphModelCreatedWithMicAndDateIntervalShouldContainDataForThatInterval() {
        try {
            Date date1 = new Date(2022, 6, 20);
            Date date2 = new Date(2022,7, 21);
            GraphModel graphModel = new GraphModel("AAPL", date1, date2);
            assertEquals(22, graphModel.data.size());
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
            GraphModel graphModel = new GraphModel("AAPL", dates);
            assertEquals(21, graphModel.data.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test void updateMethodInGraphModelShouldUpdateTheValuesOfTheGraphModel(){
        try{
            Date date1 = new Date(2022, 6, 20);
            Date date2 = new Date(2022, 7, 20);
            GraphModel graphModel = new GraphModel("AAPL", date1, date2);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
