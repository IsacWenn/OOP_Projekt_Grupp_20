package model.datahandling;

/*
*
*   A static class dealing with everything within the model.datahandling package.
*
*/

import model.Date;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DataHandler {

    public static void main(String[] args) {
        List<String> mics = getMICs();
        System.out.println(mics);
        System.out.println(getCompanyData(mics.get(0)));
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

    public static List<String> getMICs() {
        return CompanyData.getMICs();
    }

    public static List<String> getCompanyNames() {
        return CompanyData.getCompanyNames();
    }
}
