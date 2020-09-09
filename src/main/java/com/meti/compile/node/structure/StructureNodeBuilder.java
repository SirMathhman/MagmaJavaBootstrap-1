package com.meti.compile.node.structure;

import com.meti.compile.type.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Collections.emptyList;

public class StructureNodeBuilder {
    private final String name;
    private final List<Field> fields;

    public StructureNodeBuilder() {
        this(null, emptyList());
    }

    public <T> T apply(Function<StructureNodeBuilder, T> function) {
        return function.apply(this);
    }

    public StructureNodeBuilder(String name, List<Field> fields) {
        this.name = name;
        this.fields = new ArrayList<>(fields);
    }

    public StructureNodeBuilder withName(Supplier<String> name) {
        return new StructureNodeBuilder(name.get(), fields);
    }

    public StructureNodeBuilder withFields(Supplier<List<Field>> fields) {
        return new StructureNodeBuilder(name, fields.get());
    }

    public StructureNodeBuilder withName(String name) {
        return new StructureNodeBuilder(name, fields);
    }

    public StructureNodeBuilder withField(Field field) {
        this.fields.add(field);
        return this;
    }

    public StructureNode build() {
        return new StructureNode(name, fields);
    }
}