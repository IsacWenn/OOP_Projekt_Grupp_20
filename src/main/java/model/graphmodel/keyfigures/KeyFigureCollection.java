package model.graphmodel.keyfigures;

import model.graphmodel.keyfigures.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KeyFigureCollection {

    private static Map<String, KeyFigureAlgorithm> keyFigures = null;

    private KeyFigureCollection(){
    }

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

    public static KeyFigureAlgorithm getKeyFigure(String keyFigure){
        return keyFigures.get(keyFigure);
    }

    public static Set<String> getKeySet(){return keyFigures.keySet();}
}
