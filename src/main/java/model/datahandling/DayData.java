package model.datahandling;

import java.util.HashMap;

/**
 * A class that contains the data of a certain date in the database.
 *
 * @author Isac
 * @author Carl
 */
public class DayData {

    /**
     * A final {@link Integer} that contains the volume of a stock.
     */
    private final int volume;

    /**
     * A final {@link Double} that contains the opening price of a stock.
     */
    private final double open;

    /**
     * A final {@link Double} that contains the closing price of a stock.
     */
    private final double closed;

    /**
     * A final {@link Double} that contains the highest price of a stock.
     */
    private final double high;

    /**
     * A final {@link Double} that contains the lowest price of a stock.
     */
    private final double low;

    /**
     * A constructor that takes the values of its attributes as parameters.
     *
     * @param volume An {@link Integer} representing the volume of a stock.
     * @param open A {@link Double} representing the opening price of a stock.
     * @param closed A {@link Double} representing the closing price of a stock.
     * @param high A {@link Double} representing the highest price of a stock.
     * @param low A {@link Double} representing the lowest price of a stock.
     */
    DayData(int volume, double open, double closed, double high, double low){
        this.volume = volume;
        this.open = open;
        this.closed = closed;
        this.high = high;
        this.low = low;
    }

    /**
     * A constructor that takes the values of its attributes as a HashMap parameter.
     *
     * @param data A {@link HashMap} containing the different values of a {@link DayData}s attributes.
     */
    DayData(HashMap<String, Object> data) {
        this.volume = (int) data.get("volume");
        this.open = (double) data.get("open");
        this.closed = (double) data.get("closed");
        this.low = (double) data.get("low");
        this.high = (double) data.get("high");
    }

    /**
     * A getter method for the volume attribute.
     *
     * @return An {@link Integer} representing the volume of a stock.
     */
    public int getVolume(){
        return volume;
    }

    /**
     * A getter method for the {@link DayData#open} attribute
     *
     * @return A {@link Double} representing the opening price of a stock.
     */
    public double getOpen(){
        return open;
    }

    /**
     * A getter method for the {@link DayData#closed} attribute
     *
     * @return A {@link Double} representing the closing price of a stock.
     */
    public double getClosed(){
        return closed;
    }

    /**
     * A getter method for the {@link DayData#high} attribute
     *
     * @return A {@link Double} representing the highest price of a stock.
     */
    public double getHigh(){
        return high;
    }

    /**
     * A getter method for the {@link DayData#low} attribute
     *
     * @return A {@link Double} representing the lowest price of a stock.
     */
    public double getLow(){
        return low;
    }

    /**
     * A method returning a {@link String} representation of the values of the instance's attributes.
     *
     * @return A {@link String} representation of the values of the instance's attributes.
     */
    @Override
    public String toString() {
        return "DayData{" +
                "volume=" + volume +
                ", open=" + open +
                ", closed=" + closed +
                ", high=" + high +
                ", low=" + low +
                '}';
    }
}
