package model.datahandling;

/*
*
*   A static class dealing with everything within the model.datahandling package.
*
*/

import model.util.CurrencyEnum;
import model.util.Date;

import java.io.IOException;
import java.util.*;

/**
 * The class that communicates with the outside of the package datahandling. Contains methods for retrieving different
 * kinds of data.
 *
 * @author Isac
 */
public class DataHandler {

    /**
     * A method for retrieving a {@link Map} of {@link Date}s and {@link DayData} for a company.
     *
     * @param mic A {@link String} containing the MIC of a specific company.
     * @return A {@link Map} containing the Data of a company.
     */
    public static Map<Date, DayData> getCompanyData(String mic) {
        try {
            String path = CompanyData.getFileName(mic);
            return StockExchangeReader.convertCSVFileToHandledData(path);
        } catch (NullPointerException | IOException e) {
            System.out.println(e.getMessage());
        }
        return new HashMap<>() {{ put(new Date(), new DayData(0, 0, 0, 0, 0)); }};
    }

    /**
     * A method for retrieving a {@link Map} of {@link Date}s and {@link DayData} for a company on specified
     * dates.
     *
     * @param dates A {@link List} of {@link Date}s that contains the specified dates for retrieving the data.
     * @param mic A {@link String} of the company's MIC.
     * @return A {@link Map} containing the Data of a company on the specified dates.
     */
    public static Map<Date, DayData> getCompanyData(List<Date> dates, String mic) {
        try {
            String path = CompanyData.getFileName(mic);
            return filterDataByDates(StockExchangeReader.convertCSVFileToHandledData(path), dates);
        } catch (NullPointerException | IOException e) {
            return new HashMap<>(){{ put(new Date(), new DayData(0, 0, 0, 0, 0)); }};
        }
    }

    /**
     * A method for retrieving a {@link Map} of {@link Date}s and {@link DayData} for a company on a specified
     * interval of dates.
     *
     * @param from A {@link Date} that contains the starting point of the interval.
     * @param to A {@link Date} that contains the endpoint of the interval.
     * @param mic A {@link String} of the company's MIC.
     * @return A {@link Map} containing the Data of a company in the specified interval of Dates.
     */
    public static Map<Date, DayData> getCompanyData(Date from, Date to, String mic) {
        List<Date> interval = from.listIntervalTo(to);
        return getCompanyData(interval, mic);
    }

    /**
     * A method that filters data by using a {@link List} of {@link Date}s.
     *
     * @param data A {@link Map} containing the data from a company.
     * @param dates A {@link List} containing the specified days that should be returned.
     * @return A {@link Map} containing the data from a company on the specified dates.
     */
    private static Map<Date, DayData> filterDataByDates(Map<Date, DayData> data, List<Date> dates) {
        Map<Date, DayData> filteredData = new HashMap<>();
        for (Date date :  dates) {
            if (data.containsKey(date))
                filteredData.put(date, data.get(date));
        }
        return filteredData;
    }

