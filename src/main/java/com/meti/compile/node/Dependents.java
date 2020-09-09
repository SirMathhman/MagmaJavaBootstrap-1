package com.meti.compile.node;

import com.meti.compile.instance.Field;
import com.meti.util.MonadStream;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Dependents {
    <T> T apply(BiFunction<List<Field>, List<Token>, T> function);

    DependentsBuilder withoutFields();

    Dependents copyChildren(List<Token> children);

    Dependents copyFields(List<Field> fields);

    Dependents copyProperties(Field properties);

    Dependents identity();

    <T> T applyToProperties(Function<Field, T> function);

    @Deprecated
    Stream<Token> streamChildrenNatively();

    MonadStream<Token> streamChildren();

    MonadStream<Field> streamFields();

    @Deprecated
    Stream<Field> streamFieldsNatively();

    Dependents append(Token child);
}
