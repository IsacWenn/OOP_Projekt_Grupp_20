package model.datahandling;

import model.util.Date;

import java.io.IOException;
import java.util.Map;

/**
 * An interface used when retrieving currency exchange data for the application.
 *
 * @author Isac
 * Uses: Date.
 * Used By: DataHandling, CurrencyExchangeReader.
 */
public interface CurrencyDataRetriever {

    /**
     * A method used to retrieve currency exchange data for two specified currencies.
     *
     * @param currencyExchangeName the name of the currencies in the format: FROM_TO
     * @return a {@link Map} of {@link Date}s and {@link Double} containing the conversion rates.
     * @throws IOException if the conversion rates can not be found.
     */
    Map<Date, Double> retrieveData(String currencyExchangeName) throws IOException;

}
