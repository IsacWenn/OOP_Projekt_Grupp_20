package model.graphmodel;

import model.datahandling.DayData;
import model.graphmodel.graphalgorithms.GraphAlgorithm;
import model.graphmodel.graphalgorithms.GraphAlgorithmCollection;
import model.graphmodel.keyfigures.KeyFigureCollection;
import model.util.CurrencyCollection;
import model.util.Date;

import java.util.*;

/**
 * GraphModel is a class for representing the data and graph-functionality of a visual graph object.
 *
 * @author Pontus
 * @author Isac
 * @author Carl
 */

public class GraphModel {

    /**
     * The private {@link Map} that holds the {@link Date} and its corresponding {@link Number}
     */
    private Map<Date, Number> values;

    /**
     * The {@link Map} that holds the raw company data from the GraphData class
     */
    Map<Date, DayData> data;

    /**
     * The {@link Map} that holds the company data adjusted for currency
     */
    private Map<Date, DayData> currencyAdjustedData;

    /**
     * The reference to the {@link GraphData} class
     */
    private GraphData graphData;

    /**
     * The reference to the {@link GraphComputer} class
     */
    private GraphComputer graphComputer;

    /**
     * The {@link String} that represents
     */
    private String companyMIC, graphName;

    private String currency;

    /**
     * A constructor for the class GraphModel that retrieves all available data for the given mic of a company
     *
     * @param mic A {@link String} which represents a company on the stock market
     */
    public GraphModel(String mic, String graphName){
        init(mic, graphName);
        this.data = graphData.getCompanyData(mic);
        updateAlgorithm(GraphAlgorithmCollection.getDefaultGraphAlgorithmName());
        update();
    }

    /**
     * A constructor for the class GraphModel that retrieves all available data for the given mic of a company
     *
     * @param mic A {@link String} which represents a company on the stock market
     * @param graphName A {@link String} for the name of the {@link GraphModel}
     * @param from A {@link Date} being the first date of the time interval of the {@link GraphModel}
     * @param to A {@link Date} being the last date of the time interval of the {@link GraphModel}
     * @param graphAlg A {@link String} representing a {@link GraphAlgorithm}
     * @param currency A {@link String} representing a currency
     */
    public GraphModel(String mic, String graphName, Date from, Date to, String graphAlg, String currency){
        init(mic, graphName);
        this.data = graphData.getCompanyData(mic, from, to);
        updateAlgorithm(graphAlg);
        updateCurrency(currency);
    }

    /**
     * A constructor for the class GraphModel that retrieves data for a given interval of {@link Date}
     *
     * @param mic a {@link String} representing a company's mic
     * @param from a {@link Date} for the start of the interval
     * @param to a {@link Date} for the end of the interval
     */
    public GraphModel(String mic, String graphName, Date from, Date to) {
        init(mic, graphName);
        this.data = graphData.getCompanyData(mic, from, to);
        updateAlgorithm(getGraphAlgorithmNames().get(0));
        update();
    }

    /**
     * A method that initializes the private variables of this class, used by every constructor in this class
     *
     *
     */
    private void init(String mic, String graphName) {
        this.graphComputer = new GraphComputer();
        this.graphData = new GraphData();
        this.values = new HashMap<>();
        this.companyMIC = mic;
        this.graphName = graphName;
    }

    /**
     * A method that sends a call to {@link GraphComputer} to calculate the data with its current {@link GraphAlgorithm}
     * and data
     */
    private void update() {
        if (this.currency == null) {
            this.values = this.graphComputer.calculateValues(data);
        } else {
            this.values = this.graphComputer.calculateValues(currencyAdjustedData);
        }
    }

    /**
     *A method that updates the current graphAlgorithm function the GraphModel is using.
     *
     * @param graphAlg a {@link String} that the {@link GraphComputer} will use for its calculations.
     */
    public void updateAlgorithm(String graphAlg) {
        this.graphComputer.setAlgorithm(GraphAlgorithmCollection.getGraphAlgorithm(graphAlg));
        update();
    }

