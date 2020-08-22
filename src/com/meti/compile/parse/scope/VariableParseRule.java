package com.meti.compile.parse.scope;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;
import com.meti.compile.node.scope.VariableNode;
import com.meti.compile.parse.FilteredParseRule;
import com.meti.compile.util.CallStack;

public class VariableParseRule extends FilteredParseRule {
	private final CallStack stack;

	public VariableParseRule(CallStack stack) {
		this.stack = stack;
	}

	@Override
	public boolean canQualify(String content) {
		return stack.isDefined(content);
	}

	@Override
	public Node parseQualified(String content, Compiler compiler) {
		return new VariableNode(content);
	}
}
