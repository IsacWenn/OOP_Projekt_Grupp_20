package model.graphmodel;


import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class GraphModelDataTest {
    private GraphData graphData;

    @BeforeEach
    public void create_Instance(){
        GraphData graphData = new GraphData();
    }

    @Test
    public void getCompanyData_should_return_an_interval_of_dates_if_called_with_an_interval() {
        Date fromDate = null;
        Date toDate = null;
        try {
            fromDate = new Date(2022, 8, 20);
            toDate = new Date(2022, 8, 30);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DateHashMap<Date, DayData> data = graphData.getCompanyData("AAPL", fromDate, toDate);
        DayData dayData = data.get(toDate);
        DayData nullData = data.get(fromDate);
        assertEquals(158.91,dayData.getClosed());
        assertNull(nullData);
    }

}
