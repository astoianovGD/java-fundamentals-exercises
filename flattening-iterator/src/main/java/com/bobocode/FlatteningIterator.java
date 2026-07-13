package com.bobocode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class FlatteningIterator<T> implements Iterator<T> {
    private final Queue<Iterator<T>> iteratorQueue;

    @SafeVarargs
    public FlatteningIterator(Iterator<T>... iterators) {
        if (iterators == null) {
            throw new IllegalArgumentException("Iterators source cannot be null");
        }
        this.iteratorQueue = new LinkedList<>();

        for (Iterator<T> iter : iterators) {
            if (iter != null) {
                iteratorQueue.add(iter);
            }
        }
    }

    @Override
    public boolean hasNext() {
        // Clean out exhausted iterators from the front of the queue
        while (!iteratorQueue.isEmpty() && !iteratorQueue.peek().hasNext()) {
            iteratorQueue.poll();
        }
        // If the queue isn't empty, the top iterator is guaranteed to have elements left
        return !iteratorQueue.isEmpty();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in flattened sequence");
        }
        // hasNext() guarantees the head iterator has a next element
        return iteratorQueue.peek().next();
    }
}