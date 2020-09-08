package com.meti.util;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.meti.util.Some.Some;

public class BothOption<A, B> implements DuadOption<A, B> {
    private final A value0;
    private final B value1;

    public BothOption(A value0, B value1) {
        this.value0 = value0;
        this.value1 = value1;
    }

    @Override
    public <R> Option<R> map(BiFunction<A, B, R> function) {
        return Some(function.apply(value0, value1));
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
}
