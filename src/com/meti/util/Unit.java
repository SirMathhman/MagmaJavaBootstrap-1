package com.meti.util;

import java.util.function.Function;

public class Unit<A> {
    private final A value;

    public Unit(A value) {
        this.value = value;
    }

    public <B> Unit<B> map(Function<A, B> function) {
        return new Unit<>(function.apply(value));
    }

    public <R> Pair<A, R> explode(Function<A, R> function) {
        return new Pair<>(value, function.apply(value));
    }

    public A complete() {
        return value;
    }

    public <B> Pair<A, B> with(B next) {
        return new Pair<>(value, next);
    }
}
