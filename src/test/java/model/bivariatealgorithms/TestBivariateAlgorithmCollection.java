package model.bivariatealgorithms;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBivariateAlgorithmCollection {

    @BeforeAll
    static void initBivariateAlgorithmsCollection() {
        BivariateAlgorithmCollection.init();
    }

    @Test
    public void classForCalculatingPearsonCorrelationShouldBeRetrieved() {
        BivariateAlgorithms ba  = BivariateAlgorithmCollection.getGraphAlgorithms("Pearson correlation");
        assertTrue(ba instanceof PearsonCorrelation);
    }

    @Test
    public void methodShouldRetrieveASpecificAlgorithmName() {
        int numberOfAlgorithms = BivariateAlgorithmCollection.getKeySet().size();
        assertNotEquals(0,numberOfAlgorithms);
    }


}
