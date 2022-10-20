package model.util;

import model.graphmodel.graphalgorithms.*;

import java.util.*;

public class CurrencyCollection {
    /**
     * A static {@link String} containing the name of the currenct that should be selected as default.
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
     * A method for retrieving the {@link String} for all {@link GraphAlgorithm} in {@link CurrencyCollection#currencies}.
     *
     * @return A {@link List} of {@link String} for each currency in {@link CurrencyCollection#currencies}.
     */
    public static List<String> getCurrencyNames() {
        return List.copyOf(currencies);
    }

    public static String getDefaultCurrencyName() {
        return defaultCurrencyName;
    }
}
