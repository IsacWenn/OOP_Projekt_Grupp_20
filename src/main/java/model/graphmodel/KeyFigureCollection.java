package model.graphmodel;

import java.util.HashMap;
import java.util.Map;

public class KeyFigureCollection {

    private static Map<String, KeyFigureAlgorithm> keyFigures = null;

    private KeyFigureCollection(){
    }

    public static void init(){
        if (keyFigures == null){
            keyFigures = new HashMap<>();
            keyFigures.put("Volatility", new Volatility());
        }
    }

    public static Map<String, KeyFigureAlgorithm> getKeyFigureCollection(){
        return keyFigures;
    }
}
