package model.graphmanager;

import model.Date;
import model.datahandling.DateHashMap;

import java.util.HashMap;

public interface Algorithm {
    DateHashMap calculate(DateHashMap<Date, HashMap<String, Object>> data);
}
