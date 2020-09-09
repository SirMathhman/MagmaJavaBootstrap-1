package com.meti.util;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class NeitherOption<A, B> implements DuadOption<A, B> {
    @Override
    public <R> Option<R> map(BiFunction<A, B, R> function) {
        return null;
    }

    @Override
    public <R> TriadOption<A, B, R> extractStart(Function<A, R> function) {
        return null;
    }

    @Override
    public <R> TriadOption<A, B, R> with(R value) {
        return null;
    }

    @Override
    public DuadOption<A, B> filterEnd(Predicate<B> predicate) {
        return null;
    }

    @Override
    public <R, E extends Throwable> R applyStartOrThrow(Function<A, R> function, Function<Option<B>, E> supplier) throws E {
        return null;
    }

    @Override
    public <R, E extends Throwable> R applyAllOrThrow(BiFunction<A, B, R> function) throws E {
        return null;
    }

    @Override
    public DuadOption<A, B> filterBoth(BiPredicate<A, B> predicate) {
        return null;
    }

    @Override
    public Option<A> ignoreLast() {
        return null;
    }

    @Override
    public <R> DuadOption<R, B> mapStart(Function<A, R> function) {
        return null;
    }

    @Override
    public DuadOption<B, A> reverse() {
        return null;
    }
}
