package com.bobocode;

import com.bobocode.ZippingIterator;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class ZippingIteratorTest {

    @Test
    void testZippingWithDifferentTypesToString() {
        List<String> names = List.of("Alice", "Bob", "Charlie");
        List<Integer> scores = List.of(95, 88, 92);

        ZippingIterator<String, Integer, String> iter = new ZippingIterator<>(
                names.iterator(),
                scores.iterator(),
                (name, score) -> name + ": " + score
        );

        assertTrue(iter.hasNext());
        assertEquals("Alice: 95", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Bob: 88", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Charlie: 92", iter.next());
        assertFalse(iter.hasNext());
    }

    @Test
    void testZippingMathFunction() {
        List<Integer> list1 = List.of(10, 20, 30);
        List<Integer> list2 = List.of(1, 2, 3);

        // (Summing them up)
        ZippingIterator<Integer, Integer, Integer> iter = new ZippingIterator<>(
                list1.iterator(),
                list2.iterator(),
                Integer::sum
        );

        assertEquals(11, iter.next());
        assertEquals(22, iter.next());
        assertEquals(33, iter.next());
        assertFalse(iter.hasNext());
    }

    @Test
    void testUnevenIteratorsFirstIsShorter() {
        List<String> shortList = List.of("A", "B");
        List<Integer> longList = List.of(1, 2, 3, 4, 5);

        ZippingIterator<String, Integer, String> iter = new ZippingIterator<>(
                shortList.iterator(),
                longList.iterator(),
                (str, num) -> str + num
        );

        assertEquals("A1", iter.next());
        assertEquals("B2", iter.next());

        // Should terminate immediately because shortList is exhausted
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next());
    }

    @Test
    void testUnevenIteratorsSecondIsShorter() {
        List<Integer> longList = List.of(1, 2, 3, 4, 5);
        List<String> shortList = List.of("X");

        ZippingIterator<Integer, String, String> iter = new ZippingIterator<>(
                longList.iterator(),
                shortList.iterator(),
                (num, str) -> str + num
        );

        assertEquals("X1", iter.next());
        assertFalse(iter.hasNext());
    }

    @Test
    void testEmptyIterators() {
        Iterator<Integer> empty1 = Collections.emptyIterator();
        Iterator<Integer> empty2 = Collections.emptyIterator();

        ZippingIterator<Integer, Integer, Integer> iter = new ZippingIterator<>(
                empty1,
                empty2,
                (a, b) -> a * b
        );

        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next());
    }
}