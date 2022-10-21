package model.util;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A serializable class representing a specific graph.
 *
 * @author Isac
 */
public class GraphRepresentation implements Serializable {

    /**
     * A {@link List} of A {@link Date}s representing a desired interval. If empty represents the entire data span.
     */
    private List<Date> interval;

    /**
     * A {@link String} for the specific algorithm.
     */
    private String algorithm;

    /**
     * A {@link List} of {@link String} of the specified company MICs.
     */
    private List<String> companyMICs;

    /**
     * A {@link String} for the specified company trading currency.
     */
    private String preferredCurrency;

    /**
     * A constructor for the class.
     *
     * @param interval A {@link List} of {@link Date}s of a specified interval.
     * @param alg A {@link String} of a specified algorithm.
     * @param companyMICs A {@link List} of {@link String}s containing the specified company MICs.
     * @param preferredCurrency A {@link String} of the users preferred trading currency.
     */
    public GraphRepresentation(List<Date> interval, String alg, List<String> companyMICs,
                               String preferredCurrency) {
        this.interval = interval;
        this.algorithm = alg;
        this.companyMICs = companyMICs;
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
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * A getter method for the company MICs.
     *
     * @return A {@link List} of {@link String}s of the company MICs.
     */
    public List<String> getCompanyMICs() {
        return companyMICs;
    }

    /**
     * A getter method for the currency conversion to the users preferred trading currency.
     *
     * @return A {@link String} of the users preferred trading currency.
     */
    public String getConversionCurrency() {
        return preferredCurrency;
    }

    /**
     * An implementation of the .equals() method of Java.
     *
     * @param o the {@link Object} to compare with.
     * @return a {@link Boolean} value of that comparison.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphRepresentation that = (GraphRepresentation) o;
        return Objects.equals(interval, that.interval) && Objects.equals(algorithm, that.algorithm)
                && Objects.equals(companyMICs, that.companyMICs) && Objects.equals(preferredCurrency, that.preferredCurrency);
    }

    /**
     * An implementation of the .hashCode method that uses the instance attributes to create a unique hash.
     *
     * @return An {@link Integer} of that hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(interval, algorithm, companyMICs, preferredCurrency);
    }

    /**
     * An implementation of toString that returns a string representation of the object values.
     *
     * @return A {@link String} representation of the objects values.
     */
    @Override
    public String toString() {
        return "GraphRepresentation{" +
                "interval=" + interval +
                ", algorithm=" + algorithm +
                ", companyMIC='" + companyMICs +
                ", preferredCurrency=" + preferredCurrency +
                '}';
    }
}
