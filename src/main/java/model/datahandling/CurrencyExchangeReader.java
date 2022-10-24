package model.datahandling;

import model.util.Date;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that reads the different currency exchange files and converts them into {@link Map}s for use in
 * the rest of the program.
 *
 * @author Carl
 * @author Isac
 */
class CurrencyExchangeReader implements CurrencyDataRetriever {
    /**
     * A static final{@link String} containing the default path to the folder containing the CSV-files of currency
     * exchange rates.
     */
    private static final String defaultPath = "src/main/resources/CurrencyExchangeData/";

    /**
     * A method that converts a CSV-file of currency exchange rates into a {@link Map} for use in the rest of
     * the program
     *
     * @param path A {@link String} containing the local path to the desired CSV-file.
     * @return A {@link Map} containing all the information of that file.
     * @throws IOException if the file that the parameter path refers to does not exist.
     */
    static Map<Date, Double> convertCSVFileToHandledData(String path) throws IOException {
        Map<Date, Double> data = new HashMap<>();
        String line;
        path = defaultPath + path;
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);

        while ((line = br.readLine()) != null) {
            String[] values = line.split("[,/]");
            Date date = new Date(Integer.parseInt(values[0]),Integer.parseInt(values[1]),Integer.parseInt(values[2]));
            double rate = Double.parseDouble(values[3]);
            data.put(date, rate);
        }
        fr.close();
        br.close();
        return data;
    }

    /**
     * An implementation of {@link CurrencyDataRetriever#retrieveData(String)} for .csv files.
     *
     * @param currencyExchangeName the name of the exchange rate.
     * @return a {@link Map} of {@link Double}s containing the exchange rate.
     * @throws IOException if the exchange rate can not be found.
     */
    @Override
    public Map<Date, Double> retrieveData(String currencyExchangeName) throws IOException {
        return convertCSVFileToHandledData(currencyExchangeName + ".csv");
    }
}
