package model;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Date is a class used for representing a certain day of a year. It is used in our program primarily as a key in
 * {@link model.datahandling.DateHashMap} class in our data representations.
 *
 * @author Isac
 */
public class Date {

    /**
     * The private {@link Integer} that holds the year of a {@link Date}.
     */
    private final int year;
    /**
     * The private {@link Integer} that holds the month of a {@link Date}.
     */
    private final int month;
    /**
     * The private {@link Integer} that holds the day of a {@link Date}.
     */
    private final int day;
    /**
     * A private static {@link HashMap} using {@link Integer} as a parameterized key and mapped value.
     * Used for representing the number of days in a given month.
     */
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

            System.out.println(date4.isEqualTo(date5.nextDate()));
            System.out.println(date5.isEqualTo(date4.previousDate()));

            System.out.println(date1.plusDays(365));
            System.out.println(date1.minusDays(2));

            Date date6 = new Date(2022, 9, 19);
            System.out.println("List Interval");
            System.out.println(date6.listIntervalTo(new Date()));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * A constructor for class Date with separate parameters for each attribute.
     *
     * @param year an {@link Integer} for the year of a date.
     * @param month an {@link Integer} for the month of a date.
     * @param day an {@link Integer} for the day of a date.
     * @throws IOException if the parameters provided are not considered a valid date.
     */
    public Date(int year, int month, int day) throws IOException {
        validateDate(year, month, day);
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * A constructor for class Date taking a string in the date format mm/dd/yyyy. Useful for creating dates from
     * NasDaq CSV-files in our program.
     *
     * @param date a {@link String} using the american standard date format mm/dd/yyyy.
     * @throws IOException if the date provided is not considered a valid date.
     */
    public Date(String date) throws IOException { /* For use when converting the american date strings in the NasDaq CSV-files */
        String[] dateValues = date.split("/");
        int year = Integer.parseInt(dateValues[2]);
        int month = Integer.parseInt(dateValues[0]);
        int day = Integer.parseInt(dateValues[1]);
        validateDate(year, month, day);  /* this() call must be first line in constructor... :/ */
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * A constructor for class Date that creates a Date using the {@link LocalDate} class.
     */
    public Date() {
        LocalDate localDate = LocalDate.now();
        this.year = localDate.getYear();
        this.month = localDate.getMonth().getValue();
        this.day = localDate.getDayOfMonth();
    }

    /**
     * A constructor for class Date that creates a copy of a given {@link Date}.
     *
     * @param date
     */
    public Date(Date date) {
        this.year = date.getYear();
        this.month = date.getMonth();
        this.day = date.getDay();
    }

    /* Getters */

    /**
     * A getter method for the day parameter.
     * @return the day parameter of the Date.
     */
    public int getDay() { return day; }

    /**
     * A getter method for the month parameter.
     * @return the month parameter of the Date.
     */
    public int getMonth() { return month; }

    /**
     * A getter method for the year parameter.
     * @return the year parameter of the Date.
     */
    public int getYear() { return year; }

    /* IO-methods */

    /**
     * A method that returns a {@link String} representation of a Date.
     * @return A {@link String} representation of a {@link Date}.
     */
    @Override
    public String toString() {
        return String.format("%2d/%2d/%4d", day, month, year);
    }

    /* IO-Validation methods */

    /**
     * A method that throws an {@link IOException} if a date is considered invalid.
     *
     * @param year an {@link Integer} that represents the year of a Date.
     * @param month an {@link Integer} that represents the month of a Date.
     * @param day an {@link Integer} that represents the day of a Date.
     * @throws IOException is thrown if one or more of the parameters are not valid.
     */
    private void validateDate(int year, int month, int day) throws IOException {
        if (!validYear(year))
            throw new IOException("Invalid Year in Date Initialization");
        else if (!validMonth(month))
            throw new IOException("Invalid Month in Date Initialization");
        else if (!validDay(month , day))
            throw new IOException("Invalid Day in Date Initialization");
    }

    /**
     * A method that checks if the year attribute is after 1900 and before or equal to the current year.
     * @return the {@link Boolean} value of the conditions.
     */
    private Boolean validYear() {
        return (1900 < this.year && this.year <= LocalDateTime.now().getYear());
    }

    /**
     * A method that checks if the year parameter is after 1900 and before or equal to the current year.
     *
     * @param year a {@link Integer} representing a year.
     * @return the {@link Boolean} value of the conditions.
     */
    private Boolean validYear(int year) {
        return (1900 < year && year <= LocalDateTime.now().getYear());
    }

    /**
     * A method that checks if the month attribute is more than 0 and less or equal to 12.
     * @return the {@link Boolean} value of the conditions.
     */
    private Boolean validMonth() {
        return (0 < this.month && this.month <= 12);
    }

    /**
     * A method that checks if the month parameter is more than 0 and less or equal to 12.
     *
     * @param month a {@link Integer} representing a month.
     * @return the {@link Boolean} value of the conditions.
     */
    private Boolean validMonth(int month) {
        return (0 < month && month <= 12);
    }

    /**
     * A method that checks if the day attribute is more than 0 and less than or equal to the amount of days in the
     * {@link Date#daysInMonth} that {@link Date#month} keys to.
     * @return the {@link Boolean} value of the conditions.
     */
    private Boolean validDay() {
        return (0 < this.day && this.day <= daysInMonth.get(this.month));
    }

    /**
     * A method that checks if the day parameter is  more than 0 and less than or equal to the amount of days in the
     * {@link Date#daysInMonth} that parameter month keys to.
     *
     * @param month a {@link Integer} representing a month.
     * @param day a {@link Integer} representing a day.
     * @return the {@link Boolean} value of the conditions.
     */
    private Boolean validDay(int month, int day) {
        return (0 < day && day <= daysInMonth.get(month));
    }

    /* Comparison methods */

    /**
     * A method that checks if this Date is before the Date given in the parameter otherDate.
     *
     * @param otherDate the {@link Date} to compare with.
     * @return the {@link Boolean} value of the comparison.
     */
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

    /**
     * A method that checks if this Date is before or equal to the Date given in the parameter otherDate.
     *
     * @param otherDate the {@link Date} to compare with.
     * @return the {@link Boolean} value of the comparison.
     */
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

    /**
     * A method that checks if this Date is after the Date given in the parameter otherDate.
     *
     * @param otherDate the {@link Date} to compare with.
     * @return the {@link Boolean} value of the comparison.
     */
    public Boolean isAfter(Date otherDate) { return otherDate.isBefore(this); }

    /**
     * A method that checks if this Date is after or equal to the Date given in the parameter otherDate.
     *
     * @param otherDate the {@link Date} to compare with.
     * @return the {@link Boolean} value of the comparison.
     */
    public Boolean isAfterOrEqual(Date otherDate) { return otherDate.isBeforeOrEqual(this); }

    /**
     * A method that checks if this Date is equal to the Date given in the parameter otherDate.
     *
     * @param otherDate the {@link Date} to compare with.
     * @return the {@link Boolean} value of the comparison.
     */
    public Boolean isEqualTo(Date otherDate) {
        if (this.equals(otherDate))
            return true;
        return (this.year == otherDate.getYear() &&
                this.month == otherDate.getMonth() &&
                this.day == otherDate.getDay());
    }

    /*  Functional methods  */

    /**
     * A method that returns the next Date after this Date.
     *
     * @return the next {@link Date}.
     * @throws IOException if the next Date is in the future.
     */
    public Date nextDate() throws IOException {
        LocalDate date = LocalDate.of(this.year, this.month, this.day);
        date = date.plusDays(1);
        return new Date(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
    }

    /**
     * A method that returns the Date the parameter days amount after this Date.
     *
     * @param days a {@link Integer} for the amount of days to increment with.
     * @return the new {@link Date}.
     * @throws IOException if the next Date is in the future.
     */
    public Date plusDays(int days) throws IOException {
        LocalDate date = LocalDate.of(this.year, this.month, this.day);
        date = date.plusDays(days);
        return new Date(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
    }

    /**
     * A method that returns the previous Date before this Date.
     *
     * @return the previous {@link Date}.
     * @throws IOException if the previous Date is before the year 1901.
     */
    public Date previousDate() throws IOException {
        LocalDate date = LocalDate.of(this.year, this.month, this.day);
        date = date.minusDays(1);
        return new Date(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
    }


    /**
     * A method that returns the previous Date the parameter days amount before this Date.
     *
     * @param days a {@link Integer} for the amount of days to decrement with.
     * @return the previous {@link Date}.
     * @throws IOException if the previous Date is before the year 1901.
     */
    public Date minusDays(int days) throws IOException {
        LocalDate date = LocalDate.of(this.year, this.month, this.day);
        date = date.minusDays(days);
        return new Date(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
    }

    /**
     * A method that returns a List of an interval of Dates. From this Date to the Date given in the parameter "to".
     *
     * @param to the Date to make an interval towards.
     * @return a {@link List} of {@link Date}s in an interval.
     */
    public List<Date> listIntervalTo(Date to) {
        ArrayList<Date> listInterval = new ArrayList<>();
        Date iterator = new Date(this);
        boolean err = false;

        while (iterator.isBeforeOrEqual(to)) {
            listInterval.add(new Date(iterator));
            try {
                iterator = iterator.nextDate();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("listInterval iterator out of bounds");
                break;
            }
        }
        return listInterval;
    }

    /**
     * A method that returns the next weekday after this Date.
     *
     * @return the next weekday {@link Date}.
     * @throws IOException if the next Date is in the future.
     */
    public Date nextWeekDay() throws IOException {
        LocalDate date = LocalDate.of(this.year, this.month, this.day);
        DayOfWeek dayOfWeek;
        do {
            date = date.plusDays(1);
            dayOfWeek = date.getDayOfWeek();
        } while (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY);
        return new Date(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
    }

    /**
     * A method that returns the previous weekday before this Date.
     *
     * @return the previous weekday {@link Date}.
     * @throws IOException if the previous weekday is before 1901.
     */
    public Date previousWeekDay() throws IOException {
        LocalDate date = LocalDate.of(this.year, this.month, this.day);
        DayOfWeek dayOfWeek;
        do {
            date = date.plusDays(-1);
            dayOfWeek = date.getDayOfWeek();
        } while (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY);
        return new Date(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
    }
}
