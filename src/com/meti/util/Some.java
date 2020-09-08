package com.meti.util;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.meti.util.Monad.Monad;
import static com.meti.util.MonadStream.Stream;
import static com.meti.util.None.None;

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
        return Stream(value);
    }

    @Override
    public MonadOption<T> filter(Predicate<T> predicate) {
        return predicate.test(value) ? Some(value) : None();
    }

    @Override
    public <R> MonadOption<R> map(Function<T, R> function) {
        return Monad(value)
                .map(function)
                .toOption();
    }

    @Override
    public <R> MonadOption<R> replace(Supplier<R> supplier) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <R> R applyOrThrow(Function<T, R> function) {
        return applyOrThrow(function, new NoSuchElementException("No value present."));
    }
}
