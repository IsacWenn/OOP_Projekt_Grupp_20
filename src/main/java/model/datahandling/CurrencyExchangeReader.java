package model.datahandling;

import model.Date;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * A class that reads the different currency exchange files and converts them into {@link DateHashMap}s for use in
 * the rest of the program.
 *
 * @author Carl
 * @author Isac
 */
public class CurrencyExchangeReader {
    /**
     * A {@link String} containing the default path to the folder containing the CSV-files of currency exchange rates.
     */
    private static final String defaultPath = "src/main/resources/CurrencyExchangeData/";

    public static void main(String[] args) throws IOException {

        DateHashMap<Date, Double> data = null;
        try {
            data = convertCSVFileToHandledData(defaultPath + "SEK_USD.csv");
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }

        Date date1 = new Date(2020,6,1);

        System.out.println(data.get(date1));
    }

    /**
     * A method that converts a CSV-file of currency exchange rates into a {@link DateHashMap} for use in the rest of
     * the program
     *
     * @param path A {@link String} containing the local path to the desired CSV-file.
     * @return A {@link DateHashMap} containing all the information of that file.
     * @throws IOException if the file that the parameter path refers to does not exist.
     */
    static DateHashMap<Date, Double> convertCSVFileToHandledData(String path) throws IOException {
        DateHashMap<Date, Double> data = new DateHashMap<>();
        String line;
        path = defaultPath + path
        BufferedReader br = new BufferedReader(new FileReader(path));
        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] values = line.split("[,/]");
            Date date = new Date(Integer.parseInt(values[0]),Integer.parseInt(values[1]),Integer.parseInt(values[2]));
            double rate = Double.parseDouble(values[3]);
            data.put(date, rate);
        }

        return data;
    }

}
