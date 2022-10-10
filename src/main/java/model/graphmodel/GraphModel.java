package model.graphmodel;

import model.datahandling.DayData;
import model.graphmodel.graphalgorithms.GraphAlgorithm;
import model.graphmodel.graphalgorithms.GraphAlgorithmCollection;
import model.graphmodel.graphalgorithms.GraphAlgorithms;
import model.util.CurrencyEnum;
import model.util.Date;

import java.io.IOException;
import java.util.HashMap;
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

    private Map<Date, DayData> currencyAdjustedData;
    /**
     * The reference to the {@link GraphData} class
     */
    private GraphData graphData;

    /**
     * The reference to the {@link GraphComputer} class
     */
    private GraphComputer graphComputer;

    private String companyMic;

    /**
     * A constructor for the class GraphModel that retrieves all available data for the given mic of a company
     *
     * @param mic a {@link String} which represents a company on the stock market
     */
    public GraphModel(String mic){
        init(mic);
        this.data = graphData.getCompanyData(mic);
        currencyAdjustedData = data;
        update();
    }

    public GraphModel(String mic, GraphAlgorithms graphAlgorithms){
        init(mic);
        this.data = graphData.getCompanyData(mic);
        updateAlgorithm(graphAlgorithms);
        currencyAdjustedData = data;
        update();
    }

    /**
     * A constructor for the class GraphModel that retrieves data for a given interval of {@link Date}
     *
     * @param mic a {@link String} representing a company's mic
     * @param from a {@link Date} for the start of the interval
     * @param to a {@link Date} for the end of the interval
     */

    public GraphModel(String mic, Date from, Date to) {
        init(mic);
        this.data = graphData.getCompanyData(mic, from, to);
        currencyAdjustedData = data;
        update();
    }


    /**
     * A constructor for the class GraphModel that retrieves data for a given list of Dates
     *
     * @param mic a {@link String} representing a company's mic
     * @param dates a {@link List} of {@link Date}
     */
    @Deprecated
    public GraphModel(String mic, List<Date> dates){
        init(mic);
        this.data = graphData.getCompanyData(mic ,dates);
    }

    /**
     * A method that initializes the private variables of this class, used by every constructor in this class
     */
    private void init(String mic) {
        this.graphComputer = new GraphComputer();
        this.graphData = new GraphData();
        this.values = new HashMap<>();
        this.companyMic = mic;
        GraphAlgorithmCollection.init();
    }

    /**
     * A method that sends a call to {@link GraphComputer} to calculate the data with its current {@link GraphAlgorithm}
     * and data
     */

    private void update(){
        this.values = this.graphComputer.updateValues(currencyAdjustedData);
    }

    private void updateKeyFigures(){
        
    }

    /**
     *A method that updates the current graphAlgorithm function the GraphModel is using.
     *
     * @param graphAlgorithms a {@link GraphAlgorithm} that the {@link GraphComputer} will use for its calculations.
     */
    public void updateAlgorithm(GraphAlgorithms graphAlgorithms) {
        this.graphComputer.setAlgorithm(graphAlgorithms);
        update();
    }

    /**
     * A method that updates the time interval for the data of the current company.
     *
     * @param from a {@link Date} that is the first day of the new time interval.
     * @param to a {@link Date} that is the last day of the new time interval.
     */
    public void updateTimeInterval(Date from, Date to) {
        this.data = graphData.getCompanyData(this.companyMic, from, to);
        update();
    }

    /**
     *
     * @param toCurrency
     */
    public void changeCurrency(CurrencyEnum toCurrency){
        Map<Date,Double> from = graphData.getNativeCurrencyData(companyMic, data.keySet());
        Map<Date,Double> to = graphData.getCurrencyData(toCurrency,data.keySet());
        this.currencyAdjustedData = graphComputer.calculateCurrency(from, to, data);
        update();
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

        try {
            Date date1 = new Date(2022,7,1);
            Date date2 = new Date(2022,8,1);
            GraphModel graphModel = new GraphModel("AAPL");
            graphModel.updateTimeInterval(date1, date2);
            graphModel.changeCurrency(CurrencyEnum.USD);
            System.out.println(graphModel.getValues());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

