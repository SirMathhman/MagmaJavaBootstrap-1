package com.meti.compile.type;

import com.meti.compile.node.Renderable;
import com.meti.compile.process.util.Declaration;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Field extends Renderable {
    Declaration createDeclaration(String nameToUse);

    <T> T apply(Function<Field, T> function);

    <T> T apply(BiFunction<String, Type, T> function);

    <T> T applyToType(Function<Type, T> function);

    <T> T applyToName(Function<String, T> function);

    Field withName(String name);

    Field acceptType(Consumer<Type> consumer);
}
