package model.graphmanager.algorithms;

import model.util.Date;
import model.datahandling.DateHashMap;

public interface Algorithm {
    DateHashMap<Date, Number> calculate();

}
