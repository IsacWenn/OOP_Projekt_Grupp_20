package model.bivariatealgorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestBivariateAlgorithmCollection {

    @Test
    public void classForCalculatingPearsonCorrelationShouldBeRetrieved() {
        BivariateAlgorithmCollection.init();
        BivariateAlgorithms ba  = BivariateAlgorithmCollection.getGraphAlgorithms("Pearson correlation");
        assertTrue(ba instanceof PearsonCorrelation);
    }

}
