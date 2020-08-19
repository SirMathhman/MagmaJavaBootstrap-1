package com.meti.token.node;

import com.meti.token.type.Type;

import java.util.List;
import java.util.Map;

public interface ComplexNode extends Node {
	List<Node> children();

	default ComplexNode copy(Node... children) {
		return copy(List.of(children));
	}

	ComplexNode copy(List<Node> children);

	ComplexNode copy(Map<String, Type> parameters);

	Node copyParameters(List<Node> parameters);

	List<String> names();

	List<Node> parameterNodes();

	@Deprecated
	Map<String, Type> parameters();

}
