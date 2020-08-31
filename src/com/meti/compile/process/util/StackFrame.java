package com.meti.compile.process.util;

import com.meti.compile.type.Type;
import com.meti.compile.type.TypePair;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface StackFrame {
    String define(TypePair pair);

    boolean isDefined(String name);

    List<Type> lookup(String name);

    Optional<String> lookup(String name, Type type);
}
