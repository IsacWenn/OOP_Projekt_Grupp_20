package model.graphmodel.keyfigures;

import java.util.*;

/**
 * A class containing all available implementations of {@link KeyFigureAlgorithm} and is used to access them.
 * @author Carl
 * Uses StandardDeviation, AveragePrice, AverageVolume, HighestPrice, LowestPrice, KeyFigureAlgorithm.
 * Used by GraphModel
 */
public class KeyFigureCollection {

    /**
     * A static {@link Map} of {@link String} and {@link KeyFigureAlgorithm}.
     */
    private static final Map<String, KeyFigureAlgorithm> keyFigures = new HashMap<>() {{
        put("Standard Deviation", new StandardDeviation());
        put("Average Price", new AveragePrice());
        put("Average Volume", new AverageVolume());
        put("Highest Price", new HighestPrice());
        put("Lowest Price", new LowestPrice());
    }};

    /**
     * A method for retrieving a specific implementation of {@link KeyFigureAlgorithm} given the corresponding {@link String}.
     *
     * @param keyFigure A {@link String} symbolizing the different implementation of {@link KeyFigureAlgorithm}.
     * @return A {@link KeyFigureAlgorithm}.
     */
    public static KeyFigureAlgorithm getKeyFigure(String keyFigure){
        return keyFigures.get(keyFigure);
    }

    /**
     * A method for returning the names of all implementations of {@link KeyFigureAlgorithm}.
     *
     * @return A {@link List} of names represented as {@link String}.
     */
    public static List<String> getKeyFigureNames() {
        List<String> returnList = new ArrayList<>(Set.copyOf(keyFigures.keySet()));
        Collections.sort(returnList);
        return returnList;
    }
}
