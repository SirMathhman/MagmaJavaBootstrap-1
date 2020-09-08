package com.meti.compile.node;

import com.meti.compile.type.Field;

public interface DependentsBuilder {
    Dependents build();

    DependentsBuilder append(Field field);
}
