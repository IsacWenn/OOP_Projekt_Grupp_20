package model.graphmodel.keyfigures;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class TestKeyFigureCollection {

    @Test
    public void keyFigureAlgorithmsShouldHaveBeenAdded() {
        KeyFigureCollection.init();
        assertNotEquals(0, KeyFigureCollection.getKeySet().size());
    }

}
