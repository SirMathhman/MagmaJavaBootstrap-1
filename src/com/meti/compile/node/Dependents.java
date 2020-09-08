package com.meti.compile.node;

import com.meti.compile.type.Field;
import com.meti.util.MonadStream;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public interface Dependents {
    <T> T apply(BiFunction<List<Field>, List<Token>, T> function);

    Dependents copyChildren(List<Token> children);

    Dependents copyFields(List<Field> fields);

    Dependents identity();

    @Deprecated
    Stream<Token> streamChildrenNatively();

    MonadStream<Token> streamChildren();

    @Deprecated
    Stream<Field> streamFieldsNatively();

    Dependents append(Token child);
}
