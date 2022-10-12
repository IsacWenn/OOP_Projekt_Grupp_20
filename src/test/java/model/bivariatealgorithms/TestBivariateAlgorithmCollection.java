package model.bivariatealgorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestBivariateAlgorithmCollection {

    @Test
    public void classForCalclatingPearsonCorrelationShouldBeRetrieved() {
        BivariateAlgorithmCollection.init();
        BivariateAlgorithms ba  = BivariateAlgorithmCollection.getGraphAlgorithms().get("Pearson correlation coefficient");
        assertTrue(ba instanceof PearsonCorrelation);
    }

}
