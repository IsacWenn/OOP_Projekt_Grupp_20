package model.util;

import java.util.*;

/**
 * A collection of the different currencies currently available to the application.
 *
 * Uses: -
 * Used By: GraphData, GraphModel.
 */
public class CurrencyCollection {
    /**
     * A static {@link String} containing the name of the currency that should be selected as default.
     */
    private static final String defaultCurrencyName = "USD";

    /**
     * A static {@link List} used to store a collection of currencies.
     */
    private static final List<String> currencies = new ArrayList<>();

    static {
        currencies.add(defaultCurrencyName);
        currencies.add("SEK");
        currencies.add("EUR");
        currencies.add("GBP");
        currencies.add("CNY");
        currencies.add("PLN");
        currencies.add("JPY");
        currencies.add("INR");
        currencies.add("CAD");
    }

    /**
     * A method for retrieving a {@link List} of names of {@link CurrencyCollection#currencies} in alphabetic order
     * except the {@link CurrencyCollection#defaultCurrencyName} that should be the first element in the list.
     *
     * @return A {@link List} of {@link String} for each currency in {@link CurrencyCollection#currencies}.
     */
    public static List<String> getCurrencyNames() {
        List<String> returnList = new ArrayList<>();
        List<String> namesInOrder = new ArrayList<>(Set.copyOf(currencies));
        Collections.sort(namesInOrder);

        returnList.add(defaultCurrencyName);
        for (String algoName : namesInOrder) {
            if (!Objects.equals(algoName, defaultCurrencyName))
                returnList.add(algoName);
        }
        return returnList;
    }


    public static String getDefaultCurrencyName() {
        return defaultCurrencyName;
    }
}
