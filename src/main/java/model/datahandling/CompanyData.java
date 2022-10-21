package model.datahandling;

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
            put("currency", "USD");
        }});
        put("AMD", new HashMap<>() {{
            put("name", "Advanced Micro Devices, Inc.");
            put("filename", "HistoricalData_AMD.csv");
            put("currency", "USD");
        }});
        put("AMZN", new HashMap<>() {{
            put("name", "Amazon.com, Inc.");
            put("filename", "HistoricalData_AMZN.csv");
            put("currency", "USD");
        }});
        put("CSCO", new HashMap<>() {{
            put("name", "Cisco Systems, Inc.");
            put("filename", "HistoricalData_CSCO.csv");
            put("currency", "USD");
        }});
        put("META", new HashMap<>() {{
            put("name", "Meta Platforms, Inc.");
            put("filename", "HistoricalData_META.csv");
            put("currency", "USD");
        }});
        put("MSFT", new HashMap<>() {{
            put("name", "Microsoft Corporation");
            put("filename", "HistoricalData_MSFT.csv");
            put("currency", "USD");
        }});
        put("NFLX", new HashMap<>() {{
            put("name", "Netflix, Inc.");
            put("filename", "HistoricalData_NFLX.csv");
            put("currency", "USD");
        }});
        put("QCOM", new HashMap<>() {{
            put("name", "Qualcomm Incorporated");
            put("filename", "HistoricalData_QCOM.csv");
            put("currency", "USD");
        }});
        put("SBUX", new HashMap<>() {{
            put("name", "Starbucks Corporation");
            put("filename", "HistoricalData_SBUX.csv");
            put("currency", "USD");
        }});
        put("TSLA", new HashMap<>() {{
            put("name", "Tesla, Inc.");
            put("filename", "HistoricalData_TSLA.csv");
            put("currency", "USD");
        }});
        put("ADBE", new HashMap<>() {{
            put("name", "Adobe Inc.");
            put("filename", "HistoricalData_ADBE.csv");
            put("currency", "USD");
        }});
        put("BA", new HashMap<>() {{
            put("name", "Boeing Company");
            put("filename", "HistoricalData_BA.csv");
            put("currency", "USD");
        }});
        put("BEN", new HashMap<>() {{
            put("name", "Franklin Resources, Inc.");
            put("filename", "HistoricalData_BEN.csv");
            put("currency", "USD");
        }});
        put("BLK", new HashMap<>() {{
            put("name", "Black Rock, Inc.");
            put("filename", "HistoricalData_BLK.csv");
            put("currency", "USD");
        }});
        put("CRM", new HashMap<>() {{
            put("name", "Salesforce, Inc.");
            put("filename", "HistoricalData_CRM.csv");
            put("currency", "USD");
        }});
        put("HON", new HashMap<>() {{
            put("name", "Honeywell International Inc.");
            put("filename", "HistoricalData_HON.csv");
            put("currency", "USD");
        }});
        put("JNJ", new HashMap<>() {{
            put("name", "Johnson & Johnson");
            put("filename", "HistoricalData_JNJ.csv");
            put("currency", "USD");
        }});
        put("JPM", new HashMap<>() {{
            put("name", "JP Morgan Chase & Co.");
            put("filename", "HistoricalData_JPM.csv");
            put("currency", "USD");
        }});
        put("KO", new HashMap<>() {{
            put("name", "Coca-Cola Company");
            put("filename", "HistoricalData_KO.csv");
            put("currency", "USD");
        }});
        put("MCD", new HashMap<>() {{
            put("name", "McDonald's Corporation");
            put("filename", "HistoricalData_MCD.csv");
            put("currency", "USD");
        }});
        put("NKE", new HashMap<>() {{
            put("name", "Nike. Inc.");
            put("filename", "HistoricalData_NKE.csv");
            put("currency", "USD");
        }});
        put("NVDA", new HashMap<>() {{
            put("name", "NVIDIA Corporation");
            put("filename", "HistoricalData_NVDA.csv");
            put("currency", "USD");
        }});
        put("ORCL", new HashMap<>() {{
            put("name", "Oracle Corporation");
            put("filename", "HistoricalData_ORCL.csv");
            put("currency", "USD");
        }});
        put("PM", new HashMap<>() {{
            put("name", "Philip Morris International  Inc");
            put("filename", "HistoricalData_PM.csv");
            put("currency", "USD");
        }});
        put("RIO", new HashMap<>() {{
            put("name", "Rio Tinto Plc");
            put("filename", "HistoricalData_RIO.csv");
            put("currency", "USD");
        }});
        put("RTX", new HashMap<>() {{
            put("name", "Raytheon Technologies Corporation");
            put("filename", "HistoricalData_RTX.csv");
            put("currency", "USD");
        }});
        put("T", new HashMap<>() {{
            put("name", "AT&T Inc.");
            put("filename", "HistoricalData_T.csv");
            put("currency", "USD");
        }});
        put("TM", new HashMap<>() {{
            put("name", "Toyota Motors Corporation");
            put("filename", "HistoricalData_TM.csv");
            put("currency", "USD");
        }});
        put("TSM", new HashMap<>() {{
            put("name", "Taiwan Semiconductor Manufacturing");
            put("filename", "HistoricalData_TSM.csv");
            put("currency", "USD");
        }});
        put("XOM", new HashMap<>() {{
            put("name", "Exxon Mobil Corporation");
            put("filename", "HistoricalData_XOM.csv");
            put("currency", "USD");
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

    static String getCurrency(String mic) {
        return (String) companyData.get(mic).get("currency");
    }

}
