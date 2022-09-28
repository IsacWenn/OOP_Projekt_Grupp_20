package model.graphmanager;

import model.Date;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import model.graphmanager.algorithms.DailyChange;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {


    @Test
    public void graphTest(){
        try {

            Date date1 = new Date(2022, 9, 9);
            String mic = "MSFT";
            DateHashMap<Date, DayData> data = DataHandler.getCompanyData(date1, new Date(), mic);
            Graph graph = new Graph(mic, date1, new Date());
            graph.update();
            System.out.println(graph.values);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
