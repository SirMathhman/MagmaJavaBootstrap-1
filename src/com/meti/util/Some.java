package com.meti.util;

import java.util.function.Function;

public class Some<T> implements MonadOption<T> {
    private final T value;

    private Some(T value) {
        this.value = value;
    }

    public static <T> MonadOption<T> Some(T value) {
        return new Some<>(value);
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
        return null;
    }
}
