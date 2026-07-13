package com.bobocode;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;

public class ZippingIterator<A, B, C> implements Iterator<C> {
    private final Iterator<A> iteratorA;
    private final Iterator<B> iteratorB;
    private final BiFunction<A, B, C> zipFunction;

    public ZippingIterator(Iterator<A> iteratorA, Iterator<B> iteratorB, BiFunction<A, B, C> zipFunction) {
        this.iteratorA = Objects.requireNonNull(iteratorA, "Iterator A cannot be null");
        this.iteratorB = Objects.requireNonNull(iteratorB, "Iterator B cannot be null");
        this.zipFunction = Objects.requireNonNull(zipFunction, "Zip function cannot be null");
    }

    @Override
    public boolean hasNext() {
        // Stops as soon as one iterator runs dry
        return iteratorA.hasNext() && iteratorB.hasNext();
    }

    @Override
    public C next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements to zip");
        }

        return zipFunction.apply(iteratorA.next(), iteratorB.next());
    }
}