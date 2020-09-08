package com.meti.util;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Duad<A, B> {
    private final A start;
    private final B end;

    public Duad(A start, B end) {
        this.start = start;
        this.end = end;
    }

    public <R> Duad<A, R> replaceEnd(BiFunction<A, B, R> function) {
        return new Duad<>(start, function.apply(start, end));
    }

    public Duad<B, A> reverse() {
        return new Duad<>(end, start);
    }

    public <T> Monad<T> map(BiFunction<A, B, T> function) {
        return Monad.Monad(function.apply(start, end));
    }

    public <R> Triad<A, B, R> extractStart(Function<A, R> function) {
        return new Triad<>(start, end, function.apply(start));
    }

    public <C> Triad<A, B, C> supply(C value) {
        return new Triad<>(start, end, value);
    }

    public <T> Duad<A, T> zipSecond(BiFunction<A, B, T> function) {
        return new Duad<>(start, function.apply(start, end));
    }

    public <T> Duad<T, B> mapFirst(Function<A, T> function) {
        return new Duad<>(function.apply(start), end);
    }

    public <T> T apply(BiFunction<A, B, T> function) {
        return function.apply(start, end);
    }
}
