package com.meti.compile.type;

import com.meti.compile.node.Renderable;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public interface TypePair extends Renderable {
    <T> T apply(Function<TypePair, T> function);

    <T> T apply(BiFunction<String, Type, T> function);

    <T> T applyToType(Function<Type, T> function);

    <T> T applyToName(Function<String, T> function);

    TypePair withName(String name);

    TypePair acceptType(Consumer<Type> consumer);
}
