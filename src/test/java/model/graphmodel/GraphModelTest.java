package model.graphmodel;

import model.Date;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GraphModelTest {


    @Test
    public void graphTest(){
        try {

            Date date1 = new Date(2022, 9, 9);
            String mic = "MSFT";
            DateHashMap<Date, DayData> data = DataHandler.getCompanyData(date1, new Date(), mic);
            GraphModel graph = new GraphModel(mic, date1, new Date());
            graph.update();
            System.out.println(graph.values);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
