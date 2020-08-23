package com.meti.compile.parse.scope;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;
import com.meti.compile.node.scope.VariableNode;
import com.meti.compile.parse.LexRule;

import java.util.Optional;

public class VariableLexRule implements LexRule {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return Optional.of(new VariableNode(content));
	}
}
