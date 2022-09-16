package model.datahandling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CompanyData {

    private static final HashMap<String, HashMap<String, Object>> companyData = new HashMap<>(){{
        put("AAPL", new HashMap<>(){{
            put("name", "Apple, Inc.");
            put("filename", "HistoricalData_AAPL.csv");
        }});
        put("AMD", new HashMap<>(){{
            put("name", "Advanced Micro Devices, Inc.");
            put("filename", "HistoricalData_AMD.csv");
        }});
        put("AMZN", new HashMap<>(){{
            put("name", "Amazon.com, Inc.");
            put("filename", "HistoricalData_AMZN.csv");
        }});
        put("CSCO", new HashMap<>(){{
            put("name", "Cisco Systems, Inc.");
            put("filename", "HistoricalData_CSCO.csv");
        }});
        put("META", new HashMap<>(){{
            put("name", "Meta Platforms, Inc.");
            put("filename", "HistoricalData_META.csv");
        }});
        put("MSFT", new HashMap<>(){{
            put("name", "Microsoft Corporation");
            put("filename", "HistoricalData_MSFT.csv");
        }});
        put("NFLX", new HashMap<>(){{
            put("name", "Netflix, Inc.");
            put("filename", "HistoricalData_NFLX.csv");
        }});
        put("QCOM", new HashMap<>(){{
            put("name", "Qualcomm Incorporated");
            put("filename", "HistoricalData_QCOM.csv");
        }});
        put("SBUX", new HashMap<>(){{
            put("name", "Starbucks Corporation");
            put("filename", "HistoricalData_SBUX.csv");
        }});
        put("TSLA", new HashMap<>(){{
            put("name", "Tesla, Inc.");
            put("filename", "HistoricalData_TSLA.csv");
        }});
    }};

    static String getFileName(String mic) {  /* mic = Market Identifier Code */
        return ((String) companyData.get(mic).get("filename"));
    }

    static List<String> getCompanyNames() {
        ArrayList<String> names =  new ArrayList<>();
        for (String mic : companyData.keySet())
            names.add((String) companyData.get(mic).get("name"));
        return names;
    }

    static List<String> getMICs() {
        return new ArrayList<>(companyData.keySet());
    }

}
