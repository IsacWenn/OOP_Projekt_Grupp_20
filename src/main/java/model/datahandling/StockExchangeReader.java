package model.datahandling;

import model.util.Date;

import java.io.*;
import java.util.*;

/**
 * A class that reads the different stock exchange files and converts them into {@link HashMap}s for use in
 * the rest of the program.
 *
 * @author Isac
 * @author Dennis
 */
class StockExchangeReader implements StockDataRetriever {

    /**
     * A static final{@link String} containing the default path to the folder containing the CSV-files of stock
     * exchange data.
     */
    private static final String defaultPath = "src/main/resources/StockExchangeData/";


    /**
     * A method that reads the file at the given path and converts that data into an {@link Map} for use
     * in the rest of the program.
     *
     * @param path A {@link String} containing the local path to the desired CSV-file.
     * @return A {@link Map} containing all the information of that file.
     * @throws IOException if the file that the parameter path refers to does not exist.
     */
    static Map<Date, DayData> convertCSVFileToHandledData(String path) throws IOException {
        Map<Date, DayData> data = new HashMap<>();
        String line;
        path = defaultPath + path;
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
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
        fr.close();
        br.close();
        return data;
    }

    /**
     * A implementation of {@link StockDataRetriever#retrieveData(String)} for .csv files.
     *
     * @param mic the MIC of the company.
     * @return a {@link Map} of {@link Date} and {@link DayData} containing the stock data of a company.
     * @throws IOException if the file for a given mic does not exist.
     */
    @Override
    public Map<Date, DayData> retrieveData(String mic) throws IOException {
        return convertCSVFileToHandledData(CompanyData.getFileName(mic));
    }
}


