package model.util;

import model.graphmodel.graphalgorithms.GraphAlgorithms;

import java.io.Serializable;
import java.util.List;

/**
 * A serializable class representing a specific graph.
 */
public class GraphRepresentation implements Serializable {

    /**
     * A {@link List} of A {@link Date}s representing a desired interval. If empty represents the entire data span.
     */
    private List<Date> interval;

    /**
     * A {@link GraphAlgorithms} enum for the specific algorithm.
     */
    private GraphAlgorithms algorithm;

    /**
     * A {@link String} for the specified company MIC.
     */
    private String companyMIC;

    /**
     * A {@link CurrencyEnum} for the specified company trading currency.
     */
    private CurrencyConversionEnum preferredCurrency;

    /**
     * A constructor for the class.
     *
     * @param interval A {@link List} of {@link Date}s of a specified interval.
     * @param alg A {@link GraphAlgorithms} of a specified algorithm.
     * @param companyMIC A {@link String} of a specified company MIC.
     * @param preferredCurrency A {@link CurrencyConversionEnum} of the users preferred trading currency.
     */
    public GraphRepresentation(List<Date> interval, GraphAlgorithms alg, String companyMIC,
                               CurrencyConversionEnum preferredCurrency) {
        this.interval = interval;
        this.algorithm = alg;
        this.companyMIC = companyMIC;
        this.preferredCurrency = preferredCurrency;
    }

    // Getters

    /**
     * A getter method for the interval.
     *
     * @return A {@link List} of A {@link Date}s of the interval.
     */
    public List<Date> getInterval() {
        return interval;
    }

    /**
     * A getter method for the algorithm.
     *
     * @return A {@link GraphAlgorithms} of the algorithm.
     */
    public GraphAlgorithms getAlgorithm() {
        return algorithm;
    }

    /**
     * A getter method for the company MIC.
     *
     * @return A {@link String} of the company MIC.
     */
    public String getCompanyMIC() {
        return companyMIC;
    }

    /**
     * A getter method for the currency conversion to the users preferred trading currency.
     *
     * @return A {@link CurrencyConversionEnum} of the users preferred trading currency.
     */
    public CurrencyConversionEnum getConversionCurrency() {
        return preferredCurrency;
    }

}
