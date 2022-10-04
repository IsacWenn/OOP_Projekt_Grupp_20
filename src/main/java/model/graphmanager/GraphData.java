package model.graphmanager;

import model.util.Date;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GraphData is a class that retrieves data from the {@link DataHandler} class
 *
 * @author Pontus
 * @author Isac
 */
class GraphData {

    public GraphData() {
    }

    /**
     * A method that calls the {@link DataHandler} class to retrieve all available data from a company
     *
     * @param mic a {@link String} representing a company's mic
     * @return a {@link Map} containing the data
     */
    Map<Date, DayData> getCompanyData(String mic) {
        return DataHandler.getCompanyData(mic);
    }

    /**
     * A method that calls the {@link DataHandler} class to retrieve company data from a list of {@link Date}
     *
     * @param mic a {@link String} representing a company's mic
     * @param dates a {@link List} of {@link Date}
     * @return a {@link Map} containing the data
     */
    Map<Date, DayData> getCompanyData(String mic, List<Date> dates) {
        return DataHandler.getCompanyData(dates, mic);
    }

    /**
     * A method that calls  the {@link DataHandler} class to retrieve company data from an interval of {@link Date}
     *
     * @param mic a {@link String} representing a company's mic
     * @param from a {@link Date} for the start of the interval
     * @param to a {@link Date} for the end of the interval
     * @return a {@link Map} containing the data
     */
    Map<Date, DayData> getCompanyData(String mic, Date from, Date to) {
        return DataHandler.getCompanyData(from, to, mic);
    }

    Map<Date, DayData> getLatestDayData(String mic){
        try {
            return DataHandler.getLatestDayData(mic);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new HashMap<>(){{ put(new Date(), new DayData(0,0, 0, 0, 0)); }};
    }

    /**
     *
     * @param currency
     * @return a {@link Map} containing the data
     */
    Map<Date, Double> getCurrencyData(String currency){
        return DataHandler.getCurrencyData(currency);
    }
}
