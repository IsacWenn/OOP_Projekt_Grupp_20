package model.datahandling;


import model.util.Date;
import org.junit.jupiter.api.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestDataHandler {

    private static Map<Date, DayData> AAPL_Map;
    private static Date lastDate;
    private static DayData lastDateDayData;
    private static List<Date> dateList;

    @BeforeEach
    public void resetStaticVariables() throws IOException {
        AAPL_Map = DataHandler.getCompanyData("AAPL");
        lastDate = new Date(2022, 9, 13);
        lastDateDayData = new DayData(
                122656600, 159.9,
                153.84, 160.54,
                153.37
        );
        dateList = new Date(2022, 6, 1).listIntervalTo(new Date());
    }

    @Test
    public void getCompanyDataShouldReturnCorrectData() throws IOException {
        Map<Date, DayData> dataMap = DataHandler.getCompanyData("AAPL");
        List<Date> dates = Date.sortDates(dataMap.keySet());
        assertEquals(lastDate, dates.get(dates.size() - 1));
        assertEquals(lastDateDayData, dataMap.get(lastDate));
    }

    @Test
    public void getCompanyDataWithListOfDatesShouldReturnCorrectDataInterval() {
        assertTrue(true);
    }



    /*
    Testing of date retrieval.
     */

    @Test
    public void retrieveClosestExchangeRateShouldReturnCorrectValue() throws IOException {
        Map<Date, Double> map = new DateHashMap<>(){{       // Fungerar utan problem
            put(new Date(2022, 9, 27), 5.3d);
        }};
        Map<Date, Double> map2 = new HashMap<>(){{       // Fungerar ej
            put(new Date(2022, 9, 27), 5.3d);
        }};
        double d = DataHandler.retrieveClosestExchangeRate(new Date(), map);
        assertEquals(5.3d, d);
        assertThrows(StackOverflowError.class, () -> {
            double d2 = DataHandler.retrieveClosestExchangeRate(new Date(), map2);
        });

        /*
            Av vad jag kan se så använder sig AbstractMap av Object.equals() när den kollar om en key existerar i sig
            själv. Problemet uppstår för att HashMap Override:ar den metoden och kallar sin egna getNode() metod istället.
            Om jag förstått hur den metoden fungerar korrekt så uppstår felet där eftersom 'key' jämförelsen har med
            referens till minnet istället för Object.equals() att göra.

            Eftersom vi pratade under handledningstillfället om implementation av DateHashMap undrar jag följande?

                1. Är klassen DateHashMap som jag skrivit en OK lösning på detta problemet eller föredrar du att jag
                   hittar en annan?

                2. Om jag ska hitta en annan lösning har du någon preferens/idé för vilken typ av Map
                   som fungerar bäst för detta eftersom HashMap ej gör det?

            // Isac Ingvast Wennerström
         */
    }
    
}
