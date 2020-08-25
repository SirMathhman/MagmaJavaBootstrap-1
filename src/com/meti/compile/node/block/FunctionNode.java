package com.meti.compile.node.block;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.type.InlineTypePair;
import com.meti.compile.type.Type;
import com.meti.compile.type.TypePair;
import com.meti.compile.type.block.FunctionType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class FunctionNode implements Node {
	private final String name;
	private final List<TypePair> parameters;
	private final Type returnType;
	private final Node value;

	public FunctionNode(String name, Type returnType, Node value, List<TypePair> parameters) {
		this.parameters = Collections.unmodifiableList(parameters);
		this.returnType = returnType;
		this.value = value;
		this.name = name;
	}

	@Override
	public <T> T applyToDependents(Function<Dependents, T> mapper) {
		List<TypePair> fields = buildFields();
		List<Node> children = Collections.singletonList(value);
		Dependents dependents = InlineDependents.of(fields, children);
		return mapper.apply(dependents);
	}

	public List<TypePair> buildFields() {
		List<TypePair> fields = new ArrayList<>(createNamePair());
		fields.addAll(parameters);
		return fields;
	}

	public List<TypePair> createNamePair() {
		Collection<Type> paramTypes = parameters.stream().reduce(new ArrayList<>(), FunctionNode::append,
				(oldList, newList) -> newList);
		Type type = new FunctionType(returnType, paramTypes);
		TypePair pair = new InlineTypePair(name, type);
		return List.of(pair);
	}

	public static List<Type> append(List<Type> oldList, TypePair typePair) {
		List<Type> newList = new ArrayList<>(oldList);
		typePair.applyToType(newList::add);
		return newList;
	}

	@Override
	public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
		return mapper.apply(NodeGroup.Function);
	}

	@Override
	public Node copy(Dependents dependents) {
		return dependents.apply(FunctionNode::copyImpl);
	}

	public static Node copyImpl(List<TypePair> typePairs, List<Node> nodes) {
		TypePair pair = typePairs.get(0);
		List<TypePair> newParameters = typePairs.subList(1, typePairs.size());
		return pair.apply(FunctionNode::createBuilder)
				.withParameters(newParameters)
				.withValue(nodes.get(0))
				.build();
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

	@Override
	public String render() {
		String renderedValue = value.render();
		return returnType.render(name + "()" + renderedValue);
	}
}
