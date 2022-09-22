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
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static DateHashMap<Date, DayData> getCompanyData(String mic) {
        String path = CompanyData.getFileName(mic);
        try {
            return StockExchangeReader.convertCSVFileToHandledData(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new DateHashMap<>() {{ put(new Date(), new DayData(0, 0, 0, 0, 0)); }};
    }

    public static DateHashMap<Date, DayData> getCompanyData(List<Date> dates, String mic) {
        String path = CompanyData.getFileName(mic);
        try {
            return filterDataByDates(StockExchangeReader.convertCSVFileToHandledData(path), dates);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new DateHashMap<>() {{ put(new Date(), new DayData(0, 0, 0, 0, 0)); }};
    }

    public static DateHashMap<Date, DayData> getCompanyData(Date from, Date to, String mic) {
        List<Date> interval = from.listIntervalTo(to);
        return getCompanyData(interval, mic);
    }

    private static DateHashMap<Date, DayData> filterDataByDates(
            DateHashMap<Date, DayData> data, List<Date> dates) {
        DateHashMap<Date, DayData> filteredData = new DateHashMap<>();
        for (Date date :  dates) {
            if (data.containsKey(date))
                filteredData.put(date, data.get(date));
        }
        return filteredData;
    }


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

    public static List<String> getMICs() {
        return CompanyData.getMICs();
    }

    public static List<String> getCompanyNames() {
        return CompanyData.getCompanyNames();
    }
}
