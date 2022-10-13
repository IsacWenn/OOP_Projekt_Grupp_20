package model.graphmodel.keyfigures;

import model.datahandling.DayData;
import model.util.Date;

import java.util.Map;

public interface KeyFigureAlgorithm {
    Double calculate(Map<Date, DayData> data);
}
