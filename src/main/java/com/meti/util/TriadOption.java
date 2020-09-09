package com.meti.util;

import java.util.function.Predicate;

public interface TriadOption<A, B, C> {
    TriadOption<A, B, C> filterMiddle(Predicate<B> predicate);

    TriadOption<A, B, C> filterEnd(Predicate<C> predicate);

    Option<A> onlyFirst();

    <R, E extends Throwable> R applyDestructionOrThrow(TriFunction<A, B, C, R> function, E e) throws E;
}
