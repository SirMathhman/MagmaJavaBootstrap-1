package com.meti.compile.node.block;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.type.InlineField;
import com.meti.compile.type.Type;
import com.meti.compile.type.Field;
import com.meti.compile.type.block.FunctionType;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunctionNode implements Node {
    private final String name;
    private final List<Field> parameters;
    private final Type returnType;
    private final Node value;

    public FunctionNode(String name, Type returnType, Node value, List<Field> parameters) {
        this.parameters = Collections.unmodifiableList(parameters);
        this.returnType = returnType;
        this.value = value;
        this.name = name;
    }

    @Override
    public void acceptDependents(Consumer<Dependents> consumer) {
        List<Field> fields = buildFields();
        List<Node> children = Collections.singletonList(value);
        Dependents dependents = InlineDependents.of(fields, children);
        consumer.accept(dependents);
    }

    @Override
    public <T> T applyToDependents(Function<Dependents, T> mapper) {
        List<Field> fields = buildFields();
        List<Node> children;
        if (value == null) {
            children = Collections.emptyList();
        } else {
            children = Collections.singletonList(value);
        }
        Dependents dependents = InlineDependents.of(fields, children);
        return mapper.apply(dependents);
    }

    @Override
    public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
        return mapper.apply(NodeGroup.Function);
    }

    @Override
    public Node copy(Dependents dependents) {
        return dependents.apply(FunctionNode::copyImpl);
    }

    public static Node copyImpl(List<Field> fields, List<Node> nodes) {
        Field pair = fields.get(0);
        List<Field> newParameters = fields.subList(1, fields.size());
        FunctionNodeBuilder builder = pair.apply(FunctionNode::createBuilder)
                .withParameters(newParameters);
        if(!nodes.isEmpty()) builder = builder.withValue(nodes.get(0));
        return builder.build();
    }

    public static FunctionNodeBuilder createBuilder(String name, Type type) {
        Type returnType = findReturnType(type);
        return new FunctionNodeBuilder()
                .withName(name)
                .withReturnType(returnType);
    }

    public static Type findReturnType(Type type) {
        return type.streamChildren()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No return type was found in: " + type));
    }

    public List<Field> buildFields() {
        List<Field> fields = new ArrayList<>(createNamePair());
        fields.addAll(parameters);
        return fields;
    }

    public List<Field> createNamePair() {
        Collection<Type> paramTypes = parameters.stream().reduce(new ArrayList<>(), FunctionNode::append,
                (oldList, newList) -> newList);
        Type type = new FunctionType(returnType, paramTypes);
        //TODO:  function flags
        Field pair = new InlineField(name, type, Collections.emptyList());
        return List.of(pair);
    }

    public static List<Type> append(List<Type> oldList, Field field) {
        List<Type> newList = new ArrayList<>(oldList);
        field.applyToType(newList::add);
        return newList;
    }

    @Override
    public String render() {
        if (value != null) {
            String paramString = parameters.stream()
                    .map(Field::render)
                    .collect(Collectors.joining(",", "(", ")"));
            String renderedValue = value.render();
            return returnType.render(name + paramString + renderedValue);
        } else {
            return "";
        }
    }

    @Override
    public <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function) {
        return Optional.empty();
    }
}
