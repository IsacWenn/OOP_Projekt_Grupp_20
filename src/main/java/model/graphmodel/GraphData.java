package model.graphmodel;

import model.util.CurrencyEnum;
import model.util.Date;
import model.datahandling.DataHandler;
import model.datahandling.DayData;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * @param companyMIC
     * @param
     * @return
     */
    Map<Date, Double> getNativeCurrencyData(String companyMIC, Set<Date> dates){
        CurrencyEnum nativeCurrency = getCompanyCurrency(companyMIC);
        return getCurrencyData(nativeCurrency, dates);
    }

    Map<Date, Double> getCurrencyData(CurrencyEnum toCurrency, Set<Date> dates){
        if(toCurrency ==CurrencyEnum.USD){
            return putOnes(dates);
        }
        String path = "USD_TO_" + toCurrency.toString() + ".csv";
        return DataHandler.getCurrencyData(path);
    }

    private Map<Date, Double> putOnes(Set<Date> dates){
        Map<Date, Double> result = new HashMap<>();
        for (Date date: dates){
            result.put(date, 1d);
        }
        return result;
    }
    /**
     *
     * @param mic
     * @return
     */
    CurrencyEnum getCompanyCurrency(String mic){
        return DataHandler.getCompanyTradingCurrency(mic);
    }
}
