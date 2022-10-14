package model.datahandling;

import model.util.CurrencyEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that holds a {@link Map} containing the essential data for each of the companies in our Database.
 *
 * @author Isac
 */
public class CompanyData {

    /**
     * A {@link HashMap} that contains the essential data for the different companies in our Database.
     */
    private static final Map<String, HashMap<String, Object>> companyData = new HashMap<>() {{
        put("AAPL", new HashMap<>() {{
            put("name", "Apple, Inc.");
            put("filename", "HistoricalData_AAPL.csv");
            put("currency", CurrencyEnum.USD);
        }});
        put("AMD", new HashMap<>() {{
            put("name", "Advanced Micro Devices, Inc.");
            put("filename", "HistoricalData_AMD.csv");
            put("currency", CurrencyEnum.USD);
        }});
        put("AMZN", new HashMap<>() {{
            put("name", "Amazon.com, Inc.");
            put("filename", "HistoricalData_AMZN.csv");
            put("currency", CurrencyEnum.USD);
        }});
        put("CSCO", new HashMap<>() {{
            put("name", "Cisco Systems, Inc.");
            put("filename", "HistoricalData_CSCO.csv");
            put("currency", CurrencyEnum.USD);
        }});
        put("META", new HashMap<>() {{
            put("name", "Meta Platforms, Inc.");
            put("filename", "HistoricalData_META.csv");
            put("currency", CurrencyEnum.USD);
        }});
        put("MSFT", new HashMap<>() {{
            put("name", "Microsoft Corporation");
            put("filename", "HistoricalData_MSFT.csv");
            put("currency", CurrencyEnum.USD);
        }});
        put("NFLX", new HashMap<>() {{
            put("name", "Netflix, Inc.");
            put("filename", "HistoricalData_NFLX.csv");
            put("currency", CurrencyEnum.USD);
        }});
        put("QCOM", new HashMap<>() {{
            put("name", "Qualcomm Incorporated");
            put("filename", "HistoricalData_QCOM.csv");
            put("currency", CurrencyEnum.USD);
        }});
        put("SBUX", new HashMap<>() {{
            put("name", "Starbucks Corporation");
            put("filename", "HistoricalData_SBUX.csv");
            put("currency", CurrencyEnum.USD);
        }});
        put("TSLA", new HashMap<>() {{
            put("name", "Tesla, Inc.");
            put("filename", "HistoricalData_TSLA.csv");
            put("currency", CurrencyEnum.USD);
        }});
    }};

    /**
     * A method that returns a {@link String} containing the respective filename of the company mic given as a parameter.
     *
     * @param mic A {@link String} containing the MIC (Market Identifier Code) of a company.
     * @return A {@link String} containing the filename of the given company.
     */
    static String getFileName(String mic) {  /* mic = Market Identifier Code */
        return ((String) companyData.get(mic).get("filename"));
    }

    /**
     * A method that returns a {@link List} of {@link String}s of the different companies.
     *
     * @return A {@link List} of {@link String}s of the company names.
     */
    static List<String> getCompanyNames() {
        ArrayList<String> names = new ArrayList<>();
        for (String mic : companyData.keySet())
            names.add((String) companyData.get(mic).get("name"));
        return names;
    }

    /**
     * A getter method for a specific company name.
     *
     * @param mic A {@link String} of the specified company MIC.
     * @return A {@link String} of that company's name.
     */
    static String getCompanyName(String mic) {
        try {
            return (String) companyData.get(mic).get("name");
        } catch (NullPointerException e) {
            return "";
        }
    }

    /**
     * A method that returns a {@link List} of {@link String}s of the different company MIC:s
     *
     * @return A {@link List} of {@link String}s of tha different company MIC:s
     */
    static List<String> getMICs() {
        return new ArrayList<>(companyData.keySet());
    }

    static CurrencyEnum getCurrency(String mic) {
        return (CurrencyEnum) companyData.get(mic).get("currency");
    }

}
