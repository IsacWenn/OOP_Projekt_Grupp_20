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
     * The starting {@link Date} for a graph.
     */
    private Date from;

    /**
     * The end {@link Date} for a graph.
     */
    private Date to;

    /**
     * A {@link String} for the specific algorithm.
     */
    private String algorithm;

    /**
     * A {@link String} of the specified company MICs.
     */
    private String companyMIC;

    /**
     * A {@link String} for the specified company trading currency.
     */
    private String preferredCurrency;

    /**
     * A constructor for the class.
     *
     * @param from The first {@link Date} in the time interval.
     * @param to The last {@link Date} in the time interval.
     * @param alg A {@link String} representing an algorithm.
     * @param companyMIC The company´s identifier code as a {@link String}.
     * @param preferredCurrency A {@link String} representing a currency.
     */
    public GraphRepresentation(Date from, Date to, String alg, String companyMIC,
                               String preferredCurrency) {
        this.from = from;
        this.to = to;
        this.algorithm = alg;
        this.companyMIC = companyMIC;
        this.preferredCurrency = preferredCurrency;
    }

    // Getters

    /**
     * A getter method for the starting date
     *
     * @return the starting {@link Date} of the graph.
     */
    public Date getStartingDate() { return from; }

    /**
     * A getter method for the end date.
     *
     * @return the end {@link Date} of the graph.
     */
    public Date getEndDate() { return to; }

    /**
     * A getter method for the algorithm.
     *
     * @return The name of an algorithm as a {@link String}.
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * A getter method for the company MIC.
     *
     * @return A {@link String}s of the company MIC.
     */
    public String getCompanyMIC() {
        return companyMIC;
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
        return from.equals(that.from) && to.equals(that.to) && algorithm.equals(that.algorithm)
                && companyMIC.equals(that.companyMIC) && preferredCurrency.equals(that.preferredCurrency);
    }

    /**
     * An implementation of the .hashCode method that uses the instance attributes to create a unique hash.
     *
     * @return An {@link Integer} of that hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(from, to, algorithm, companyMIC, preferredCurrency);
    }

    /**
     * An implementation of toString that returns a string representation of the object values.
     *
     * @return A {@link String} representation of the objects values.
     */
    @Override
    public String toString() {
        return "GraphRepresentation{" +
                "from=" + from +
                ", to=" + to +
                ", algorithm=" + algorithm +
                ", companyMIC=" + companyMIC +
                ", preferredCurrency=" + preferredCurrency +
                '}';
    }
}
