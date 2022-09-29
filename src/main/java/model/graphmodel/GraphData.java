package model.graphmodel;

import model.Date;
import model.datahandling.DataHandler;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;

import java.util.List;

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
     * @return a {@link DateHashMap} containing the data
     */
    DateHashMap<Date, DayData> getCompanyData(String mic) {
        return DataHandler.getCompanyData(mic);
    }

    /**
     * A method that calls the {@link DataHandler} class to retrieve company data from a list of {@link Date}
     *
     * @param mic a {@link String} representing a company's mic
     * @param dates a {@link List} of {@link Date}
     * @return a {@link DateHashMap} containing the data
     */
    DateHashMap<Date, DayData> getCompanyData(String mic, List<Date> dates) {
        return DataHandler.getCompanyData(dates, mic);
    }

    /**
     * A method that calls  the {@link DataHandler} class to retrieve company data from an interval of {@link Date}
     *
     * @param mic a {@link String} representing a company's mic
     * @param from a {@link Date} for the start of the interval
     * @param to a {@link Date} for the end of the interval
     * @return a {@link DateHashMap} containing the data
     */
    DateHashMap<Date, DayData> getCompanyData(String mic, Date from, Date to) {
        return DataHandler.getCompanyData(from, to, mic);
    }

    DateHashMap<Date, DayData> getLatestDayData(String mic){
        return DataHandler.getLatestDayData(mic);
    }

    /**
     *
     * @param currency
     * @return a {@link DateHashMap} containing the data
     */
    DateHashMap<Date, Double> getCurrencyData(String currency){
        return DataHandler.getCurrencyData(currency);
    }
}
