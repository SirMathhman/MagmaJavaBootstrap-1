package com.meti.util;

import java.util.function.Predicate;

public interface TriadOption<A, B, C> {
    TriadOption<A, B, C> filterMiddle(Predicate<B> predicate);

    TriadOption<A, B, C> filterEnd(Predicate<C> predicate);

    Option<A> onlyFirst();
}
