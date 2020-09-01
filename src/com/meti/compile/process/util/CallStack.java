package com.meti.compile.process.util;

import com.meti.compile.type.Field;
import com.meti.compile.type.Type;

import java.util.List;
import java.util.Optional;

public interface CallStack {
    void enter();

    List<Field> enter(List<Field> scope);

    Field define(Field field);

    boolean isDefined(String name);

    List<Type> lookup(String name);

    Optional<String> lookup(String name, Type type);

    Optional<List<CallFlag>> flags(String name);
}
