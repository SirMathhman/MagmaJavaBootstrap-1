package com.meti.util;

public class Triplet<A, B, C> {
    private final A a;
    private final B b;
    private final C c;

    public Triplet(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public <R> Unit<R> implode(TriFunction<A, B, C, R> function) {
        return new Unit<>(function.apply(a, b, c));
    }
}
