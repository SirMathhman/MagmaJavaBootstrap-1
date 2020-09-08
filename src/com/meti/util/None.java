package com.meti.util;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class None<T> implements MonadOption<T> {
    private None() {
    }

    public static <T> None<T> None() {
        return new None<>();
    }

    @Override
    public <R> DuadOption<T, R> extract(Function<T, R> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <R, E extends Throwable> R applyOrThrow(Function<T, R> function, E exception) throws E {
        throw exception;
    }

    @Override
    public <R> DuadOption<T, R> with(R other) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MonadStream<T> stream() {
        return MonadStream.Empty();
    }

    @Override
    public MonadOption<T> filter(Predicate<T> predicate) {
        return None();
    }

    @Override
    public <R> MonadOption<R> map(Function<T, R> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <R> MonadOption<R> replace(Supplier<R> supplier) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <R> R applyOrThrow(Function<T, R> function){
        return applyOrThrow(function, new NoSuchElementException("No value present."));
    }
}
