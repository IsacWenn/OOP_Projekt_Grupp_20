package model.datahandling;

/*
*
*   A static class dealing with everything within the model.datahandling package.
*
*/

import model.Date;

import java.io.IOException;
import java.util.HashMap;

public class DataHandler {

    public static HashMap<Date, HashMap<String, Object>> getCompanyData(String company) {
        String path = "";
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
        return new HashMap<>() {{ put(new Date(), errHash)}};
    }



}
