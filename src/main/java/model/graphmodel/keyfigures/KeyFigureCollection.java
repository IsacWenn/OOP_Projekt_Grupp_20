package model.graphmodel.keyfigures;

import model.graphmodel.keyfigures.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KeyFigureCollection {

    /**
     * A static {@link Map} of {@link String} and {@link KeyFigureAlgorithm}.
     */
    private static Map<String, KeyFigureAlgorithm> keyFigures = null;

    /**
     * A class for retrieving all available implementations of {@link KeyFigureAlgorithm}.
     */
    private KeyFigureCollection(){
    }

    /**
     * A method for creating implementations of {@link KeyFigureAlgorithm} and adding them to {@link KeyFigureCollection#keyFigures}.
     */
    public static void init(){
        if (keyFigures == null){
            keyFigures = new HashMap<>();
            keyFigures.put("Volatility", new Volatility());
            keyFigures.put("Average Price", new AveragePrice());
            keyFigures.put("Average Volume", new AverageVolume());
            keyFigures.put("Highest Price", new HighestPrice());
            keyFigures.put("Lowest Price", new LowestPrice());
        }
    }

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
     * @return A {@link Set} of names represented as {@link String}.
     */
    public static Set<String> getKeySet(){return Set.copyOf(keyFigures.keySet());}
}
