package com.meti.compile.node;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class Line implements Node {
    private final Node child;

    public Line(Node child) {
        this.child = child;
    }

    @Override
    public void acceptDependents(Consumer<Dependents> consumer) {
        consumer.accept(InlineDependents.ofChild(child));
    }

    @Override
    public <T> T applyToDependents(Function<Dependents, T> mapper) {
        return mapper.apply(InlineDependents.ofChild(child));
    }

    @Override
    public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
        return mapper.apply(NodeGroup.Line);
    }

    @Override
    public Node copy(Dependents dependents) {
        return dependents.streamChildren()
                .findFirst()
                .map(Line::new)
                .orElseThrow();
    }

    @Override
    public String render() {
        return child.render() + ";";
    }

    @Override
    public <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function) {
        return Optional.empty();
    }
}
