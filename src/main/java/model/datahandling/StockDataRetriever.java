package model.datahandling;

import model.util.Date;

import java.io.IOException;
import java.util.Map;

/**
 * An interface used when retrieving stock data for the application.
 *
 * @author Isac
 * Uses: Date
 * Used By: DataHandling, StockExchangeReader.
 */
public interface StockDataRetriever {

    /**
     * A method used to retrieve stock data for a specific company.
     *
     * @param mic the MIC of the specified company.
     * @return a {@link Map} of {@link Date}s and {@link DayData} containing the stock data of a company.
     * @throws IOException if the company MIC does not exist or can not be found.
     */
    Map<Date, DayData> retrieveData(String mic) throws IOException;

}
