package com.meti.compile.process.util;

import com.meti.compile.instance.Type;
import com.meti.compile.instance.Field;

import java.util.List;
import java.util.Optional;

public interface StackFrame {
    String define(Field pair);

    boolean isDefined(String name);

    List<Type> lookup(String name);

    Optional<String> lookup(String name, Type type);

    List<CallFlag> flags(String name);
}
