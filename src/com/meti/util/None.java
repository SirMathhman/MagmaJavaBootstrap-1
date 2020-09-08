package com.meti.util;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.meti.util.Monad.Monad;

public class None<T> implements Option<T> {
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
    public Option<T> filter(Predicate<T> predicate) {
        return None();
    }

    @Override
    public <R> Option<R> map(Function<T, R> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <R> Option<R> set(Supplier<R> supplier) {
        return Monad(supplier)
                .map(Supplier::get)
                .toOption();
    }

    @Override
    public <R> R applyOrThrow(Function<T, R> function) {
        return applyOrThrow(function, new NoSuchElementException("No value present."));
    }

    @Override
    public <E extends Throwable> void acceptOrThrow(Consumer<T> consumer, E exception) throws E{
        throw new UnsupportedOperationException();
    }

    @Override
    public <R> Monad<R> set(R ifPresent, R ifEmpty) {
        throw new UnsupportedOperationException();
    }
}
