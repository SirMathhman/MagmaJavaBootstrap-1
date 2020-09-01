package com.meti.compile.type.primitive;

import com.meti.compile.type.Type;
import com.meti.compile.type.TypeGroup;

import java.util.function.Function;
import java.util.stream.Stream;

public enum PrimitiveType implements Type {
    Int("int"),
    Implicit("?"),
    Char("char"),
    Unknown("?"),
    Any("void"),
    Void("void");

    private final String name;

    PrimitiveType(String name) {
        this.name = name;
    }

    @Override
    public <T> T applyToGroup(Function<TypeGroup, T> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean matches(Type other) {
        return this == other;
    }

    @Override
    public String render(String name) {
        return this.name + " " + name;
    }

    @Override
    public Stream<Type> streamChildren() {
        return Stream.empty();
    }
}
