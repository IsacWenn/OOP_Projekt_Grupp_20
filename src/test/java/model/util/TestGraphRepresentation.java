package model.util;

import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class TestGraphRepresentation {

    private GraphRepresentation testGraphRep1;
    private GraphRepresentation testGraphRep2;
    private GraphRepresentation testGraphRep3;

    @BeforeEach
    public void initializeTestingVariables() throws IOException {
        testGraphRep1 = new GraphRepresentation(
                new Date(2022, 10, 14),
                new Date(2022, 10, 15),
                "Closing Price",
                "MSFT",
                "GBP"
        );
        testGraphRep2 = new GraphRepresentation(
                new Date(2022, 10, 14),
                new Date(2022, 10, 15),
                "Closing Price",
                "MSFT",
                "GBP"
        );
        testGraphRep3 = new GraphRepresentation(
                new Date().previousDate(),
                new Date(),
                "Daily Change",
                "MSFT",
                "GBP"
        );
    }

    @Test
    public void getStartingDateShouldReturnStartingDate() throws IOException {
        Date start = new Date(2022, 10, 14);
        assertEquals(start, testGraphRep1.getStartingDate());
    }

    @Test
    public void getEndDateShouldReturnEndDate() throws IOException {
        Date end = new Date(2022, 10, 15);
        assertEquals(end, testGraphRep1.getEndDate());
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
    public void getAlgorithmShouldReturnAlgorithm() {
        assertEquals("Daily Change", testGraphRep3.getAlgorithm());
        assertEquals("Closing Price", testGraphRep2.getAlgorithm());
    }

    @Test
    public void toStringShouldReturnACorrectRepresentationOfTheObject() {
        assertEquals("GraphRepresentation{from=14/10/2022, to=15/10/2022," +
                " algorithm=Closing Price, companyMIC=MSFT, preferredCurrency=GBP}", testGraphRep1.toString());
    }
}
