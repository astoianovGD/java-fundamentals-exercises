package com.bobocode;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BusinessDaysIterator implements Iterator<LocalDate> {
    private LocalDate currentDate;

    public BusinessDaysIterator(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        this.currentDate = startDate;
    }

    /**
     * Since this is designed to be an infinite iterator, hasNext() always returns true.
     */
    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public LocalDate next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        currentDate = currentDate.plusDays(1);

        if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            currentDate = currentDate.plusDays(2); // Jump to Monday
        } else if (currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            currentDate = currentDate.plusDays(1); // Jump to Monday
        }

        return currentDate;
    }
}