    /**
     * A method for retrieving a {@link HashMap} of {@link Date}s and their corresponding {@link Double}s of a
     * currency exchange rate.
     *
     * @param path A {@link String} containing the local path of the desired CSV-file of currency exchange rates.
     * @return A {@link Map} containing the currency exchange rate data.
     */
    public static Map<Date, Double> getCurrencyData(String path) {
        try {
            return CurrencyExchangeReader.convertCSVFileToHandledData(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new HashMap<>() {{ put(new Date(), 0d); }};
    }

    /**
     * A method for retrieving a {@link Map} of {@link Date}s and {@link Double} for an exchange rate on specified
     * dates.
     *
     * @param dates A {@link Set} of {@link Date}s that contains the specified dates for retrieving the data.
     * @param path A {@link String} for the files' path.
     * @return A {@link Map} containing the Data of an exchange rate on the specified dates.
     */
    public static Map<Date, Double> getCurrencyData(Set<Date> dates, String path) {
        return getCurrencyData(new ArrayList<>(dates), path);
    }

    /**
     * A method for retrieving a {@link Map} of {@link Date}s and {@link Double} for an exchange rate on specified
     * dates.
     *
     * @param dates A {@link List} of {@link Date}s that contains the specified dates for retrieving the data.
     * @param path A {@link String} for the files' path.
     * @return A {@link Map} containing the Data of an exchange rate on the specified dates.
     */
    public static Map<Date, Double> getCurrencyData(List<Date> dates, String path) {
        try {
            return filterCurrencyData(CurrencyExchangeReader.convertCSVFileToHandledData(path), dates);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new HashMap<>() {{ put(new Date(), 0d); }};
    }

    /**
     * A method for retrieving a {@link HashMap} of {@link Date}s and their corresponding {@link Double}s of a
     * currency exchange rate. Expands the Map to include data for the dates that does not exist originally.
     *
     * @param path A {@link String} containing the local path of the desired CSV-file of currency exchange rates.
     * @return A {@link Map} containing the currency exchange rate data.
     */
    public static Map<Date, Double> getExpandedCurrencyData(String path) {
        Map<Date, Double> map;
        try {
            map = CurrencyExchangeReader.convertCSVFileToHandledData(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new HashMap<>(){{ put(new Date(), 0d); }};
        }
        List<Date> dates = new ArrayList<>(map.keySet());
        dates = Date.sortDates(dates);
        dates = dates.get(0).listIntervalTo(dates.get(dates.size() - 1));
        return filterCurrencyData(map, dates);
    }

    /**
     * A method for retrieving a {@link HashMap} of {@link Date}s and their corresponding {@link Double}s of a
     * currency exchange rate. Expands the Map to include data for the dates that does not exist originally. Limits
     * the amount of Data to the {@link List} of dates given.
     *
     * @param dates the list of dates.
     * @param path the file-path for the currency exchange rate.
     * @return a Map of Dates and Doubles.
     */
    public static Map<Date, Double> getExpandedCurrencyData(List<Date> dates, String path) {
        Map<Date, Double>  map = getExpandedCurrencyData(path);
        return filterCurrencyData(map, dates);
    }

    /**
     * A method for retrieving a {@link HashMap} of {@link Date}s and their corresponding {@link Double}s of a
     * currency exchange rate. Expands the Map to include data for the dates that does not exist originally. Limits
     * the amount of Data to the {@link List} of dates given.
     *
     * @param dates the set of dates.
     * @param path the file-path for the currency exchange rate.
     * @return a Map of Dates and Doubles.
     */
    public static Map<Date, Double> getExpandedCurrencyData(Set<Date> dates, String path) {
        return getExpandedCurrencyData(new ArrayList<>(dates), path);
    }

    /**
     * A method that filters currency Data for a given {@link Map} and {@link List} of dates.
     *
     * @param map the {@link Map} of currency data.
     * @param dates the {@link List} of dates.
     * @return A {@link Map} of {@link Date}s and {@link Double}s containing the new data.
     */
    private static Map<Date, Double> filterCurrencyData(Map<Date, Double> map, List<Date> dates) {
        Map<Date, Double> filteredCurrencyData = new HashMap<>();
        for (Date date : dates) {
            if (map.containsKey(date))
                filteredCurrencyData.put(date, map.get(date));
            else
                try {
                    filteredCurrencyData.put(date, DataHandler.retrieveClosestExchangeRate(date, map));
                } catch (IOException | StackOverflowError e) {
                    System.out.println(e.getMessage());
                    filteredCurrencyData.put(date, 0d);
                }
        }
        return filteredCurrencyData;
    }

    /**
     * A method for retrieving a {@link Map} of the latest day data of a company.
     *
     * @param mic A {@link String} containing the MIC of a company.
     * @return A {@link Map} containing the {@link Date} and {@link DayData} of the latest day data for that company.
     */
    public static Map<Date, DayData> getLatestDayData(String mic) throws IOException {
        Date iteratorDate = new Date();
        Map<Date, DayData> data = getCompanyData(mic);
        while (!data.containsKey(iteratorDate))
            iteratorDate = iteratorDate.previousDate();
        Date finalIteratorDate = iteratorDate;
        return new HashMap<>(){{ put(new Date(finalIteratorDate), data.get(finalIteratorDate)); }};
    }

    /**
     * A method that retrieves a {@link List} of all the different MIC:s in the database.
     *
     * @return A {@link List} of the different MIC:s.
     */
    public static List<String> getMICs() {
        return CompanyData.getMICs();
    }

    /**
     * A method that retrieves a {@link List} of all the different names of companies in the database.
     *
     * @return A {@link List} of the different names.
     */
    public static List<String> getCompanyNames() {
        return CompanyData.getCompanyNames();
    }

    /**
     * A method that retrieves a {@link String} containing the specified company name.
     *
     * @param mic A {@link String} of the company's MIC.
     * @return A {@link String} of the company's name.
     */
    public static String getCompanyName(String mic) { return CompanyData.getCompanyName(mic); }

    /**
     * A method that retrieves the {@link CurrencyEnum} representing a company's trading currency.
     *
     * @param mic A {@link String} of the company's MIC.
     * @return A {@link CurrencyEnum} representing that company's trading currency.
     */
    public static CurrencyEnum getCompanyTradingCurrency(String mic) { return CompanyData.getCurrency(mic); }

    /**
     * A method that returns the closest exhange rate for a given date. If there is not an exchange rate for the given
     * date it starts to search through the past.
     *
     * @param date The specified {@link Date}.
     * @param map The {@link Map} to retrieve the exchange rate for the given date.
     * @return A {@link Double} exchange rate for the given date.
     * @throws IOException If there is not any given exchange rates before the given date.
     */
    public static Double retrieveClosestExchangeRate(Date date, Map<Date, Double> map) throws IOException {
        return (map.containsKey(date) ? map.get(date) : retrieveClosestExchangeRate(date.previousDate(), map));
    }

}
