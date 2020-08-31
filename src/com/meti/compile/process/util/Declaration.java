package com.meti.compile.process.util;

import com.meti.compile.type.Type;
import com.meti.compile.type.TypePair;

import java.util.List;
import java.util.function.Function;

public interface Declaration {
    <T> T applyFirst(Function<Type, T> function);

    String defineFrom(TypePair pair);

    String define(Type type);

    List<Type> listTypes();

    String lookup(Type type);
}
