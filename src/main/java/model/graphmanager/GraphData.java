package model.graphmanager;

import model.Date;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

import java.util.List;

public class GraphData {

    public GraphData() {

    }

    DateHashMap<Date, DayData> getCompanyData(String mic) {
        return DataHandler.getCompanyData(mic);
    }

    DateHashMap<Date, DayData> getCompanyData(String mic, List<Date> dates) {
        return DataHandler.getCompanyData(dates, mic);
    }

    DateHashMap<Date, DayData> getCompanyData(String mic, Date from, Date to) {
        return DataHandler.getCompanyData(from, to, mic);
    }

}
