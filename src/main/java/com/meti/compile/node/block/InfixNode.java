package com.meti.compile.node.block;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InfixNode extends ParentNode {
    private final Node child0;
    private final Node child1;
    private final Node operator;

    public InfixNode(Node operator, Node child0, Node child1) {
        this.child0 = child0;
        this.child1 = child1;
        this.operator = operator;
    }

    @Override
    public Dependents createDependents() {
        return InlineDependents.ofChildren(operator, child0, child1);
    }

    //TODO:
    /*
    Complete this node.
    Then complete the parser.
    Then, add a modifier that replaces this node with the proper invocation,
    based on the types of the nodes given.
    Additionally, add native infixes so that C operations can be used as well.
     */

    @Override
    public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
        return mapper.apply(NodeGroup.Infix);
    }

    @Override
    public Node copy(Dependents dependents) {
        List<Node> children = dependents.streamChildren()
                .collect(Collectors.toList());
        return new InfixNode(children.get(0), children.get(1), children.get(2));
    }

    @Override
    public String render() {
        return child0.render() + operator.render() + child1.render();
    }
}
