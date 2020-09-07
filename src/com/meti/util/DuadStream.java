package com.meti.util;

import java.util.stream.Stream;

public class DuadStream<A, B> {
    private final Stream<Duad<A, B>> stream;

    public DuadStream(Stream<Duad<A, B>> stream) {
        this.stream = stream;
    }
}
