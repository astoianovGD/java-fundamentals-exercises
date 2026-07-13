package com.bobocode;

import com.bobocode.BusinessDaysIterator;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class BusinessDaysIteratorTest {

    @Test
    void testNextBusinessDayFromSaturday() {
        // Jan 1, 2022 is a Saturday
        LocalDate saturday = LocalDate.of(2022, 1, 1);
        BusinessDaysIterator iter = new BusinessDaysIterator(saturday);

        // Next business day should jump straight to Monday (Jan 3)
        assertEquals(LocalDate.of(2022, 1, 3), iter.next());
        // Next one should be Tuesday (Jan 4)
        assertEquals(LocalDate.of(2022, 1, 4), iter.next());
    }

    @Test
    void testNextBusinessDayFromSunday() {
        // Jan 2, 2022 is a Sunday
        LocalDate sunday = LocalDate.of(2022, 1, 2);
        BusinessDaysIterator iter = new BusinessDaysIterator(sunday);

        // Next business day should jump to Monday (Jan 3)
        assertEquals(LocalDate.of(2022, 1, 3), iter.next());
    }

    @Test
    void testNextBusinessDayFromFriday() {
        // Jan 7, 2022 is a Friday
        LocalDate friday = LocalDate.of(2022, 1, 7);
        BusinessDaysIterator iter = new BusinessDaysIterator(friday);

        // Next business day should skip Saturday/Sunday and land on Monday (Jan 10)
        assertEquals(LocalDate.of(2022, 1, 10), iter.next());
    }

    @Test
    void testConsecutiveMidWeekDays() {
        // Jan 3, 2022 is a Monday
        LocalDate monday = LocalDate.of(2022, 1, 3);
        BusinessDaysIterator iter = new BusinessDaysIterator(monday);

        assertEquals(LocalDate.of(2022, 1, 4), iter.next()); // Tuesday
        assertEquals(LocalDate.of(2022, 1, 5), iter.next()); // Wednesday
        assertEquals(LocalDate.of(2022, 1, 6), iter.next()); // Thursday
        assertEquals(LocalDate.of(2022, 1, 7), iter.next()); // Friday
    }

    @Test
    void testHasNextIsAlwaysTrue() {
        BusinessDaysIterator iter = new BusinessDaysIterator(LocalDate.now());

        assertTrue(iter.hasNext());
        iter.next();
        assertTrue(iter.hasNext());
    }
}