package model.graphmodel.graphalgorithms;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;



public class TestGraphAlgorithmCollection {

    @Test
    public void classForCalculatingPearsonCorrelationShouldBeRetrieved() {
        GraphAlgorithm ba  = GraphAlgorithmCollection.getGraphAlgorithm("Linear Regression");
        assertTrue(ba instanceof LinearRegression);
    }

    @Test
    public void methodShouldRetrieveASpecificAlgorithmName() {
        int numberOfAlgorithms = GraphAlgorithmCollection.getGraphAlgorithmNames().size();
        assertNotEquals(0,numberOfAlgorithms);
    }

    @Test
    public void defaultAlgorithmNameShouldContainPrice() {
        String nameOfDefaultAlgo = GraphAlgorithmCollection.getDefaultGraphAlgorithmName();
        assertTrue(nameOfDefaultAlgo.toLowerCase().contains("price"));
    }


}