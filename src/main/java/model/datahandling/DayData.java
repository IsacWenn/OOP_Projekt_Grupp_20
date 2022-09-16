package model.datahandling;

public class DayData {

    private String date;
    private int volume;
    private double open;
    private double closed;
    private double high;
    private double low;

    DayData(String date, int volume, double open, double closed, double high, double low){
        this.date = date;
        this.volume = volume;
        this.open = open;
        this.closed = closed;
        this.high = high;
        this.low = low;
    }
    
    String getDate(){
        return date;
    }

    int getVolume(){
        return volume;
    }

    double getOpen(){
        return open;
    }

    double getClosed(){
        return closed;
    }

    double getHigh(){
        return high;
    }

    double getLow(){
        return low;
    }

}
