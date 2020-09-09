package com.meti.compile.node;

import com.meti.compile.instance.Field;
import com.meti.util.MonadStream;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.meti.util.MonadStream.Empty;

public final class EmptyDependents implements Dependents {
    public static final EmptyDependents Empty = new EmptyDependents();

    private EmptyDependents() {
    }

    @Override
    public <T> T apply(BiFunction<List<Field>, List<Token>, T> function) {
        return function.apply(Collections.emptyList(), Collections.emptyList());
    }

    @Override
    public Dependents copyChildren(List<Token> children) {
        return Empty;
    }

    @Override
    public Dependents copyFields(List<Field> fields) {
        return Empty;
    }

    @Override
    public Stream<Token> streamChildrenNatively() {
        return Stream.empty();
    }

    @Override
    public Stream<Field> streamFieldsNatively() {
        return Stream.empty();
    }

    @Override
    public MonadStream<Token> streamChildren() {
        return Empty();
    }

    @Override
    public Dependents identity() {
        return Empty;
    }

    @Override
    public Dependents append(Token child) {
        throw new UnsupportedOperationException("This class is a singleton of empty dependents. " +
                "It doesn't make to append a child.");
    }

    @Override
    public MonadStream<Field> streamFields() {
        return Empty();
    }

    @Override
    public <T> T applyToProperties(Function<Field, T> function) {
        throw new UnsupportedOperationException("This class is a singleton of empty dependents and has no properties.");
    }

    @Override
    public Dependents copyProperties(Field properties) {
        return Empty;
    }

    @Override
    public DependentsBuilder withoutFields() {
        return new EmptyBuilder();
    }

    private static class EmptyBuilder implements DependentsBuilder {
        @Override
        public Dependents build() {
            return Empty;
        }

        @Override
        public DependentsBuilder append(Field field) {
            throw new UnsupportedOperationException("Immutable builder.");
        }
    }
}
