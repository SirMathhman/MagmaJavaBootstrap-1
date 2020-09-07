package com.meti.compile.node.block;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

//printf(3, 4);
public class InvocationToken extends ParentToken {
	private final List<Token> arguments;
	private final Token caller;

	public InvocationToken(Token caller, List<Token> arguments) {
		this.caller = caller;
		this.arguments = Collections.unmodifiableList(arguments);
	}

	@Override
	public <T> T applyToGroup(Function<TokenGroup, T> mapper) {
		return mapper.apply(TokenGroup.Invocation);
	}

	@Override
	public Token copy(Dependents dependents) {
		List<Token> newChildren = dependents.streamChildren().collect(Collectors.toList());
		Token newCaller = newChildren.get(0);
		List<Token> newArguments = newChildren.subList(1, newChildren.size());
		return new InvocationToken(newCaller, newArguments);
	}

	@Override
	public Dependents createDependents() {
		List<Token> copy = new ArrayList<>(List.of(caller));
		copy.addAll(arguments);
		return InlineDependents.ofChildren(copy);
	}

	@Override
	public String render() {
		return renderArguments();
	}

	public String renderArguments() {
		return caller.render() + arguments.stream()
				.map(Token::render)
				.collect(Collectors.joining(",", "(", ")"));
	}
}
