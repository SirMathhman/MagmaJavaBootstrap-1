package com.meti.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Option<T> {
    <R> DuadOption<T, R> extract(Function<T, R> function);

    <R, E extends Throwable> R applyOrThrow(Function<T, R> function, E exception) throws E;

    <E extends Throwable> void acceptOrThrow(Consumer<T> consumer, E exception) throws E;

    <R> R applyOrThrow(Function<T, R> function);

    <R> DuadOption<T, R> with(R other);

    MonadStream<T> stream();

    Option<T> filter(Predicate<T> predicate);

    <R> Option<R> map(Function<T, R> function);

    <R> Option<R> supply(Supplier<R> supplier);

    <R> Option<R> supplyValue(R value);

    <R> Monad<R> set(R ifPresent, R ifEmpty);

    <R> R applyOrElse(Function<T, R> function, R other);

    Monad<T> toMonadOrThrow();

    boolean isEmpty();

    default <R> Option<R> flatten(Function<T, Option<R>> function) {
        throw new UnsupportedOperationException();
    }
}
