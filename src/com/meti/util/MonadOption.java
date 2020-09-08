package com.meti.util;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface MonadOption<T> {
    <R> DuadOption<T, R> extract(Function<T, R> function);

    <R, E extends Throwable> R applyOrThrow(Function<T, R> function, E exception) throws E;

    <R> R applyOrThrow(Function<T, R> function);

    <R> DuadOption<T, R> with(R other);

    MonadStream<T> stream();

    MonadOption<T> filter(Predicate<T> predicate);

    <R> MonadOption<R> map(Function<T, R> function);

    <R> MonadOption<R> replace(Supplier<R> supplier);
}
