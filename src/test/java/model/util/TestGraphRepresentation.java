package model.util;

import model.graphmodel.graphalgorithms.GraphAlgorithms;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestGraphRepresentation {

    private GraphRepresentation testGraphRep1;
    private GraphRepresentation testGraphRep2;
    private GraphRepresentation testGraphRep3;

    @BeforeEach
    public void initializeTestingVariables() throws IOException {
        testGraphRep1 = new GraphRepresentation(
                new ArrayList<>(){{ add(new Date(2022, 10, 14)); }},
                GraphAlgorithms.DAILYCLOSINGPRICE,
                "MSFT",
                "GBP"
        );
        testGraphRep2 = new GraphRepresentation(
                new ArrayList<>(){{ add(new Date(2022, 10, 14)); }},
                GraphAlgorithms.DAILYCLOSINGPRICE,
                "MSFT",
                "GBP"
        );
        testGraphRep3 = new GraphRepresentation(
                new ArrayList<>(){{ add(new Date()); }},
                GraphAlgorithms.DAILYCHANGE,
                "MSFT",
                "GBP"
        );
    }

    @Test
    public void getIntervalShouldReturnInterval() throws IOException {
        List<Date> dateList = new ArrayList<>(){{ add(new Date(2022, 10, 14)); }};
        assertEquals(dateList, testGraphRep1.getInterval());
    }

    @Test
    public void getAlgorithmShouldReturnCorrectAlgorithmEnum() {
        assertEquals(GraphAlgorithms.DAILYCLOSINGPRICE, testGraphRep1.getAlgorithm());
    }

    @Test
    public void getCompanyMICShouldReturnCompanyMIC() {
        assertEquals("MSFT", testGraphRep1.getCompanyMIC());
    }

    @Test
    public void getConversionCurrencyShouldReturnCorrectCurrencyConversionEnum() {
        assertEquals("GBP", testGraphRep1.getConversionCurrency());
    }

    @Test
    public void equalsWithSameInstanceShouldReturnTrue() {
        GraphRepresentation dummyRep = testGraphRep1;
        assertEquals(dummyRep, testGraphRep1);
    }

    @Test
    public void equalsWithOtherTypeShouldReturnFalse() {
        assertNotEquals("HEJ", testGraphRep1);
    }

    @Test
    public void equalsWithOtherGraphRepsShouldReturnComparisonWithValues() {
        assertEquals(testGraphRep2, testGraphRep1);
        assertNotEquals(testGraphRep3, testGraphRep1);
    }

    @Test
    public void hashCodeShouldReturnAHashBasedOnValues() {
        assertNotEquals(testGraphRep1.hashCode(), testGraphRep3.hashCode());
        assertEquals(testGraphRep1.hashCode(), testGraphRep2.hashCode());
    }

    @Test
    public void toStringShouldReturnAStringRepresentationOfInstance() {
        assertEquals("GraphRepresentation{interval=[14/10/2022], algorithm=DAILYCLOSINGPRICE," +
                " companyMIC='MSFT', preferredCurrency=GBP}", testGraphRep1.toString());
    }
}
