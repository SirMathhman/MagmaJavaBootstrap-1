package com.meti.compile.node.structure;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.type.Field;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.meti.compile.node.InlineDependents.toFields;

public class StructureNode implements Node {
    private final String name;
    private final List<Field> fields;

    public StructureNode(String name, List<Field> fields) {
        this.name = name;
        this.fields = fields;
    }

    @Override
    public <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function) {
        return Optional.of(name)
                .map(clazz::cast)
                .map(function);
    }

    @Override
    public void acceptDependents(Consumer<Dependents> consumer) {
        consumer.accept(toFields(fields));
    }

    @Override
    public <T> T applyToDependents(Function<Dependents, T> mapper) {
        return mapper.apply(toFields(fields));
    }

    @Override
    public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
        return mapper.apply(NodeGroup.Structure);
    }

    @Override
    public Node copy(Dependents dependents) {
        return new StructureNodeBuilder()
                .withName(name)
                .apply(structureNodeBuilder -> attachFields(dependents, structureNodeBuilder))
                .build();
    }

    private StructureNodeBuilder attachFields(Dependents dependents, StructureNodeBuilder builder) {
        return dependents.streamFields().reduce(builder, StructureNodeBuilder::withField, (oldBuilder, newBuilder) -> newBuilder);
    }

    @Override
    public String render() {
        return "struct %s%s;".formatted(name, renderFields());
    }

    private String renderFields() {
        return fields.stream()
                .map(Field::render)
                .map("%s;"::formatted)
                .collect(Collectors.joining("", "{", "}"));
    }
}
