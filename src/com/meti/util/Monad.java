package com.meti.util;

import java.util.function.Function;

import static com.meti.util.Some.Some;

public class Monad<T> {
    private final T value;

    public MonadOption<T> asOption() {
        return Some(value);
    }

    public Monad(T value) {
        this.value = value;
    }

    public <B> Monad<B> map(Function<T, B> function) {
        return new Monad<>(function.apply(value));
    }

    public <R> Duad<T, R> extract(Function<T, R> function) {
        return new Duad<>(value, function.apply(value));
    }

    @Deprecated
    public T complete() {
        return value;
    }

    public <B> Duad<T, B> with(B next) {
        return new Duad<>(value, next);
    }

    public <R> R apply(Function<T, R> function) {
        return function.apply(value);
    }
}
