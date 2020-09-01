package com.meti.compile.process.util;

import com.meti.compile.type.Field;
import com.meti.compile.type.Type;

import java.util.List;

public interface Declaration {
    String defineFrom(Field pair);

    String define(Type type);

    List<Type> listTypes();

    String lookup(Type type);

    List<CallFlag> flags();
}
