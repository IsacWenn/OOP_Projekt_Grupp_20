package model.util;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestDate {

    private static Date date1;
    private static Date date2;
    private static Date date3;
    private static Date firstCorrectDate;
    private static Date aThursday;
    private static Date aFriday;
    private static Date aMonday;
    private static Date aTuesday;
    private static List<Date> dateList;
    private static Set<Date> dateSet;

    @BeforeEach
    public void resetDateTestVariables() throws IOException {
        date1 = new Date(2022, 6, 5);
        date2 = new Date(2022, 6, 6);
        date3 = new Date(2022, 6, 7);
        firstCorrectDate = new Date(1901, 1, 1);
        aThursday = new Date(2022, 9, 22);
        aFriday = new Date(2022, 9, 23);
        aMonday = new Date(2022, 9, 26);
        aTuesday = new Date(2022, 9, 27);

        dateList = new ArrayList<>(){{
            add(date1);
            add(date2);
            add(date3);
        }};

        dateSet = new HashSet<>(){{
            add(date1);
            add(date2);
            add(date3);
        }};
    }

    @Test
    public void constructingAValidDateUsingIntegersShouldCreateCorrectDate() throws IOException {
        Date testDate = new Date(2020, 6, 8);
        assertEquals(2020, testDate.getYear());
        assertEquals(6, testDate.getMonth());
        assertEquals(8, testDate.getDay());
    }

    @Test
    public void constructingAValidDateUsingStringShouldCreateCorrectDate() throws IOException {
        Date testDate = new Date("09/07/2020");
        assertEquals(2020, testDate.getYear());
        assertEquals(9, testDate.getMonth());
        assertEquals(7, testDate.getDay());
    }

    @Test
    public void constructingAValidDateUsingOtherDateShouldCreateACopy() {
        Date testDate = new Date(date1);
        assertEquals(date1, testDate);
    }

    @Test
    public void constructingAEmptyDateShouldCreateTheCurrentDate() {
        Date testDate = new Date();
        LocalDate localDate = LocalDate.now();
        assertEquals(localDate.getYear(), testDate.getYear());
        assertEquals(localDate.getMonthValue(), testDate.getMonth());
        assertEquals(localDate.getDayOfMonth(), testDate.getDay());
    }

    @Test
    public void constructingInvalidDateUsingIntegersShouldThrowException() {
        assertThrows(IOException.class, () -> {
            Date testDate = new Date(0, 10, 10);
        });
        assertThrows(IOException.class, () -> {
            Date testDate = new Date(2000, 0, 10);
        });
        assertThrows(IOException.class, () -> {
            Date testDate = new Date(2000, 10, 0);
        });
    }

    @Test
    public void constructingInvalidDateUsingStringShouldThrowException() {
        assertThrows(IOException.class, () -> {
           Date testDate = new Date("0/0/0");
        });
    }

    @Test
    public void datesRepresentingSameDateShouldBeEqual() throws IOException {
        Date sameDate = new Date(date1.getYear(), date1.getMonth(), date1.getDay());
        assertEquals(sameDate, date1);
    }

    @Test
    public void datesNotRepresentingSameDateShouldNotBeEqual() {
        assertNotEquals(date1, date2);
    }

    @Test
    public void dateAndADifferentTypeShouldNotBeEqual() {
        assertNotEquals(date1, "Hej");
    }

    @Test
    public void getDayShouldReturnTheDayInMonthOfTheDate() {
        assertEquals(5, date1.getDay());
    }

    @Test
    public void getMonthShouldReturnTheIntegerValueOfTheMonthInDate() {
        assertEquals(6, date1.getMonth());
    }

    @Test
    public void getYearShouldReturnTheIntegerOfTheYearInDate() {
        assertEquals(2022, date1.getYear());
    }

    @Test
    public void toStringShouldReturnStringRepresentationOfDate() {
        assertEquals( " 5/ 6/2022", date1.toString());
    }

    @Test
    public void validYearShouldReturnTrue() { // After 1900 and Before or Equal to current Year.
        assertTrue(Date.validYear(1950));
    }

    @Test
    public void invalidYearShouldReturnFalse() {
        assertFalse(Date.validYear(1900));
        assertFalse(Date.validYear(LocalDate.now().getYear() + 1));
    }

    @Test
    public void validMonthShouldReturnTrue() {
        assertTrue(Date.validMonth(6));
    }

    @Test
    public void invalidMonthShouldReturnFalse() {
        assertFalse(Date.validMonth(0));
        assertFalse(Date.validMonth(13));
    }

    @Test
    public void validDayShouldReturnTrue() {
        assertTrue(Date.validDay(6, 20));
    }

    @Test
    public void invalidDayShouldReturnFalse() {
        assertFalse(Date.validDay(0, 0));
        assertThrows(NullPointerException.class, () -> {
            assertFalse(Date.validDay(0 , 10));
        });
        assertFalse(Date.validDay(5, 32));
        assertFalse(Date.validDay(5, 0));
        assertFalse(Date.validDay(2, 30));
    }

    @Test
    public void compareToShouldReturnIntegersAccordingToComparableInterface() {
        assertEquals(1, date2.compareTo(date1));
        assertEquals(-1, date2.compareTo(date3));
        assertEquals(0, date2.compareTo(new Date(date2)));
    }

    @Test
    public void isBeforeShouldReturnTrueOnlyIfDateIsBefore() {
        assertTrue(date2.isBefore(date3));
        assertFalse(date2.isBefore(date1));
        assertFalse(date2.isBefore(new Date(date2)));
        assertFalse(date1.isBefore(firstCorrectDate));
        assertTrue(firstCorrectDate.isBefore(date1));
    }

    @Test
    public void isBeforeOrEqualShouldReturnTrueOnlyIfDateIsBeforeOrEqual() {
        assertTrue(date2.isBeforeOrEqual(date3));
        assertFalse(date2.isBeforeOrEqual(date1));
        assertTrue(date2.isBeforeOrEqual(new Date(date2)));
        assertFalse(date1.isBeforeOrEqual(firstCorrectDate));
    }

    @Test
    public void isAfterShouldReturnTrueOnlyIfDateIsAfter() {
        assertFalse(date2.isAfter(date3));
        assertTrue(date2.isAfter(date1));
        assertFalse(date2.isAfter(new Date(date2)));
    }

    @Test
    public void isAfterOrEqualShouldReturnTrueOnlyIfDateIsAfterOrEqual() {
        assertFalse(date2.isAfterOrEqual(date3));
        assertTrue(date2.isAfterOrEqual(date1));
        assertTrue(date2.isAfterOrEqual(new Date(date2)));
    }

    @Test
    public void nextDateShouldReturnDateRepresentingNextDate() throws IOException {
        assertEquals(date3, date2.nextDate());
    }

    @Test
    public void nextDateShouldThrowExceptionIfItIsInvalid() {
        assertThrows(IOException.class, () -> {
           Date currentDate = new Date();
           Date tomorrow = currentDate.nextDate();
        });
    }

    @Test
    public void plusDaysShouldReturnDateRepresentingNAmountOfDaysAhead() throws IOException {
        assertEquals(date3, date1.plusDays(2));
    }

    @Test
    public void plusDaysShouldThrowExceptionIfInvalid() {
        assertThrows(IOException.class, () -> {
            Date currentDate = new Date();
            System.out.println(currentDate);
            Date inTenDays = currentDate.plusDays(10);
        });
    }

    @Test
    public void previousDateShouldReturnDateRepresentingPreviousDate() throws IOException {
        assertEquals(date1, date2.previousDate());
    }

    @Test
    public void previousDateShouldThrowExceptionIfInvalid() {
        assertThrows(IOException.class, () -> {
            Date illegalDate = firstCorrectDate.previousDate();
        });
    }

    @Test
    public void minusDaysShouldReturnDateRepresentingNAmountOfDaysBehind() throws IOException {
        assertEquals(date1, date3.minusDays(2));
    }

    @Test
    public void minusDaysShouldThrowExceptionIfInvalid() {
        assertThrows(IOException.class, () -> {
            Date illegalDate = firstCorrectDate.minusDays(5);
        });
    }

    @Test
    public void listIntervalToShouldCreateACorrectList() {
        assertEquals(dateList, date1.listIntervalTo(date3));
    }

    @Test
    public void nextWeekDayShouldSkipDatesOnWeekEnd() throws IOException {
        assertEquals(aMonday, aFriday.nextWeekDay());
    }

    @Test
    public void nextWeekDayShouldNotSkipWeekDays() throws IOException {
        assertEquals(aTuesday, aMonday.nextWeekDay());
        assertEquals(aFriday, aThursday.nextWeekDay());
    }

    @Test
    public void previousWeekDayShouldSkipWeekEnd() throws IOException {
        assertEquals(aFriday, aMonday.previousWeekDay());
    }

    @Test
    public void previousWeekDayShouldNotSkipWeekDays() throws IOException {
        assertEquals(aMonday, aTuesday.previousWeekDay());
        assertEquals(aThursday, aFriday.previousWeekDay());
    }

    @Test
    public void sortDatesShouldReturnAListOfDatesInChronologicalOrder() throws IOException {
        List<Date> listOfThisYearsDates = new Date(2022, 1, 1).listIntervalTo(new Date());
        List<Date> testList = new ArrayList<>(){{ addAll(listOfThisYearsDates); }};

        assertEquals(listOfThisYearsDates, testList);
        while (listOfThisYearsDates.equals(testList))
            Collections.shuffle(testList);
        assertNotEquals(listOfThisYearsDates, testList);
        testList = Date.sortDates(testList);
        assertEquals(listOfThisYearsDates, testList);
    }

    @Test
    public void sortDatesShouldBeAbleToHandleSets() {
        List<Date> newList = Date.sortDates(dateSet);
        assertEquals(dateList, newList);
    }

    @Test
    public void sortDatesQShouldReturnAListOfDatesInChronologicalOrder() throws IOException {
        List<Date> listOfThisYearsDates = new Date(2022, 1, 1).listIntervalTo(new Date());
        List<Date> testList = new ArrayList<>(){{ addAll(listOfThisYearsDates); }};

        assertEquals(listOfThisYearsDates, testList);
        while (listOfThisYearsDates.equals(testList))
            Collections.shuffle(testList);
        assertNotEquals(listOfThisYearsDates, testList);
        testList = Date.sortDatesQ(testList);
        assertEquals(listOfThisYearsDates, testList);
    }

    @Test
    public void sortDatesQShouldBeAbleToHandleSets() {
        List<Date> newList = Date.sortDatesQ(dateSet);
        assertEquals(dateList, newList);
    }
}
