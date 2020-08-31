package com.meti.compile.process.util;

import com.meti.compile.type.Type;
import com.meti.compile.type.TypePair;

import java.util.List;
import java.util.Optional;

public interface CallStack {
    void enter();

    List<TypePair> enter(List<TypePair> scope);

    TypePair define(TypePair pair);

    boolean isDefined(String name);

    List<Type> lookup(String name);

    Optional<String> lookup(String name, Type type);
}
