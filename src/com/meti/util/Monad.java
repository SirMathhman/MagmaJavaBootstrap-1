package com.meti.util;

import java.util.function.Function;

public class Monad<A> {
    private final A value;

    public Monad(A value) {
        this.value = value;
    }

    public <B> Monad<B> map(Function<A, B> function) {
        return new Monad<>(function.apply(value));
    }

    public <R> Duad<A, R> extract(Function<A, R> function) {
        return new Duad<>(value, function.apply(value));
    }

    public A complete() {
        return value;
    }

    public <B> Duad<A, B> with(B next) {
        return new Duad<>(value, next);
    }
}
