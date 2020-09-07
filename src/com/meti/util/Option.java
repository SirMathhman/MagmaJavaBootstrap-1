package com.meti.util;

import java.util.function.Function;

public interface Option<T> {
    <R, E extends Throwable> R applyOrThrow(Function<T, R> function, E exception) throws E;
}
