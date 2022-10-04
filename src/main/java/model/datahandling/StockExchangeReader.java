package model.datahandling;

import model.util.Date;

import java.io.*;
import java.util.*;

/**
 * A class that reads the different stock exchange files and converts them into {@link DateHashMap}s for use in
 * the rest of the program.
 *
 * @author Isac
 * @author Dennis
 */
public class StockExchangeReader {

    /**
     * A static final{@link String} containing the default path to the folder containing the CSV-files of stock
     * exchange data.
     */
    private static final String defaultPath = "src/main/resources/StockExchangeData/";

    public static void main(String[] args) {
        testing();
    }

    private static void testing() {
        System.out.println("Testing");

        Map<Date, DayData> data = convertCSVFileToHandledData(defaultPath + "HistoricalData_AAPL.csv");

        /*
        *   Varje dags data ligger i en HashMap som är "Keyed" till de givna datumet i CSV filen.
        *
        *   EX data.get("09/12/2022") #=> HashMap som innehåller värdena för den dagen.
        *
        *   Notera att datum är angivet i USA:s format.
        */
        try {
            System.out.println(data);
            System.out.println(data.get(new Date(2022, 6, 13)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * A method that reads the file at the given path and converts that data into an {@link Map} for use
     * in the rest of the program.
     *
     * @param path A {@link String} containing the local path to the desired CSV-file.
     * @return A {@link Map} containing all the information of that file.
     * @throws IOException if the file that the parameter path refers to does not exist.
     */
    static Map<Date, DayData> convertCSVFileToHandledData(String path) {
        Map<Date, DayData> data = new HashMap<>();
        String line;
        path = defaultPath + path;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // String date = values[0];
                Date date = new Date(values[0]);

                int volume = Integer.parseInt(values[2]);
                double close = Double.parseDouble(values[1].substring(1));
                double open = Double.parseDouble(values[3].substring(1));
                double high = Double.parseDouble(values[4].substring(1));
                double low = Double.parseDouble(values[5].substring(1));

                DayData dayData = new DayData(volume, open, close, high, low);
                data.put(date, dayData);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
}


