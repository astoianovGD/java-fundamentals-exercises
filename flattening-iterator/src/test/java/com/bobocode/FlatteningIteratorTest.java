package com.bobocode;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class FlatteningIteratorTest {

    @Test
    void testStandardFlattening() {
        Iterator<Integer> iter = new FlatteningIterator<>(
                List.of(42, 5).iterator(),
                List.of(-4).iterator(),
                List.of(999, 998, 997).iterator()
        );

        assertTrue(iter.hasNext());
        assertEquals(42, iter.next());
        assertEquals(5, iter.next());
        assertEquals(-4, iter.next());
        assertEquals(999, iter.next());
        assertEquals(998, iter.next());
        assertEquals(997, iter.next());
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next());
    }

    @Test
    void testWithInterspersedEmptyIterators() {
        Iterator<String> iter = new FlatteningIterator<>(
                Collections.<String>emptyIterator(),
                List.of("A", "B").iterator(),
                Collections.<String>emptyIterator(),
                List.of("C").iterator()
        );

        assertTrue(iter.hasNext());
        assertEquals("A", iter.next());
        assertEquals("B", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("C", iter.next());
        assertFalse(iter.hasNext());
    }

    @Test
    void testAllEmptyIterators() {
        Iterator<Integer> iter = new FlatteningIterator<>(
                Collections.emptyIterator(),
                Collections.emptyIterator()
        );

        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next());
    }

    @Test
    void testSingleIterator() {
        Iterator<Integer> iter = new FlatteningIterator<>(
                List.of(1, 2, 3).iterator()
        );

        assertEquals(1, iter.next());
        assertEquals(2, iter.next());
        assertEquals(3, iter.next());
        assertFalse(iter.hasNext());
    }
}