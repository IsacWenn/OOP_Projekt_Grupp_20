package model.datahandling;

/*
*
*   A static class dealing with everything within the model.datahandling package.
*
*/

import model.Date;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The class that communicates with the outside of the package datahandling. Contains methods for retrieving different
 * kinds of data.
 *
 * @author Isac
 */
public class DataHandler {

    public static void main(String[] args) {
        List<String> mics = getMICs();
        System.out.println(mics);
        // System.out.println(getCompanyData(mics.get(0)));

        List<Date> dates = new ArrayList<>();
        DateHashMap<Date, DayData> data = getCompanyData(mics.get(0));
        Date afterDate;
        try {
            afterDate = new Date(2022, 9, 8);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            afterDate = new Date();
        }
        for (Date date : data.keySet())
            if (date.isAfter(afterDate))
                dates.add(date);
        System.out.println(dates);

        System.out.println(getCompanyData(dates, mics.get(0)));
        try {
            System.out.println(getCompanyData(mics.get(0)).get(new Date(2022, 9, 9)));
        } catch (IOException e) {
            System.out.println("Error");
        }


        try {
            Date date1 = new Date(2022, 9, 9);
            System.out.println(getCompanyData(date1.listIntervalTo(new Date()), mics.get(0)));

            System.out.println("Latest Date : ");
            System.out.println(getLatestDayData(mics.get(0)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * A method for retrieving a {@link DateHashMap} of {@link Date}s and {@link DayData} for a company.
     *
     * @param mic A {@link String} containing the MIC of a specific company.
     * @return A {@link DateHashMap} containing the Data of a company.
     */
    public static DateHashMap<Date, DayData> getCompanyData(String mic) {
        String path = CompanyData.getFileName(mic);
        try {
            return StockExchangeReader.convertCSVFileToHandledData(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new DateHashMap<>() {{ put(new Date(), new DayData(0, 0, 0, 0, 0)); }};
    }

    /**
     * A method for retrieving a {@link DateHashMap} of {@link Date}s and {@link DayData} for a company on specified
     * dates.
     *
     * @param dates A {@link List} of {@link Date}s that contains the specified dates for retrieving the data.
     * @param mic A {@link String} of the company's MIC.
     * @return A {@link DateHashMap} containing the Data of a company on the specified dates.
     */
    public static DateHashMap<Date, DayData> getCompanyData(List<Date> dates, String mic) {
        String path = CompanyData.getFileName(mic);
        try {
            return filterDataByDates(StockExchangeReader.convertCSVFileToHandledData(path), dates);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new DateHashMap<>() {{ put(new Date(), new DayData(0, 0, 0, 0, 0)); }};
    }

    /**
     * A method for retrieving a {@link DateHashMap} of {@link Date}s and {@link DayData} for a company on a specified
     * interval of dates.
     *
     * @param from A {@link Date} that contains the starting point of the interval.
     * @param to A {@link Date} that contains the endpoint of the interval.
     * @param mic A {@link String} of the company's MIC.
     * @return A {@link DateHashMap} containing the Data of a company in the specified interval of Dates.
     */
    public static DateHashMap<Date, DayData> getCompanyData(Date from, Date to, String mic) {
        List<Date> interval = from.listIntervalTo(to);
        return getCompanyData(interval, mic);
    }

    /**
     * A method that filters data by using a {@link List} of {@link Date}s.
     *
     * @param data A {@link DateHashMap} containing the data from a company.
     * @param dates A {@link List} containing the specified days that should be returned.
     * @return A {@link DateHashMap} containing the data from a company on the specified dates.
     */
    private static DateHashMap<Date, DayData> filterDataByDates(
            DateHashMap<Date, DayData> data, List<Date> dates) {
        DateHashMap<Date, DayData> filteredData = new DateHashMap<>();
        for (Date date :  dates) {
            if (data.containsKey(date))
                filteredData.put(date, data.get(date));
        }
        return filteredData;
    }

    /**
     * A method for retrieving a {@link DateHashMap} of {@link Date}s and their corresponding {@link Double}s of a
     * currency exchange rate.
     *
     * @param path A {@link String} containing the local path of the desired CSV-file of currency exchange rates.
     * @return A {@link DateHashMap} containing the currency exchange rate data.
     */
    public static DateHashMap<Date, Double> getCurrencyData(String path) {
        IOException exception;
        try {
            return CurrencyExchangeReader.convertCSVFileToHandledData(path);
        } catch (IOException e) {
            exception = e;
            System.out.println(e.getMessage());
        }
        return new DateHashMap<>() {{ put(new Date(), 0d); }};
    }

    /**
     * A method for retrieving a {@link DateHashMap} of the latest day data of a company.
     *
     * @param mic A {@link String} containing the MIC of a company.
     * @return A {@link DateHashMap} containing the {@link Date} and {@link DayData} of the latest day data for that company.
     */
    public static DateHashMap<Date, DayData> getLatestDayData(String mic) {
        Date iteratorDate = new Date();
        DateHashMap<Date, DayData> data = getCompanyData(mic);
        while (!data.containsKey(iteratorDate)) {
            try {
                iteratorDate = iteratorDate.previousDate();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        Date finalIteratorDate = iteratorDate;
        return new DateHashMap<>(){{ put(new Date(finalIteratorDate), data.get(finalIteratorDate)); }};
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
}
