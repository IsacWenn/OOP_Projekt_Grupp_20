package model.datahandling;


import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCompanyData {

    @Test
    public void getMICsShouldReturnListOfDifferentCompanyMICs() {
        List<String> mics = CompanyData.getMICs();
        assertEquals(10, mics.size());
        assertTrue(mics.contains("AAPL"));
    }

    @Test
    public void getCurrencyShouldReturnCorrectCurrencyEnum() {
        assertEquals(CurrencyEnum.USD, CompanyData.getCurrency("AAPL"));
    }

    @Test
    public void getCompanyNamesShouldReturnListOfCompanyNames() {
        List<String> names = CompanyData.getCompanyNames();
        assertEquals(10, names.size());
        assertTrue(names.contains("Amazon.com, Inc."));
    }

    @Test
    public void getFileNameShouldReturnCorrectFileName() {
        assertEquals("HistoricalData_MSFT.csv", CompanyData.getFileName("MSFT"));
    }

}
