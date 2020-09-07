package com.meti.util;

import java.util.function.Function;

public interface MonadOption<T> {
    <R, E extends Throwable> R applyOrThrow(Function<T, R> function, E exception) throws E;

    <R> DuadOption<T, R> with(R other);

    MonadStream<T> stream();
}
