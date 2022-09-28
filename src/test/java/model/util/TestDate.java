package model.util;

import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestDate {

    private static Date date1;
    private static Date date2;
    private static Date date3;

    @BeforeEach
    public void resetDateTestVariables() throws IOException {

        date1 = new Date(2022, 6, 5);
        date2 = new Date(2022, 6, 6);
        date3 = new Date(2022, 6, 7);

    }

    @Test
    public void datesRepresentingSameDateShouldBeEqual() throws IOException {
        Date sameDate = new Date(date1.getYear(), date1.getMonth(), date1.getDay());
        assertEquals(date1, sameDate);
    }

    @Test
    public void datesNotRepresentingSameDateShouldNotBeEqual() {
        assertNotEquals(date1, date2);
    }

}
