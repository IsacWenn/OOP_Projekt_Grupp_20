package model;

import java.io.*;
import java.util.*;

/**
 *Class for reading a .csv file and placing every value in a list as a string
 *
 */

public class StockExchangeReader {

    private final String defaultPath = "src/main/resources/StockExchangeData/";

    public StockExchangeReader() {

    }
    ArrayList<String> readCsvFile(String path) throws IOException {
        ArrayList<String> output = new ArrayList<>();
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(path));
        while ((line = br.readLine()) != null) {
            String[] data = line.split(","); // Date (0), Close(1), Volume(2), Open(3), High(4), Low(5)
            output.addAll(Arrays.asList(data));
        }
        return output;
    }


    public static void main(String[] args) {
        testing();
    }

    private static void testing(){
        System.out.println("Testing");

        StockExchangeReader reader = new StockExchangeReader();
        HashMap<String, HashMap<String, Object>> data = null;
        try {
            data = reader.convertCSVFileToHandledData(reader.defaultPath + "HistoricalData_AAPL.csv");
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }

        /*
        *   Varje dags data ligger i en HashMap som är "Keyed" till de givna datumet i CSV filen.
        *
        *   EX data.get("09/12/2022") #=> HashMap som innehåller värdena för den dagen.
        *
        *   Notera att datum är angivet i USA:s format.
        */

        System.out.println(data.get("09/12/2022"));
    }

    HashMap<String, HashMap<String, Object>> convertCSVFileToHandledData(String path) throws IOException {
        HashMap<String, HashMap<String, Object>> data = new HashMap<>();
        String line;
        BufferedReader br = new BufferedReader(new FileReader(path));
        br.readLine();
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");

            String date = values[0];
            HashMap<String, Object> mappedValues = new HashMap<>() {{
                put("close",  Float.parseFloat(values[1].substring(1)));
                put("volume", Integer.parseInt(values[2]));
                put("open",   Float.parseFloat(values[3].substring(1)));
                put("high",   Float.parseFloat(values[4].substring(1)));
                put("low",    Float.parseFloat(values[5].substring(1)));
            }};

            data.put(date, mappedValues);
        }

        return data;
    }


    private enum StockExchangeDataTypes {
        DATE,
        CLOSE,
        VOLUME,
        OPEN,
        HIGH,
        LOW
    }

}


