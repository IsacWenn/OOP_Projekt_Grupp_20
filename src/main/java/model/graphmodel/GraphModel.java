package model.graphmodel;


import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import model.graphmodel.graphalgorithms.GraphAlgorithm;
import model.graphmodel.graphalgorithms.GraphAlgorithms;
import model.util.Date;

import java.util.List;
import java.util.Map;

/**
 * GraphModel is a class for representing the data and graph-functionality of a visual graph object.
 *
 * @author Pontus
 * @author Isac
 */

public class GraphModel {

    /**
     * The private {@link Map} that holds the {@link Date} and its corresponding {@link Number}
     */
    private Map<Date, Number> values;

    /**
     * The {@link Map} that holds the raw company data from the GraphData class, a {@link Date}
     */
    Map<Date, DayData> data;

    /**
     * The reference to the {@link GraphData} class
     */
    private GraphData graphData;

    /**
     * The reference to the {@link GraphComputer} class
     */
    private GraphComputer graphComputer;


    /**
     * A constructor for the class GraphModel that retrieves all available data for the given mic of a company
     *
     * @param mic a {@link String} which represents a company on the stock market
     */
    public GraphModel(String mic){
        init();
        this.data = graphData.getCompanyData(mic);
    }

    /**
     * A constructor for the class GraphModel that retrieves data for a given interval of {@link Date}
     *
     * @param mic a {@link String} representing a company's mic
     * @param from a {@link Date} for the start of the interval
     * @param to a {@link Date} for the end of the interval
     */
    public GraphModel(String mic, Date from, Date to) {
        init();
        this.data = graphData.getCompanyData(mic, from, to);
    }

    /**
     * A constructor for the class GraphModel that retrieves data for a given list of Dates
     *
     * @param mic a {@link String} representing a company's mic
     * @param dates a {@link List} of {@link Date}
     */
    public GraphModel(String mic, List<Date> dates){
        init();
        this.data = graphData.getCompanyData(mic ,dates);
    }

    /**
     * A method that initializes the private variables of this class, used by every constructor in this class
     */
    private void init() {
        this.graphComputer = new GraphComputer();
        this.graphData = new GraphData();
        this.values = new DateHashMap<>();
    }

    /**
     * A method that sends a call to {@link GraphComputer} to calculate the data with its current {@link GraphAlgorithm}
     * and data
     */
    public void update() {
        this.values = this.graphComputer.updateValues(data);
    }

    /**
     *A method that updates the current graphable function the GraphModel is using
     *
     * @param graphable
     */
    public void updateAlgorithm(GraphAlgorithms graphable) {
        this.graphComputer.setAlgorithm(graphable);
    }


    /**
     *A method that changes the currency the data is using
     *
     * @param currency
     */
    public void changeCurrency(String currency){
        this.graphComputer.calculateCurrency(graphData.getCurrencyData(currency), data);
    }

    /**
     * A getter method for the {@link Map} with {@link Date} and {@link Number} values in GraphModel
     *
     * @return the {@link Map} containing the values
     */
    public Map<Date, Number> getValues() {
        return this.values;
    }

    public static void main(String[] args) {

    }
}

