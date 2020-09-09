package com.meti.compile.process.util;

import com.meti.compile.instance.Field;
import com.meti.compile.instance.Type;
import com.meti.util.Option;

import java.util.List;
import java.util.Optional;

public interface CallStack {
    void enter();

    Field define(Field field);

    boolean isDefined(String name);

    List<Type> lookup(String name);

    Optional<String> lookup(String name, Type type);

    Optional<List<CallFlag>> flags(String name);

    default Option<List<CallFlag>> flagsOptionally(String name) {
        throw new UnsupportedOperationException();
    }
}
