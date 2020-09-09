package com.meti.compile.node.block;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

//printf(3, 4);
public class InvocationNode extends ParentNode {
	private final List<Node> arguments;
	private final Node caller;

	public InvocationNode(Node caller, List<Node> arguments) {
		this.caller = caller;
		this.arguments = Collections.unmodifiableList(arguments);
	}

	@Override
	public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
		return mapper.apply(NodeGroup.Invocation);
	}

	@Override
	public Node copy(Dependents dependents) {
		List<Node> newChildren = dependents.streamChildren().collect(Collectors.toList());
		Node newCaller = newChildren.get(0);
		List<Node> newArguments = newChildren.subList(1, newChildren.size());
		return new InvocationNode(newCaller, newArguments);
	}

	@Override
	public Dependents createDependents() {
		List<Node> copy = new ArrayList<>(List.of(caller));
		copy.addAll(arguments);
		return InlineDependents.ofChildren(copy);
	}

	@Override
	public String render() {
		return renderArguments();
	}

	public String renderArguments() {
		return caller.render() + arguments.stream()
				.map(Node::render)
				.collect(Collectors.joining(",", "(", ")"));
	}
}
