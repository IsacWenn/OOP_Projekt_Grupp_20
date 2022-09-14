package model;

import java.io.*;
import java.util.*;

/**
 *Class for reading a .csv file and placing every value in a list as a string
 *
 */

public class StockExchangeReader {

    private String deafultPath = "src/main/resources/StockExchangeData/";

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
        HashMap<String, HashMap> data = null;
        try {
            data = reader.convertCSVFileToHandledData(reader.deafultPath + "HistoricalData_AAPL.csv");
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

    HashMap<String, HashMap> convertCSVFileToHandledData(String path) throws IOException {
        HashMap<String, HashMap> data = new HashMap<>();
        String line = "";
        String date = "";
        BufferedReader br = new BufferedReader(new FileReader(path));
        while ((line = br.readLine()) != null) {
            HashMap<String, String> dateValues = new HashMap<>();
            int i = 0;
            for (String field : line.split(",")){
                if (i == 0) date = field;
                else {
                    dateValues.put(
                            StockExchangeDataTypes.values()[i].name().toLowerCase(),
                            field);
                }
                i += 1;
            }
            data.put(date, dateValues);
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


