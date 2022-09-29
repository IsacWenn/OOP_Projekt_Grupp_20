package model.graphmodel;

import model.Date;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class GraphModelTest {


    @Test
    public void graphModelCreatedWithOnlyMicShouldContainAllAvailableCompanyData() {
        GraphModel graphModel = new GraphModel("AAPL");
        assertEquals(2515, graphModel.data.size());
    }

    @Test
    public void graphModelCreatedWithMicAndDateIntervalShouldContainDataForThatInterval() {
        try {
            Date date1 = new Date(2022, 1, 1);
            GraphModel graphModel = new GraphModel("AAPL", date1, new Date());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void graphModelCreatedWithAListOfDatesShouldContainDataForThoseDates(){
        try{
            Date date1 = new Date(2022,1 ,1);
            Date date2 = new Date(2022,1, 2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
