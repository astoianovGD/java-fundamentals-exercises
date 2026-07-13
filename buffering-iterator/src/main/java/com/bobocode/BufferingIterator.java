package com.bobocode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class BufferingIterator<T> implements Iterator<List<T>> {
    private final Iterator<T> sourceIterator;
    private final int batchSize;

    public BufferingIterator(Iterator<T> sourceIterator, int batchSize) {
        if (sourceIterator == null) {
            throw new IllegalArgumentException("Source iterator cannot be null");
        }
        if (batchSize <= 0) {
            throw new IllegalArgumentException("Batch size must be greater than 0");
        }
        this.sourceIterator = sourceIterator;
        this.batchSize = batchSize;
    }

    @Override
    public boolean hasNext() {
        return sourceIterator.hasNext();
    }

    @Override
    public List<T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more batches available");
        }

        List<T> batch = new ArrayList<>(batchSize);
        // Pull up to batchSize elements, or fewer if the source runs dry
        while (batch.size() < batchSize && sourceIterator.hasNext()) {
            batch.add(sourceIterator.next());
        }

        return batch;
    }
}