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
        DateHashMap<Date, HashMap<String, Object>> data = getCompanyData(mics.get(0));
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
    }

    public static DateHashMap<Date, HashMap<String, Object>> getCompanyData(String mic) {
        String path = CompanyData.getFileName(mic);
        IOException exception;
        try {
            return StockExchangeReader.convertCSVFileToHandledData(path);
        } catch (IOException e) {
            exception = e;
            System.out.println(e.getMessage());
        }
        HashMap<String, Object> errHash = new HashMap<>(){{
            put("IOException", exception);
        }};
        return new DateHashMap<>() {{ put(new Date(), errHash); }};
    }

    public static DateHashMap<Date, HashMap<String, Object>> getCompanyData(List<Date> dates, String mic) {
        String path = CompanyData.getFileName(mic);
        IOException exception;
        DateHashMap<Date, HashMap<String, Object>> companyData;
        try {
            return filterDataByDates(StockExchangeReader.convertCSVFileToHandledData(path), dates);
        } catch (IOException e) {
            exception = e;
            System.out.println(e.getMessage());
        }
        HashMap<String, Object> errHash = new HashMap<>(){{
            put("IOException", exception);
        }};
        return new DateHashMap<>() {{ put(new Date(), errHash); }};
    }

    private static DateHashMap<Date, HashMap<String, Object>> filterDataByDates(
            DateHashMap<Date, HashMap<String, Object>> data, List<Date> dates) {
        DateHashMap<Date, HashMap<String, Object>> filteredData = new DateHashMap<>();
        for (Date date :  dates) {
            if (data.containsKey(date))
                filteredData.put(date, data.get(date));
            else
                System.out.println("No data exists for Date: " + date);
        }
        return filteredData;
    }


    public static DateHashMap<Date, Float> getCurrencyData(String path) {
        IOException exception;
        try {
            return CurrencyExchangeReader.convertCSVFileToHandledData(path);
        } catch (IOException e) {
            exception = e;
            System.out.println(e.getMessage());
        }
        return new DateHashMap<>() {{ put(new Date(), 0f); }};
    }

    public static List<String> getMICs() {
        return CompanyData.getMICs();
    }

    public static List<String> getCompanyNames() {
        return CompanyData.getCompanyNames();
    }
}
