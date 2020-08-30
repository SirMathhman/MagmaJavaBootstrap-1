package com.meti.compile.type.reference;

import com.meti.compile.type.Type;
import com.meti.compile.type.TypeGroup;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PointerType implements Type {
    private final Type parent;

    public PointerType(Type parent) {
        this.parent = parent;
    }

    @Override
    public <T> T applyToGroup(Function<TypeGroup, T> function) {
        return function.apply(TypeGroup.Pointer);
    }

    @Override
    public boolean matches(Type other) {
        if (!other.applyToGroup(TypeGroup.Pointer::matches)) return false;
        List<Type> children = other.streamChildren().collect(Collectors.toList());
        if (children.size() != 1) return false;
        return children.get(0).matches(parent);
    }

    @Override
    public String render(String name) {
        return parent.render("* " + name);
    }

    @Override
    public Stream<Type> streamChildren() {
        return Stream.of(parent);
    }
}
