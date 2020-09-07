package com.meti.util;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Pair<A, B> {
    private final A value0;
    private final B value1;

    public Pair(A value0, B value1) {
        this.value0 = value0;
        this.value1 = value1;
    }

    public Pair<B, A> swap() {
        return new Pair<>(value1, value0);
    }

    public <T> Unit<T> implode(BiFunction<A, B, T> function) {
        return new Unit<>(function.apply(value0, value1));
    }

    public <R> Triplet<A, B, R> explodeFirst(Function<A, R> function) {
        return new Triplet<>(value0, value1, function.apply(value0));
    }

    public <C> Triplet<A, B, C> supply(C value) {
        return new Triplet<>(value0, value1, value);
    }

    public <T> Pair<A, T> zipSecond(BiFunction<A, B, T> function) {
        return new Pair<>(value0, function.apply(value0, value1));
    }

    public <T> Pair<T, B> mapFirst(Function<A, T> function) {
        return new Pair<>(function.apply(value0), value1);
    }
}
