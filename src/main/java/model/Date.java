package model;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;


public class Date {

    private final int year;
    private final int month;
    private final int day;
    private static HashMap<Integer, Integer> daysInMonth = new HashMap<>() {{
        put(1, 31);
        put(2, 29);
        put(3, 31);
        put(4, 30);
        put(5, 31);
        put(6, 30);
        put(7, 31);
        put(8, 31);
        put(9, 30);
        put(10, 31);
        put(11, 30);
        put(12, 31);
    }};


    public static void main(String[] args) {
        try {
            Date date1 = new Date(2000,  3, 5);
            Date date2 = new Date(2001, 3, 5);
            Date date3 = new Date(2001, 6, 5);
            Date date4 = new Date(2001, 3, 6);
            Date date5 = new Date(2001, 3, 5);

            System.out.println(date1.isBefore(date2));
            System.out.println(date2.isBefore(date3));
            System.out.println(date2.isBefore(date4));
            System.out.println(date2.isBeforeOrEqual(date5));
            System.out.println(date5.isAfter(date1));
            System.out.println(!(date1.isAfterOrEqual(date3)));
            System.out.println(date2.isEqualTo(date5));

            System.out.println(new Date("09/13/2022"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public Date(int year, int month, int day) throws IOException {
        validateDate(year, month, day);
        this.year = year;
        this.month = month;
        this.day = day;
    }

    Date(String date) throws IOException { /* For use when converting the american date strings in the NasDaq CSV-files */
        String[] dateValues = date.split("/");
        int year = Integer.parseInt(dateValues[2]);
        int month = Integer.parseInt(dateValues[0]);
        int day = Integer.parseInt(dateValues[1]);
        validateDate(year, month, day);  /* this() call must be first line in constructor... :/ */
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /* Getters */

    public int getDay() { return day; }

    public int getMonth() { return month; }

    public int getYear() { return year; }

    /* IO-methods */

    @Override
    public String toString() {
        return String.format("Date: %2d/%2d/%4d", day, month, year);
    }

    /* IO-Validation methods */
    private void validateDate(int year, int month, int day) throws IOException {
        if (!validYear(year))
            throw new IOException("Invalid Year in Date Initialization");
        else if (!validMonth(month))
            throw new IOException("Invalid Month in Date Initialization");
        else if (!validDay(month , day))
            throw new IOException("Invalid Day in Date Initialization");
    }

    private Boolean validYear() {
        return (1900 < this.year && this.year <= LocalDateTime.now().getYear());
    }

    private Boolean validYear(int year) {
        return (1900 < year && year <= LocalDateTime.now().getYear());
    }

    private Boolean validMonth() {
        return (0 < this.month && this.month <= 12);
    }
    private Boolean validMonth(int month) {
        return (0 < month && month <= 12);
    }

    private Boolean validDay() {
        return (0 < this.day && this.day <= daysInMonth.get(this.month));
    }
    private Boolean validDay(int month, int day) {
        return (0 < day && day <= daysInMonth.get(month));
    }

    /* Comparison methods */

    public Boolean isBefore(Date otherDate) {
        if (this.year > otherDate.getYear())
            return false;
        else if (this.year < otherDate.getYear())
            return true;

        if (this.month > otherDate.getMonth())
            return false;
        else if (this.month < otherDate.getMonth())
            return true;

        return this.day < otherDate.getDay();
    }

    public Boolean isBeforeOrEqual(Date otherDate) {
        if (this.year > otherDate.getYear())
            return false;
        else if (this.year < otherDate.getYear())
            return true;

        if (this.month > otherDate.getMonth())
            return false;
        else if (this.month < otherDate.getMonth())
            return true;

        return this.day <= otherDate.getDay();
    }

    public Boolean isAfter(Date otherDate) { return otherDate.isBefore(this); }

    public Boolean isAfterOrEqual(Date otherDate) { return otherDate.isBeforeOrEqual(this); }

    public Boolean isEqualTo(Date otherDate) {
        if (this.equals(otherDate))
            return true;
        return (this.year == otherDate.getYear() &&
                this.month == otherDate.getMonth() &&
                this.day == otherDate.getDay());
    }

}
