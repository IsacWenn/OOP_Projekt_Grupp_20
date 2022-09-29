package model.graphmodel;

import model.Date;
import model.datahandling.DateHashMap;
import model.datahandling.DayData;
import model.graphmodel.graphablefunctions.DailyClosingPrice;
import model.graphmodel.graphablefunctions.Graphable;
import model.graphmodel.graphablefunctions.DailyChange;

/**
 * GraphComputer is the class that uses the strategy pattern to change what class that implements {@link Graphable}
 * it should use
 *
 * @author Pontus
 * @author Carl
 * @author Isac
 */
public class GraphComputer {

    /**
     * A private {@link Graphable} variable
     */
    private Graphable graphable;

    /**
     * A constructor for the GraphComputer class that sets graphable to {@link DailyClosingPrice}
     */
    public GraphComputer(){
        this.graphable = new DailyClosingPrice();
    }

    /**
     * A method that sets the current graphable to the one called with the method
     *
     * @param graphable a class that implements {@link Graphable}
     */
    void setAlgorithm(Graphable graphable) {
        this.graphable = graphable;
    }

    public void calculateCurrency(DateHashMap<Date, Double> currency,
                                  DateHashMap<Date, Number> data) {
        for (Date date : data.keySet()) {
            double processedDouble = (double) data.get(date);
            processedDouble *= currency.get(date);
            data.put(date, processedDouble);
        }
    }

    public DateHashMap<Date, Number> updateValues(DateHashMap<Date, DayData> data) {
        return graphable.calculate(data);
    }

}
