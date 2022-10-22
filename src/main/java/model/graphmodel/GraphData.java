package model.graphmodel;

import model.util.CurrencyCollection;
import model.util.Date;
import model.datahandling.DataHandler;
import model.datahandling.DayData;

import java.util.*;

/**
 * GraphData is a class that retrieves data from the {@link DataHandler} class.
 *
 * @author Pontus
 * @author Isac
 */
class GraphData {

    public GraphData() {
    }

    /**
     * A method that calls the {@link DataHandler} class to retrieve all available data from a company.
     *
     * @param mic a {@link String} representing a company's mic.
     * @return a {@link Map} containing the data.
     */
    Map<Date, DayData> getCompanyData(String mic) {
        return DataHandler.getCompanyData(mic);
    }

    /**
     * A method that calls the {@link DataHandler} class to retrieve company data from a list of {@link Date}.
     *
     * @param mic a {@link String} representing a company's mic.
     * @param dates a {@link List} of {@link Date}.
     * @return a {@link Map} containing the data.
     */
    Map<Date, DayData> getCompanyData(String mic, List<Date> dates) {
        return DataHandler.getCompanyData(dates, mic);
    }

    /**
     * A method that calls the {@link DataHandler} class to retrieve company data from an interval of {@link Date}.
     *
     * @param mic a {@link String} representing a company's mic.
     * @param from a {@link Date} for the start of the interval.
     * @param to a {@link Date} for the end of the interval.
     * @return a {@link Map} containing the data.
     */
    Map<Date, DayData> getCompanyData(String mic, Date from, Date to) {
        return DataHandler.getCompanyData(from, to, mic);
    }

    /**
     * A Method for retrieving the currency rate for the currency that the stock is traded in at the stock exchange.
     *
     * @param companyMIC a {@link String} representing a company's mic.
     * @param dates the {@link Set} of {@link Date} that the data in GraphModel currently contains.
     * @return a {@link Map} of the native currency's exchange rate for the company.
     */
    Map<Date, Double> getNativeCurrencyData(String companyMIC, Set<Date> dates){
        String nativeCurrency = getCompanyCurrency(companyMIC);
        return getCurrencyData(nativeCurrency, dates);
    }

    /**
     * A method for retrieving currency exchange rates for a given currency.
     *
     * @param toCurrency an {@link Enum} representing the currency that you want convert to.
     * @param dates the {@link Set} of {@link Date} that the data in GraphModel currently contains.
     * @return a {@link Map} of the exchange rate.
     */
    Map<Date, Double> getCurrencyData(String toCurrency, Set<Date> dates){
        if(Objects.equals(toCurrency, CurrencyCollection.getDefaultCurrencyName())){
            return putOnes(dates);
        }
        String path = "USD_TO_" + toCurrency + ".csv";
        return DataHandler.getExpandedCurrencyData(dates, path);
    }

    /**
     * A method for producing a {@link Map} containing an exchange rate of one for all {@link Date}.
     *
     * @param dates the {@link Set} of {@link Date} that the data in GraphModel currently contains.
     * @return a {@link Map} that effectively keeps the native exchange rate.
     */
    private Map<Date, Double> putOnes(Set<Date> dates){

        return  new HashMap<Date, Double>(){{
            for (Date date : dates)
                put(date, 1d);
        }};
    }
    
    /**
     * A method for getting the name of the currency the stock is traded in.
     *
     * @param mic a {@link String} representing a company's mic.
     * @return a {@link String} that represents the currency the Company stock has natively.
     */
    String getCompanyCurrency(String mic){
        return DataHandler.getCompanyTradingCurrency(mic);
    }
}
