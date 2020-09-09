package com.meti.util;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.meti.util.Some.Some;

public class BothOption<A, B> implements DuadOption<A, B> {
    private final A start;
    private final B end;

    public BothOption(A start, B end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public <R> Option<R> map(BiFunction<A, B, R> function) {
        return Some(function.apply(start, end));
    }

    @Override
    public <R> TriadOption<A, B, R> extractStart(Function<A, R> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <R> TriadOption<A, B, R> with(R value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DuadOption<A, B> filterEnd(Predicate<B> predicate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <R, E extends Throwable> R applyStartOrThrow(Function<A, R> function, Function<Option<B>, E> supplier) throws E {
        throw new UnsupportedOperationException();
    }

    @Override
    public <R, E extends Throwable> R applyAllOrThrow(BiFunction<A, B, R> function, Supplier<E> supplier) throws E {
        throw new UnsupportedOperationException();
    }

    @Override
    public Option<A> ignoreLast() {
        return Some(start);
    }
}
