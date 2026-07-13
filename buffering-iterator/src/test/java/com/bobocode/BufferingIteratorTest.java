package com.bobocode;

import com.bobocode.BufferingIterator;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class BufferingIteratorTest {

    @Test
    void testUnevenBatching() {
        List<Integer> data = List.of(1, 2, 3, 4, 5);
        BufferingIterator<Integer> iter = new BufferingIterator<>(data.iterator(), 3);

        assertTrue(iter.hasNext());
        assertEquals(List.of(1, 2, 3), iter.next());

        assertTrue(iter.hasNext());
        assertEquals(List.of(4, 5), iter.next());

        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, iter::next);
    }

    @Test
    void testPerfectlyEvenBatching() {
        List<Integer> data = List.of(1, 2, 3, 4);
        BufferingIterator<Integer> iter = new BufferingIterator<>(data.iterator(), 2);

        assertTrue(iter.hasNext());
        assertEquals(List.of(1, 2), iter.next());

        assertTrue(iter.hasNext());
        assertEquals(List.of(3, 4), iter.next());

        assertFalse(iter.hasNext());
    }

    @Test
    void testBatchSizeGreaterThanListSize() {
        List<String> data = List.of("A", "B");
        BufferingIterator<String> iter = new BufferingIterator<>(data.iterator(), 10);

        assertTrue(iter.hasNext());
        assertEquals(List.of("A", "B"), iter.next());
        assertFalse(iter.hasNext());
    }

    @Test
    void testEmptySourceIterator() {
        Iterator<Integer> emptyIter = Collections.<Integer>emptyIterator();
        BufferingIterator<Integer> iter = new BufferingIterator<>(emptyIter, 5);

        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, iter::next);
    }

    @Test
    void testBatchSizeOfOne() {
        List<Integer> data = List.of(1, 2);
        BufferingIterator<Integer> iter = new BufferingIterator<>(data.iterator(), 1);

        assertTrue(iter.hasNext());
        assertEquals(List.of(1), iter.next());
        assertTrue(iter.hasNext());
        assertEquals(List.of(2), iter.next());
        assertFalse(iter.hasNext());
    }

    @Test
    void testInvalidConstructorArguments() {
        assertThrows(IllegalArgumentException.class, () ->
                new BufferingIterator<>(List.of(1).iterator(), 0)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new BufferingIterator<>(List.of(1).iterator(), -5)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new BufferingIterator<>(null, 5)
        );
    }
}