    /**
     * A method that updates the time interval for the data of the current company.
     *
     * @param from a {@link Date} that is the first day of the new time interval.
     * @param to a {@link Date} that is the last day of the new time interval.
     */
    public void updateTimeInterval(Date from, Date to) {
        this.data = graphData.getCompanyData(companyMIC, from, to);
        if (currency != null)
            updateCurrencyAdjustedData();
        else
            update();
    }

    /**
     * A method for updating the {@link GraphModel#currency} to the provided {@link String}.
     *
     * @param toCurrency is a {@link String} representing the currency the method should adjust for.
     */
    public void updateCurrency(String toCurrency) {
        this.currency = toCurrency;
        updateCurrencyAdjustedData();
    }

    /**
     * A method for updating the {@link GraphModel#currencyAdjustedData} to the latest {@link GraphModel#data} adjusted
     * for the currency in {@link GraphModel#currency}.
     */
    private void updateCurrencyAdjustedData() {
        Map<Date,Double> from = graphData.getNativeCurrencyData(companyMIC, data.keySet());
        Map<Date,Double> to = graphData.getCurrencyData(this.currency, data.keySet());
        this.currencyAdjustedData = graphComputer.calculateCurrency(from, to, data);
        update();
    }

    /**
     * A method for retrieving the {@link GraphModel#graphName}
     *
     * @return The name as a {@link String}
     */
    public String getName() {
        return graphName;
    }

    /**
     * A getter method for the {@link Map} with {@link Date} and {@link Number} values in GraphModel
     *
     * @return the {@link Map} containing the values
     */
    public Map<Date, Number> getValues() {
        return Map.copyOf(this.values);
    }

    /**
     * A method that tells the {@link GraphComputer} to calculate the given key figure with the relevant data in
     * GraphModel
     *
     * @param keyFigure a {@link String} that represents a {@link KeyFigureCollection}
     * @return a {@link Double} which is the calculated value of that key figure
     */
    public double getKeyFigureValue(String keyFigure){
        if(currencyAdjustedData!=null) {
            return this.graphComputer.calculateKeyFigure(KeyFigureCollection.getKeyFigure(keyFigure), currencyAdjustedData);
        }
        else{
            return this.graphComputer.calculateKeyFigure(KeyFigureCollection.getKeyFigure(keyFigure), data);
        }
    }

    static public List<String> getKeyFigureNames(){
        return KeyFigureCollection.getKeyFigureNames();
    }

    static public List<String> getGraphAlgorithmNames(){
        return GraphAlgorithmCollection.getGraphAlgorithmNames();
    }

    static public List<String> getCurrencyNames(){
        return CurrencyCollection.getCurrencyNames();
    }

    /**
     * A method that sorts the {@link GraphModel#values} and returns it in a more manageable form
     *
     * @param numDataPoints an {@link Integer} representing the number of data point you want the graph
     * @return a {@link LinkedHashMap}  containing data that is reduced and sorted
     */
    public Map<Date, Number> getSortedAndReducedData(int numDataPoints) {
        LinkedHashMap<Date, Number> orderedMap = new LinkedHashMap<>();
        List<Date> orderedDates = Date.sortDates(this.getValues().keySet());
        reduceDataPoints(numDataPoints, orderedMap, orderedDates);
        return orderedMap;
    }

    private void reduceDataPoints(int numDataPoints, LinkedHashMap<Date, Number> orderedMap, List<Date> orderedDates) {
        double daysInterval = orderedDates.size(), stepAmount, dIndex = 0;
        int index;

        stepAmount = Math.max(1, daysInterval/ numDataPoints);
        while (orderedMap.size() < Math.min(numDataPoints, daysInterval)) {
            index = (int) Math.round(dIndex);
            Date currentDate = orderedDates.get(index);
            Number val = this.getValues().get(currentDate);
            orderedMap.put(currentDate, val);
            dIndex += stepAmount;
        }
    }

}

