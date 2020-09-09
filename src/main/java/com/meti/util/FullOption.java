package com.meti.util;

import java.util.function.Predicate;

public class FullOption<A, B, C> implements TriadOption<A, B, C> {
    public FullOption(A start, B middle, C end) {
    }

    @Override
    public TriadOption<A, B, C> filterMiddle(Predicate<B> predicate) {
        return null;
    }

    @Override
    public TriadOption<A, B, C> filterEnd(Predicate<C> predicate) {
        return null;
    }

    @Override
    public Option<A> onlyFirst() {
        return null;
    }

    @Override
    public <R1, E extends Throwable> R1 applyDestructionOrThrow(TriFunction<A, B, C, R1> function, E e) throws E {
        return null;
    }
}
