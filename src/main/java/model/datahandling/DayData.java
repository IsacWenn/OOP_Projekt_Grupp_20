package model.datahandling;

import java.util.HashMap;

public class DayData {
    private final int volume;
    private final double open;
    private final double closed;
    private final double high;
    private final double low;

    DayData(int volume, double open, double closed, double high, double low){
        this.volume = volume;
        this.open = open;
        this.closed = closed;
        this.high = high;
        this.low = low;
    }

    DayData(HashMap<String, Object> data) {
        this.volume = (int) data.get("volume");
        this.open = (double) data.get("open");
        this.closed = (double) data.get("closed");
        this.low = (double) data.get("low");
        this.high = (double) data.get("high");
    }
    public int getVolume(){
        return volume;
    }

    public double getOpen(){
        return open;
    }

    public double getClosed(){
        return closed;
    }

    public double getHigh(){
        return high;
    }

    public double getLow(){
        return low;
    }

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
