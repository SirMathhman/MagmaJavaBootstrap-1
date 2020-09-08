package com.meti.util;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Some<T> implements MonadOption<T> {
    private final T value;

    private Some(T value) {
        this.value = value;
    }

    public static <T> MonadOption<T> Some(T value) {
        return new Some<>(value);
    }

    @Override
    public <R> DuadOption<T, R> extract(Function<T, R> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <R, E extends Throwable> R applyOrThrow(Function<T, R> function, E exception) throws E {
        return function.apply(value);
    }

    @Override
    public <R> DuadOption<T, R> with(R other) {
        return new BothOption<>(value, other);
    }

    @Override
    public MonadStream<T> stream() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MonadOption<T> filter(Predicate<T> predicate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <R> MonadOption<R> map(Function<T, R> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <R> MonadOption<R> replace(Supplier<R> supplier) {
        throw new UnsupportedOperationException();
    }
}
