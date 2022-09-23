package model.graphmanager.algorithms;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

public class LinearRegression implements Algorithm {

    DateHashMap<Date, DayData> data;

    public LinearRegression(DateHashMap<Date, DayData> inData) {
        this.data = inData;
    }

    @Override
    public DateHashMap<Date, Number> calculate() {

    }

}
