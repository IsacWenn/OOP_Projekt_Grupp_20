package model.graphmodel.graphalgorithms;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;



public class TestGraphAlgorithmCollection {

    @BeforeAll
    static void initGraphAlgorithmsCollection() {
        GraphAlgorithmCollection.init();
    }

    @Test
    public void classForCalculatingPearsonCorrelationShouldBeRetrieved() {
        GraphAlgorithm ba  = GraphAlgorithmCollection.getGraphAlgorithm("Linear regression");
        assertTrue(ba instanceof LinearRegression);
    }

    @Test
    public void methodShouldRetrieveASpecificAlgorithmName() {
        int numberOfAlgorithms = GraphAlgorithmCollection.getKeySet().size();
        assertNotEquals(0,numberOfAlgorithms);
    }


}