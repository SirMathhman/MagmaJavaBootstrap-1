package com.meti.util;

import java.util.function.Function;

public class None<T> implements MonadOption<T> {
    private None() {
    }

    public static <T> None<T> None() {
        return new None<>();
    }

    @Override
    public <R, E extends Throwable> R applyOrThrow(Function<T, R> function, E exception) throws E {
        throw exception;
    }

    @Override
    public <R> DuadOption<T, R> with(R other) {
        return null;
    }

    @Override
    public MonadStream<T> stream() {
        return MonadStream.Empty();
    }
}
