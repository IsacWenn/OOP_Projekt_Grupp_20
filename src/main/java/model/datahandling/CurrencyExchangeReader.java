package model.datahandling;

import model.Date;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CurrencyExchangeReader {

    private final String defaultPath = "src/main/resources/CurrencyExchangeData/";


    public CurrencyExchangeReader() {

    }

    public static void main(String[] args) throws IOException {

        DateHashMap<Date, Float> data = null;
        CurrencyExchangeReader reader = new CurrencyExchangeReader();
        try {
            data = reader.convertCSVFileToHandledData(reader.defaultPath + "SEK_USD.csv");
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }

        Date date1 = new Date(2020,6,1);

        System.out.println(data.get(date1));
    }

    DateHashMap<Date, Float> convertCSVFileToHandledData(String path) throws IOException {
        DateHashMap<Date, Float> data = new DateHashMap<>();
        String line;
        BufferedReader br = new BufferedReader(new FileReader(path));
        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] values = line.split("[,/]");
            Date date = new Date(Integer.parseInt(values[0]),Integer.parseInt(values[1]),Integer.parseInt(values[2]));
            float rate = Float.parseFloat(values[3]);
            data.put(date, rate);
        }

        return data;
    }

}